define(['globals'], function() {

  // var minRegex = /^min-(.+)/
  // , maxRegex = /^max-(.+)/
  // ;
  var parseQuery = function(query) {
    var queryObject = {}
    , pattern = /(.+)=(.+)/
    ;
    query.split('&').forEach(function(param) {
      var parts = param.match(pattern);
      if (parts) {
        queryObject[parts[1]] = parts[2];
      }
    });
    return queryObject;
  }
  , queryToString = function(queryObj) {
    var queryStr = '';
    _.each(queryObj, function(value, param) {
      queryStr = param + '=' + value + '&';
    });
    return queryStr;
  }
  ;

  _.extend(persistence.QueryCollection.prototype, {
    /*
      QueryCollection.query permite especificar varios filtros en un solo objeto @queryObject
      @queryObject: un hash de filtros nombre/valor. Por defecto aplica un filtro '='.

    */
    query: function(queryObject) {
      var queryCollection = this
      // , fieldName
      ;
      for (var field in queryObject) {
        // if (field.match(minRegex) && (fieldName = field.match(minRegex)[1])) {
        //   queryCollection = queryCollection.filter(fieldName, '>=', queryObject[field]);
        // } else if (field.match(maxRegex) && (fieldName = field.match(maxRegex)[1])) {
        //   queryCollection = queryCollection.filter(fieldName, '<=', queryObject[field]);
        // } else {
        queryCollection = queryCollection.filter(field, '=', queryObject[field]);
        // }
      }
      return queryCollection;
    },

    asJSON: function(callback) {
      this.list(function(results) {
        callback(results.map(function(item) {
          return item.toJSON();
        }));
      });
    },

    asCollection: function(callback) {
      this.list(function(results) {
        callback(new B.Collection(
          results.map(function(item) {
            return new B.Model(item.toJSON());
          })
        ));
      });
    }
  });

  return {
    parseQuery: parseQuery,
    queryToString: queryToString
  };
});