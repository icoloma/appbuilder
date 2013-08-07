define(['globals', 'modules/geo', 'schemas/poi'], function(Globals, Geo, Poi) {
  return B.Model.extend({
    propDistanceTo: function(lat, lon) {
      return Geo.propDistance(this.get('lat'), this.get('lon'), lat, lon);
    },
    geoLink: function() {
      if (window.appConfig.platform.match(/ios/i)) {
        // TO-DO: testear!!
        return 'http//:maps.apple.com/?ll=' + this.get('lat') + ',' + this.get('lon') + '&z=17';
      } else {
        // Android como valor por defecto
        return 'geo:' + this.get('lat') + ',' + this.get('lon') + 
               '?q=' + this.get('lat') + ',' + this.get('lon') + 
               '(' + this.get('name') + ')';
      }
    },

    /* 
      Un pequeño shim para persistir cambios en un POI 
      https://github.com/icoloma/appbuilder/issues/29
    */
    persist: function(callback) {
      var self = this;
      var changed = this.changed;
      Poi.load(this.get('id'), function(poi) {
        if (poi) {
          _.extend(poi, changed);
          persistence.flush(_.bind(callback, self));
        } else {
          // TO-DO: error handling
        }
      });
    }
  });
});