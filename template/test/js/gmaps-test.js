define(['modules/gmaps'], function(GMaps) {

  // Un mock para el DistanceMatrixService
  var DistanceMatrixServiceProvider = function() {
    var constructor = function() {
      return _.extend(this, distanceMatrixService);
    }
    ;
    constructor.setConfig = function(config) {
      constructor.prototype = {config: config}; 
    }
    return constructor;
  }
  , distanceMatrixService = {
    getDistanceMatrix: function(options, callback) {
      if (this.config.error) {
        callback(null, this.config.error);
        if (this.config.failure) {
          this.config.failure();
        }
        return;
      }
      return callback(this.config.results, 'OK');
    }
  }
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
  ;

  var destinations = [
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
      GMaps.maps.DistanceMatrixService = new DistanceMatrixServiceProvider();
    },
    teardown: function() {
      GMaps.maps = GMaps._oldMaps;
      delete GMaps._oldMaps;
    }
  });

  asyncTest('Error en la petición a GMaps', 1, function() {
    GMaps.maps.DistanceMatrixService.setConfig({
      error: 'SOME_ERROR'
    });

    GMaps.getDistances(destinations, options, function(err, distances) {
      equal(err.code, GMaps.DISTANCEMATRIX_ERROR, 'DISTANCEMATRIX_ERROR');
      start();
    });
  });

  asyncTest('Parseo de resultados', 1, function() {
    GMaps.maps.DistanceMatrixService.setConfig({
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

    GMaps.maps.DistanceMatrixService.setConfig({
      results: response
    });

    GMaps.getDistances(destinations, options, function(err, distances) {
      equal(distances[1][0], null);
      start();
    });
  });
});