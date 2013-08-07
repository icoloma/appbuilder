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
  /*
    Devuelve una lista de los campos de la entidad, filtrada por idioma
    AVISO: no devuelve campos 1:N o M:N
  */
  , localizedFields = function(entity, lang) {
      var fields = _.keys(entity.meta.fields)
      , regex = new RegExp('_' + lang + '$')
      ;
      fields = _.filter(fields, function(field) {
        return field.match(regex) || ! field.match(/_[a-z]{2}$/);
      });
      return fields.concat(['id']);
  },
  /*
    Copia el valor de los campos i18n correspondientes al idioma de la app
    usando claves sin idioma. e.g. 'name_fr' -> 'name'
  */
  localizedJSON = function(json, lang) {
    var regex = new RegExp('(.+)_' + lang)
    ;
    _.each(json, function(value, key) {
      var match = key.match(regex);
      if (match) {
        json[match[1]] = value;
      }
    });
    return json;
  }
  ;

  _.extend(persistence.QueryCollection.prototype, {
    /*
      Filtra la Query según el idioma de la aplicación
    */
    _localizedList: function(entity, cb) {
      return this.selectJSON(localizedFields(entity, window.appConfig.locale), cb);
    },
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
    /*
      Devuelve el resultado de una Query como un array JSON, con los campos i18n adaptados
      según localizedJSON 
    */
    asJSON: function(entity, callback) {
      this._localizedList(entity, function(results) {
        callback(results.map(function(item) {
          return localizedJSON(item, window.appConfig.locale);
        }));
      });
    },

    /*
      Devuelve el resultado de una Query como una Backbone.Collection, con los campos i18n adaptados
      según localizedJSON 
    */
    asCollection: function(entity, callback) {
      this._localizedList(entity, function(results) {
        callback(new B.Collection(
          results.map(function(item) {
            return new B.Model(localizedJSON(item, window.appConfig.locale));
          })
        ));
      });
    }
  });

  return {
    parseQuery: parseQuery,
    queryToString: queryToString,
    localizedJSON: function(entity) {
      return localizedJSON(entity.toJSON(), window.appConfig.locale);
    }
  };
});