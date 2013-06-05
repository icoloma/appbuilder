define(['globals', 'modules/geo'], function(Globals, Geo) {
  
  return B.Collection.extend({
    filterByDistanceTo: function(lat, lon, distance) {
      var filter = Geo.distanceFilter(lat, lon, distance);
      this.remove(
        this.filter(function(model) {
          return !filter(model.get('lat'), model.get('lon'));
        })
      );
      return this;
    }
  }, {
    // Devuelve un comparador por distancia al punto @lat,@lon
    sortByDistanceTo: function(lat, lon) {
      return function(model) {
        return model.propDistanceTo(lat, lon);
      };
    }
  });
});