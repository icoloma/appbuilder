define(['travelplanner/planner', 'modules/gmaps', 'test/mocksql', 'db/db'],
  function(Planner, GMaps, MockSql, Db) {

  var dbMock
  , HOUR = 3600

  , favorites = [
    { id: '0', lat: 41, lon: 2, name: 'museo plim', type: 'museum' },
    { id: '1', lat: 43, lon: 2, name: 'restaurante foo', type: 'restaurant' },
    { id: '2', lat: 39, lon: 3, name: 'siam park', type: 'aquapark' },
    { id: '3', lat: 46, lon: 4, name: 'castillo dracula', type: 'monument' },
    { id: '4', lat: 37, lon: -1, name: 'el pirulí', type: 'monument' },
    { id: '5', lat: 39, lon: -2, name: 'el prado', type: 'museum' },
    { id: '6', lat: 44, lon: 9, name: 'casa pepe', type: 'restaurant' },
  ]

  , types = {
    museum: { duration: 3*HOUR },
    aquapark: { duration: 4*HOUR },
    restaurant: { duration: 2*HOUR },
    monument: { duration: 2*HOUR }
  }

  // Matriz de distancias a devolver por GMaps
  , row = [
    {distance: 10000, duration: HOUR}, {distance: 100000, duration: HOUR},
    {distance: 25000, duration: HOUR}, {distance: 50000, duration: HOUR},
    {distance: 10000, duration: HOUR}, {distance: 5000, duration: HOUR},
    {distance: 30000, duration: HOUR}
  ]

  , distanceMatrix = [ row, row, row, row, row, row, row ]

  // Mock de GMaps. Opciones similares a lib/mocksql, junto con @options.loadError, para
  // indicar un error en GMaps.load
  , GMapsMock = {

    options: {},

    getDistances: function(destinations, options, callback) {
      if (this.options.error) {
        return callback(this.options.error, null);
      }
      return callback(null, this.options.results || []);
    },

    load: function(callback) {
      if (this.options.loadError) {
        return callback(this.options.loadError);
      }
      return callback(null);
    }
  }

  // Para guardar el auténtico módulo GMaps
  , GMapsBackup

  ;

  module('Travel Planner', {
    setup: function() {

      // Mockea GMaps devolviendo por defecto 'distanceMatrix' y sin problemas de load
      GMapsBackup = _.clone(GMaps);
      GMaps.getDistances = _.bind(GMapsMock.getDistances, GMapsMock);
      GMaps.load = _.bind(GMapsMock.load, GMapsMock);

      var matrixClone = JSON.parse(JSON.stringify(distanceMatrix));

      GMapsMock.options = {
        results: matrixClone
      };

      // Mockea la BDD devolviendo por defecto 'favorites'
      dbMock = new MockSql.Database();
      dbMock.options = {
        results: favorites
      };

      MockSql.mockProxy(Db, dbMock);

      window.res.types = types;
    },
    teardown: function() {

      // Recupera el módulo GMaps original
      _.extend(GMaps, GMapsBackup);

      localStorage.clear();

      delete Db.transaction;
    }
  });

  asyncTest('Errores en Planner', 3, function() {

    async.series([
      function(cb) {
        GMapsMock.options.loadError = new Error();

        Planner.getTravelPlan({
          startTime: '10:00',
          endTime: '21:00',
        }, function(err, plan) {
          ok(!!err && !plan, 'Error en GMaps.load.');
          cb();
        });

      },
      function(cb) {
        delete GMapsMock.options.loadError;
        dbMock.options.error = new Error();

        Planner.getTravelPlan({
          startTime: '10:00',
          endTime: '21:00',
        }, function(err, plan) {
          ok(!!err && !plan, 'Error en la petición SQL.');
          cb();
        });
      },
      function(cb) {
        delete dbMock.options.error;
        GMapsMock.options.error = new Error();

        Planner.getTravelPlan({
          startTime: '10:00',
          endTime: '20:00',
        }, function(err, plan) {
          ok(!!err && !plan, 'Error en la petición a Google Maps.');
          cb();
        });
      }
    ], function() {
      start();
    });

  });

  asyncTest('Cálculo de la ruta.', 3, function() {

    Planner.getTravelPlan({
      startTime: '10:00',
      endTime: '19:00',
      startDate: '2013-10-09',
      transportation: 'bicicleta'
    }, function(err, plan) {

      equal(plan.filter(function(step) {
        return step.travel === null;
      }).length , 3, 'Días de ruta.');



      ok(_.every(plan, function(step, i) {
        if (!i || !plan[i-1].travel) return true;

        var calculated = moment.utc(plan[i-1].date + 'T' + plan[i-1].startTime);

        return step.startTime === calculated
                                    .add('s', plan[i-1].duration)
                                    .add('s', plan[i-1].travel.duration)
                                    .format('HH:mm');
      }), 'visita[i].endDateTime + duración del viaje === visita[i+1].startDateTime.');

      ok((function() {
        var test = true;
        for (var i = 0; i < favorites.length; i++) {
          var id1 = favorites[i].id;
          for (var j = i + 1; j < favorites.length; j++) {
            var id2 = favorites[j].id
            , key = window.res.package + '.distance.bicicleta.' +
              (id1 < id2 ? id1 + '.' + id2 : (id2 + '.' + id1))
            ;

            test = test && _.isEqual(distanceMatrix[i][j],
              JSON.parse(localStorage.getItem(key))
            );
          }
        }
        return test;
      })(), 'Las distancias se almacenan en localStorage.')

      start();
    });
  });

  asyncTest('Rutas aproximadas', 3, function() {
    GMapsMock.options.results[4][5] = GMapsMock.options.results[5][4] = null;

    // Un grado de latitud ~= 1.6h de viaje (sin contar la aproximación al alza)
    favorites[5].lon = favorites[4].lon;
    favorites[5].lat = favorites[4].lat + 1;

    Planner.getTravelPlan({
      startTime: '10:00',
      startDate: '2013-10-09',
      endTime: '19:00',
      transportation: 'DRIVING'
    }, function(err, plan) {
      equal(plan.filter(function(step) {
        return step.travel === null;
      }).length, 4, 'Plan de viaje alargado.');

      var step = plan[4];
      ok(step.travel.approx, 'Duración marcada como approx.');

      ok(step.travel.duration > 3600,
          'Duración del trayecto modificada');
      start();
    });
  });
});