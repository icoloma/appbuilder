define(['globals', 'modules/geo'], function(Globals, Geo) {
  return B.Model.extend({
    propDistanceTo: function(lat, lon) {
      return Geo.propDistance(this.get('lat'), this.get('lon'), lat, lon);
    }
  });
});