define(['modules/gmaps', 'db/db', 'modules/geo'], function(GMaps, Db, Geo) {

  // Devuelve la key para almacenar la distancia entre un par de POIs.
  // Es *única* para cada par de POIs
  var storeKey = function(id1, id2, transportation) {
    id1 = String(id1);
    id2 = String(id2);

    var pairId = id1 < id2 ? id1 + '.' + id2 : id2 + '.' + id1;

    return window.res.package + '.distance.' + transportation + '.' + pairId;
  }

  // Helpers para el formato de tiempo utilizado
  , TIME_REGEX = /([0-9]+):([0-9]{2})/

  , getHours = function(time) {
    return Number(time.match(TIME_REGEX)[1]);
  }

  , getMinutes = function(time) {
    return Number(time.match(TIME_REGEX)[2]);
  }

  /*
    Compara dos timestamps en formato "24h", donde las horas pueden ser > 23
  */
  , compareTime = function(time1, time2) {
    var hours1 = getHours(time1)
    , minutes1 = getMinutes(time1)
    , hours2 = getHours(time2)
    , minutes2 = getMinutes(time2)
    , total1 = hours1*60 + minutes1
    , total2 = hours2*60 + minutes2
    , diff = total1 - total2
    ;

    return diff > 0 ? 1 : (diff < 0 ? -1 : 0);
  }

  /*
    Añade una cierta cantidad de tiempo a una hora inicial.
    *NO* efectúa acarreos: 23:55 + 3600 segundos == 24:55
    @time es la hora inicial, en formato "24h": H*HH:MM (las horas pueden ser > 23)
    @duration es la duración en segundos
    El formato final es igual al de @time
  */
  , addTime = function(time, duration) {
    var minutes = Math.floor(duration/60)%60
    , hours = Math.floor(duration/3600)
    , initialHour = getHours(time)
    , initialMinute = getMinutes(time)
    , endHour = initialHour + hours
    , endMinute = minutes + initialMinute
    ;

    if (endMinute >= 60) {
      endMinute -= 60;
      endHour++;
    }
    if (endMinute < 10) {
      endMinute = '0' + endMinute;
    }

    return endHour + ':' + endMinute;
  }

  // Velocidades por defecto en m/s
  , DEFAULT_SPEED = {
    // ~70 km/h
    DRIVING: 19,
    // ~4 km/h
    WALKING: 1,
    // ~20 km/h
    BICYCLING: 5,
    // ~50 km/h
    TRANSIT: 14
  }

  // Tiempo de visita por defecto, en s
  , DEFAULT_VISIT_TIME = 3000

  , getPoiDuration = function(poi) {
    return (window.res.types[poi.type] && window.res.types[poi.type].duration) || DEFAULT_VISIT_TIME;
  }

  /*
    Obtiene las distancias entre cada par consecutivo de POIs en @pois. Guarda los resultados en
    localStorage para acelerar consultas sucesivas. Si GMaps no puede encontrar una ruta entre ambos
    puntos, se utilizan unos valores por defecto para dar una aproximación.
    AVISO: el módulo GMaps *tiene* que estar cargado.

    @options.transportation es el medio de transporte a usar.

    @callback se llama con un par (err, distances), donde 'err' es un posible error,
    y (si no hay error) 'distances' es un array de "objetos distancia":
    {
      distance: (distancia en metros),
      duration: (duración en segundos),
      approx: true (sólo si es una aproximación)
    }
  */
  , getDistances = function(pois, options, callback) {
    // El array de resultados
    var distances = []

    // Los destinos "nuevos", para los cuales aún no se ha hecho una consulta
    , newDestinations = {}
    ;

    // Separa los (pares de) POIs entre aquellos para los que ya existe una consulta almacenada
    // y los que necesitan llamar a GMaps
    for (var i = 0; i < pois.length - 1; i++) {
      var key = storeKey(pois[i].id, pois[i+1].id, options.transportation)
      , entry = localStorage.getItem(key)
      ;

      if (entry) {
        distances[i] = JSON.parse(entry);
      } else {
        // Un placeholder para recuperar la posición del POI
        distances[i] = key;

        newDestinations[pois[i].id] = pois[i];
        newDestinations[pois[i+1].id] = pois[i+1];
      }
    }

    // Se salta GMaps si no es necesario
    if (_.isEmpty(newDestinations)) {
      return callback(null, distances);
    }


    // Se usa un array de IDs para asegurar el orden de iteración
    var destinationIds = _.keys(newDestinations)

    , newDestinationsArray = _.map(destinationIds, function(id) {
      return {
        lat: newDestinations[id].lat,
        lon: newDestinations[id].lon,
      };
    })
    ;

    GMaps.getDistances(newDestinationsArray, options, function(err, distancesMatrix) {
      if (err) return callback(err, null);

      for (var i = 0; i < distancesMatrix.length -1; i++) {
        var id1 = destinationIds[i];
        for (var j = i + 1; j < distancesMatrix.length; j++) {
          var id2 = destinationIds[j]
          , key = storeKey(id1, id2, options.transportation)
          , distanceObj = distancesMatrix[i][j]
          ;

          if (distanceObj !== null) {
            // Almacena la consulta
            localStorage.setItem(key, JSON.stringify(distanceObj));
          } else {
            // Aproximación
            var estimatedDistance =
              Math.floor(1000*1.5*
                Geo.distance(newDestinations[id1].lat, newDestinations[id1].lon, newDestinations[id2].lat, newDestinations[id2].lon)
              )
            , estimatedDuration = Math.floor(estimatedDistance/DEFAULT_SPEED[options.transportation])
            ;

            distanceObj = {
              distance: estimatedDistance,
              duration: estimatedDuration,
              approx: true
            };
          }

          // Usa el placeholder para colocar el distance object
          distances[distances.indexOf(key)] = distanceObj;
        }
      }

      callback(null, distances);
    });
  },

  /*
    Calcula un plan de viaje.

    @pois es la lista de pois ordenados a visitar.

    @distances es un array de distancias según el formato de getDistances

    @options son las opciones especificadas en getTravelPlan

    Devuelve un plan de viaje con el formato:
    [
      // Dia 1
      [
        {
          startTime: 8:00,
          endTime: 8:30,
          poi: "546da",
          name: "museo plim"
        },
        // Este corresponde a un trayecto
        {
          startTime: 8:30,
          endTime: 9:30,
          poi: null,
          approx: true (sólo si es una aproximación)
        },
        ...
      ],
      // Dia 2
      ...
    ]
  */
  makeTravelPlan = function(pois, distances, options) {
    var plan = []
    , day = []
    , currentTime = options.startTime
    , poiDuration
    ;

    for (var i = 0; i < pois.length; i++) {

      // Salvo que sea la primera visita del día, añadir el tiempo de viaje desde el
      // anterior POI
      if (day.length !== 0) {
        day.push({
          startTime: currentTime,
          endTime: addTime(currentTime, distances[i-1].duration),
          poi: null,
          approx: distances[i-1].approx
        });
        currentTime = addTime(currentTime, distances[i-1].duration);
      }

      poiDuration = getPoiDuration(pois[i]);

      day.push({
        startTime: currentTime,
        endTime: addTime(currentTime, poiDuration),
        poi: pois[i].id,
        name: pois[i].name
      });

      currentTime = addTime(currentTime, poiDuration);

      // Si este no es el último POI y la siguiente visita "no cabe" en el día actual,
      // empieza un nuevo día
      if (i < pois.length - 1) {
        poiDuration = getPoiDuration(pois[i+1]);
        var nextStopEnd = addTime(addTime(currentTime, distances[i].duration), poiDuration);

        if (compareTime(nextStopEnd, options.endTime) > 0) {
          plan.push(day);
          day = [];
          currentTime = options.startTime;
        }
      }
    }

    return plan;
  }
  ;

  return {
    /*
      Elabora un plan de viaje con los POIs marcados como favoritos.

      @options incluye:
        - transportation: tipo de transporte según el API de GMaps
        - startTime, endTime: horas para el comienzo y el fin de la ruta cada día (en formato 24h)

      @callback se llama con (err, null) en caso de error, y con (null, plan) en caso contrario,
      donde 'plan' tiene el formato especificado en makeTravelPlan
    */
    getTravelPlan: function(options, callback) {
      var pois;

      GMaps.load(function(err) {
        if (err) return callback(err, null);
        async.series([
          function(cb) {
            Db.sql('SELECT * FROM Poi WHERE starred > -1 ORDER BY starred', [], function(err, favorites) {
              pois = favorites;
              cb(err, favorites);
            });
          },
          function(cb) {
            getDistances(pois, options, cb);
          }
        ], function(err, results) {
          // TO-DO: error handling
          if (err) {
            if (err.code === GMaps.DISTANCEMATRIX_ERROR) {

            } else {
              // SQL error
            }
          }
          callback(null, makeTravelPlan(pois, results[1], options));
        });
      });

    },

  };
});