define(['globals', 'modules/geo'], function(Globals, Geo) {

  var GeolocationMock = function() {
    this.options = {};
  };

  /*
    @options: las opciones para ejecutar la geolocalización.
      
      @options.results: el resultado de la geoloc., por defecto un resultado vacío.

      @options.success: un callback opcional para llamar tras una geoloc. exitosa.
   
      @options.error: un error opcional, que marca la geoloc. como fallida.

      @options.failure: un callback opcional para después de una geoloc. fallida.
  
  */

  GeolocationMock.prototype = {
    getCurrentLocation: function(callback, errback) {
      if (this.options.error) {
        errback(err);
        if (this.options.failure) this.options.failure();
      } else {
        callback(this.options.coords || {
          coordinates: {
            latitude: 40,
            longitude: 0
          }
        });
        this.options.success();
      }
    }
  };

  var geolocationMock = new GeolocationMock();

  Geo.getCurrentLocation = _.bind(geolocationMock.getCurrentLocation, geolocationMock);
  return geolocationMock;
})
