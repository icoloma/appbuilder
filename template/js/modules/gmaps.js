/*
  Módulo para cargar el API de Google Maps.
*/
define(['globals'], function() {

  // TO-DO: mirar el GPS
  var MAPS_API_URL =
      'https://maps.googleapis.com/maps/api/js?&sensor=false&callback=tmp_loadGoogleMaps&key='
  , TIMEOUT = 15000
  
  ;

  return {

    TIMEOUT_ERROR: 'GMAPS_TIMEOUT_ERROR',

    DISTANCEMATRIX_ERROR: 'GMAPS_DISTANCEMATRIX_ERROR',

    isLoaded: function() {
      return this.maps && this.maps.version; 
    },


    /*
      Carga el API de Google maps.
      Los métodos del API se copian a este módulo, para facilitar el testeo.

      @cb es un callback que se llama si hay un error de timeout (pasando un err.code == GMaps.TIMEOUT_ERROR)
      o con null si todo ha ido bien.

      AVISO: La carga del API de GMaps no puede usar requirejs: el primer script que se descarga se encarga
      de bajar otros y llamar a un callback al final del proceso.

      AVISO: solo puede llamarse cuando la configuración del app window.res está presente.

      AVISO: define un global 'google'.
    */
    load: function(cb) {
      // TO-DO: checkear si hay conexión
      var userIsWaiting = true
      , self = this
      ;

      window.tmp_loadGoogleMaps = function() {
        delete window.tmp_loadGoogleMaps;
        // Copia el API en este modulo.
        // No se puede borrar el window.google: parece que el script de Google sigue haciendo cosas
        // incluso despues de llamar a este callback. Mientras solo se use google.maps, esta copia
        // se mantendrá actualizada.
        self.maps = google.maps;

        if (userIsWaiting) {
          cb(null);
        }
      };

      if (this.isLoaded()) return tmp_loadGoogleMaps();

      var script = document.createElement('script');
      script.src = MAPS_API_URL + window.res.gmaps_api_key;
      document.body.appendChild(script);

      _.delay(function(self) {
        userIsWaiting = false;
        cb(new Error({
          code: self.TIMEOUT_ERROR
        }));
      }, TIMEOUT, this);
    },

    /*
      Usa el API de GMaps para obtener la matriz de distancias de la lista de POIs @pois.

      @options.transportation es el medio de trasporte utilizado.

      @callback se llama con (err, null) si hay un error, y con (null, distaceMatrix) en caso contrario.
      distanceMatrix es una matriz de "objetos distancia":
      {
        distance: (distancia en metros),
        duration: (duración en segundos)
      } 
    */
    getDistances: function(destinations, options, callback) {
      var service = new this.maps.DistanceMatrixService()
      , self = this
      ;

      destinations = _.map(destinations, function(dest) {
        return new this.maps.LatLng(dest.lat, dest.lon);
      }, this);

      service.getDistanceMatrix({
        origins: destinations,
        destinations: destinations,
        travelMode: self.maps.TravelMode[options.transportation]
      }, function(response, status) {
        if (status !== 'OK') {
          var err = new Error();
          err.code = self.DISTANCEMATRIX_ERROR;
          return callback(err, null);
        }

        var distanceMatrix = [];

        // Parsea los resultados en una distanceMatrix
        for (var i = 0; i < response.rows.length; i++) {

          distanceMatrix.push([]);

          for (var j = 0; j < response.rows[i].elements.length; j++) {
            var element = response.rows[i].elements[j];

            if (element.status !== 'OK') {
              distanceMatrix[i].push(null);
            } else {
              distanceMatrix[i].push({
                distance: element.distance.value,
                duration: element.duration.value
              });
            }
          }
        }

        callback(null, distanceMatrix);
      });
    },

  };
});