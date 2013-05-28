define(['lib/persistence'], function() {
  // QueryCollection.query permite especificar varios filtros 
  // en un solo objeto @queryObject
  // OJO: de momento solo parseamos filtros '='
  persistence.QueryCollection.prototype.query = function(queryObject) {
    var queryCollection = this;
    for (var field in queryObject) {
      queryCollection = queryCollection.filter(field, '=', queryObject[field]);
    }
    return queryCollection;
  };
});