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

          // Aproximación cuando GMaps no devuelve ruta
          if (distanceObj === null) {
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

          // Almacena la consulta o aproximación
          // AVISO: almacena también las aproximaciones, descartando futuras consultas a 
          // GMaps
          localStorage.setItem(key, JSON.stringify(distanceObj));

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
      {
        date: <Fecha de la visita YYYY-MM-DD>,
        startTime: <Hora de llegada HH:mm>,
        duration: <duración de la visita, en segundos>,
        poi: <POI JSON>,

        // Describe el viaje hasta el siguiente POI. Puede ser null, si es la última visita de
        // un día
        travel: {
          distance: <distancia en metros>,
          duration: <duración del viaje en segundos>,
          approx: true (indica si el trayecto hasta el siguiente POI es aproximado)
        }
      }
    ]
  */
  makeTravelPlan = function(pois, distances, options) {
    var plan = []
    , format = 'YYYY-MM-DDTHH:mm'
    , dateFormat = 'YYYY-MM-DD'
    , timeFormat = 'HH:mm'
    // Se usa el tiempo UTC
    , currentDay = options.startDate
    , currentTime = moment.utc(options.startDate + 'T' + options.startTime)
    , todaysEndTime = moment.utc(options.startDate + 'T' + options.endTime)
    , poiDuration, travelStep
    ;


    for (var i = 0; i < pois.length; i++) {
      var poi = pois.at(i);

      poiDuration = getPoiDuration(pois.at(i));

      travelStep = {
        date: currentTime.format(dateFormat),
        startTime: currentTime.format(timeFormat),
        duration: poiDuration,
        poi: poi,
        travel: null
      };

      currentTime.add('s', poiDuration);

      // Si este no es el último POI y la siguiente visita "no cabe" en el día actual,
      // empieza un nuevo día
      if (i < pois.length - 1) {
        poiDuration = getPoiDuration(pois.at(i+1));
        var nextStopEnd = currentTime.clone()
                            .add('s', poiDuration).add('s', distances[i].duration);

        if (nextStopEnd > todaysEndTime) {
          currentDay = moment.utc(currentDay).add('d', 1).format('YYYY-MM-DD');
          currentTime = moment.utc(currentDay + 'T' + options.startTime);
          todaysEndTime.add('d', 1);
        } else {
          travelStep.travel = _.clone(distances[i]);
          currentTime.add('s', distances[i].duration);
        }
      }

      plan.push(travelStep);
    }
    return plan;
  }
  ;

  return {
    /*
      Elabora un plan de viaje con los POIs marcados como favoritos.

      Los parámetros son identicos a getPoisTravelPlan
    */
    getTravelPlan: function(options, callback) {
      var self = this;

      Db.sql('SELECT * FROM Poi WHERE starred > -1 ORDER BY starred', [], function(err, favorites) {
        // TO-DO: error handling
        if (err) return callback(err, null);
        var pois = new Poi.Collection(_.map(favorites, function(fav) {
          return new Poi.Model(fav, {parse: true});
        }));
        self.getPoisTravelPlan(pois, options, callback);
      });
    },

    /*
      Elabora un plan de viaje con los POIs suministrados.

      @pois es una Poi.Collection de POIs, en el orden en el que serán visitados.

      @options incluye:
        - transportation: tipo de transporte según el API de GMaps
        - startTime, endTime: horas para el comienzo y el fin de la ruta cada día (en formato 24h)

      @callback se llama con (err, null) en caso de error, y con (null, plan) en caso contrario,
      donde 'plan' tiene el formato especificado en makeTravelPlan
    */
    getPoisTravelPlan: function(pois, options, callback) {
      GMaps.load(function(err) {
        if (err) return callback(err, null);

        getDistances(pois, options, function(err, distances) {
          // TO-DO: error handling
          if (err) {
            return callback(err, null);
          }
          callback(null, makeTravelPlan(pois, distances, options));
        });
      });
    },
  };
});