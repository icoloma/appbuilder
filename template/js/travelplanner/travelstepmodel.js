/*
  Representa una etapa de un travel plan
*/
define(['globals'], function() {
  return B.Model.extend({
    // Lidia con el modelo anidado de un POI
    toJSON: function() {
      var json = B.Model.prototype.toJSON.call(this);
      json.poi = json.poi.toJSON();
      return json;
    }
  });
});