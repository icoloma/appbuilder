define(['globals'],
function() {
  return B.Model.extend({
    toJSON: function() {
      var json = B.Model.prototype.toJSON.apply(this);
      if (!json.url) {
        var uriObj = {
          queryConditions: json.queryConditions,
          title: json.label
        };
        json.url = '#/pois?' + encodeURIComponent(JSON.stringify(uriObj));
      }
      return json;
    }
  });
});