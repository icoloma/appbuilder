define(['globals'],
function() {
  return B.Model.extend({
    toJSON: function() {
      var json = B.Model.prototype.toJSON.apply(this);
      if (json.menu) {
        json.url = '#/menu/' + json.menu;
      } else {
        var uriObj = {
          query: json.query,
          title: json.label
        };
        json.url = '#/pois?' + encodeURIComponent(JSON.stringify(uriObj));
      }
      return json;
    }
  });
});