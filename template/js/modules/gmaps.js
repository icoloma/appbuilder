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

    isLoaded: function() {
      return window.google && google.maps && google.maps.version; 
    },

    // La carga del API de GMaps no puede usar requirejs: el primer script que se descarga se encarga
    // de bajar otros y llamar a un callback al final del proceso.
    // AVISO: solo puede llamarse cuando la configuración del app window.res está presente.
    load: function(cb) {
      // TO-DO: checkear si hay conexión

      window.tmp_loadGoogleMaps = function() {
        delete window.tmp_loadGoogleMaps;
        if (userIsWaiting) {
          cb(null);
        }
      };

      if (this.isLoaded()) return tmp_loadGoogleMaps();

      var userIsWaiting = true;

      var script = document.createElement('script');
      script.src = MAPS_API_URL + window.res.gmaps_api_key;
      document.body.appendChild(script);

      _.delay(function(self) {
        userIsWaiting = false;
        cb(new Error({
          code: self.TIMEOUT_ERROR
        }));
      }, TIMEOUT, this);
    }
  };

});