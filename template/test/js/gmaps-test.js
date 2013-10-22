define(['modules/gmaps'], function(GMaps) {

  var DistanceMatrixServiceMock

  // Un generador de mocks para DistanceServiceProvider
  // No se puede acceder al servicio concreto que se usa, sólo al constructor, al que
  // se le añade un método setOptions
  , DistanceMatrixServiceProvider = function() {
    var ServiceMock = function() {};

    ServiceMock.prototype = {
      getDistanceMatrix: function(options, callback) {
        if (this.options.error) {
          callback(null, this.options.error);
          if (this.options.failure) {
            this.options.failure();
          }
          return;
        }
        return callback(this.options.results, 'OK');
      }
    };

    // @options es similar a lib/mocksql
    ServiceMock.setOptions = function(options) {
      ServiceMock.prototype.options = options;
    };

    return ServiceMock;
  }
  // Un mock del resto de funcionalidades de google.maps
  , googleMaps = {
    LatLng: function(lat, lon) {
      return {
        lat: lat,
        lon: lon
      };
    },
    TravelMode: {
      'bicicleta': 'bici'
    },
  }
  // Construye un mock de una respuesta de GMaps a partir de una matriz de distancias y duraciones
  // https://developers.google.com/maps/documentation/javascript/distancematrix#distance_matrix_responses
  , apiResults = function(matrix) {
    var rows = []; 
    for (var i = 0; i < matrix.length; i++) {
      rows[i] = {elements: []};
      for (var j = 0; j < matrix[i].length; j++) {
        rows[i].elements.push({
          distance: { value: matrix[i][j].distance },
          duration: { value: matrix[i][j].duration },
          status: 'OK'
        });
      }
    }
    return {rows: rows};
  }

  , destinations = [
    { lat: 40, lon: 5 },
    { lat: 39, lon: 2 },
    { lat: 42, lon: 7 }
  ]
  , results = [
    [ { duration: 0, distance: 1 }, { duration: 1000, distance: 10000} ],
    [ { duration: 4000, distance: 340000 }, { duration: 2500, distance: 50000} ],
  ]
  , options = {
    transportation: 'bicicleta',
  };


  module('GMaps test', {
    setup: function() {
      GMaps._oldMaps = GMaps.maps;
      GMaps.maps = _.clone(googleMaps);
      DistanceMatrixServiceMock = new DistanceMatrixServiceProvider();
      GMaps.maps.DistanceMatrixService = DistanceMatrixServiceMock;
    },

    teardown: function() {
      GMaps.maps = GMaps._oldMaps;
      delete GMaps._oldMaps;
    }
  });

  asyncTest('Error en la petición a GMaps', 1, function() {
    DistanceMatrixServiceMock.setOptions({
      error: 'SOME_ERROR'
    });

    GMaps.getDistances(destinations, options, function(err, distances) {
      equal(err.code, GMaps.DISTANCEMATRIX_ERROR, 'DISTANCEMATRIX_ERROR');
      start();
    });
  });

  asyncTest('Parseo de resultados', 1, function() {
    DistanceMatrixServiceMock.setOptions({
      results: apiResults(results)
    });

    GMaps.getDistances(destinations, options, function(err, distances) {
      deepEqual(distances, results, 'Parseo de resultados correcto.');
      start();
    });
  });

  asyncTest('Ausencia de ruta entre POIs', 1, function() {
    var response = apiResults(results);
    response.rows[1].elements[0].status = 'ERROR';

    DistanceMatrixServiceMock.setOptions({
      results: response
    });

    GMaps.getDistances(destinations, options, function(err, distances) {
      equal(distances[1][0], null);
      start();
    });
  });
});