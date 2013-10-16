define(['modules/gmaps', 'db/db', 'modules/geo', 'poi/poi'],
  function(GMaps, Db, Geo, Poi) {

  // Devuelve la key para almacenar la distancia entre un par de POIs.
  // Es *única* para cada par de POIs
  var storeKey = function(id1, id2, transportation) {
    id1 = String(id1);
    id2 = String(id2);

    var pairId = id1 < id2 ? id1 + '.' + id2 : id2 + '.' + id1;

    return window.res.package + '.distance.' + transportation + '.' + pairId;
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
    return (window.res.types[poi.get('type')] && window.res.types[poi.get('type')].duration) ||
      DEFAULT_VISIT_TIME;
  }

  /*
    Obtiene las distancias entre cada par consecutivo de POIs en @pois. Guarda los resultados en
    localStorage para acelerar consultas sucesivas. Si GMaps no puede encontrar una ruta entre ambos
    puntos, se utilizan unos valores por defecto para dar una aproximación.
    AVISO: el módulo GMaps *tiene* que estar cargado.

    @pois es una Collection poi/collection

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
    _.each(pois.slice(0, -1), function(poi, i, list) {
      var next = pois.at(i+1) 
      , key = storeKey(poi.get('id'), next.get('id'), options.transportation)
      , entry = localStorage.getItem(key)
      ;

      if (entry) {
        distances[i] = JSON.parse(entry);
      } else {
        // Un placeholder para recuperar la posición del POI
        distances[i] = key;

        newDestinations[poi.get('id')] = poi;
        newDestinations[next.get('id')] = next;
      }
    });


    // Se salta GMaps si no es necesario
    if (_.isEmpty(newDestinations)) {
      return callback(null, distances);
    }


    // Se usa un array de IDs para asegurar el orden de iteración
    var destinationIds = _.keys(newDestinations)

    , newDestinationsArray = _.map(destinationIds, function(id) {
      return {
        lat: newDestinations[id].get('lat'),
        lon: newDestinations[id].get('lon'),
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
                Geo.distance(newDestinations[id1].get('lat'),
                              newDestinations[id1].get('lon'),
                              newDestinations[id2].get('lat'),
                              newDestinations[id2].get('lon'))
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

    @pois es una collection de pois ordenados a visitar.

    @distances es un array de distancias según el formato de getDistances

    @options son las opciones especificadas en getTravelPlan

    Devuelve un plan de viaje con el formato:
    [
      // Dia 1
      [
        {
          start: 2013-10-22T08:00,
          end: 2013-10-22T08:30,
          poi: PoiModel,
        },
        // Este corresponde a un trayecto
        {
          start: 2013-10-22T08:30,
          end: 2013-10-22T09:30,
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
    , format = 'YYYY-MM-DDTHH:mm'
    // Se usa el tiempo UTC
    , currentDay = options.startDay
    , currentTime = moment.utc(options.startDay + 'T' + options.startTime)
    , todaysEndTime = moment.utc(options.startDay + 'T' + options.endTime)
    , poiDuration
    ;


    for (var i = 0; i < pois.length; i++) {
      var poi = pois.at(i);

      // Salvo que sea la primera visita del día, añadir el tiempo de viaje desde el
      // anterior POI
      if (day.length !== 0) {
        day.push({
          startTime: currentTime.format(format),
          endTime: currentTime.add('s', distances[i-1].duration).format(format),
          poi: null,
          approx: distances[i-1].approx
        });
      }

      poiDuration = getPoiDuration(pois.at(i));

      day.push({
        startTime: currentTime.format(format),
        endTime: currentTime.add('s', poiDuration).format(format),
        poi: poi
      });

      // Si este no es el último POI y la siguiente visita "no cabe" en el día actual,
      // empieza un nuevo día
      if (i < pois.length - 1) {
        poiDuration = getPoiDuration(pois.at(i+1));
        var nextStopEnd = currentTime.clone()
                            .add('s', poiDuration).add('s', distances[i].duration);

        if (nextStopEnd > todaysEndTime) {
          plan.push(day);
          day = [];
          currentDay = moment.utc(currentDay).add('d', 1).format('YYYY-MM-DD');
          currentTime = moment.utc(currentDay + 'T' + options.startTime);
          todaysEndTime.add('d', 1);
        }
      } else {
        plan.push(day);
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
              pois = new Poi.Collection(_.map(favorites, function(fav) {
                return new Poi.Model(fav, {parse: true});
              }));
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
            return callback(err, null);
          }
          callback(null, makeTravelPlan(pois, results[1], options));
        });
      });

    },

  };
});