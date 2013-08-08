define(['globals'], function() {
  var db = {}

  // Parsea una queryString tipo foo=bar&...
  , parseQuery = function(query) {
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

  // Parsea una query como un statement SQL
  , queryToSql = function(table, query) {
    var queryObj = parseQuery(query)
    , conditions = _.map(queryObj, function(value, cond) {
      return '`' + cond + '`="' + value + '",';
    }).join('').slice(0, -1)
    ;
    return _.template('SELECT * FROM {{table}} WHERE ({{conditions}})', {
      table: table,
      conditions: conditions
    });
  }

  // Parsea como Objects y localiza los resultados de una transacción SQL.
  // Los resultados *NO* están listos para consumirse (hay campos "JSON" con valores string)
  // P. Ej.: { img : "['img1.png', 'img2.png']" }
  , parseAndLocalizeResults = function(rows) {
    if (!rows.length) return [];
    var results = [];
    for (var i = 0; i < rows.length; i++) {
      results.push(
        // El objeto rows.item(i) es de solo lectura, así que lo clonamos
        localizedJSON(_.clone(rows.item(i)), window.appConfig.locale)
      );
    }
    return results;
  }

  // Encuentra los campos localizables en un json en el idioma @lang (tipo 'name_fr')
  // y los copia en una propiedad sin localizar (p.ej. 'name')
  , localizedJSON = function(json, lang) {
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

  // Métodos adicionales de la BDD
  _.extend(db, {

    // Una abreviatura para ejecutar una transacción y parsear sus resultados
    sql: function(sqlStr, params, callback) {
      this.transaction(function(tx) {
        tx.executeSql(sqlStr, params, function(tx, results) {
          callback(null, parseAndLocalizeResults(results.rows));
        });
      }, function(err) {
        callback(err, null);
      });
    },

    // Ejecuta una transacción y devuelve el resultado como una Backbone.Collection @coll
    sqlAsCollection: function(coll, sqlStr, params, callback) {
      this.sql(sqlStr, params, function(err, results) {
        callback(err, new coll(_.map(results, function(item) {
          return new coll.model(item);
        })));
      });
    },

    utils: {
      parseQuery: parseQuery,
      queryToSql: queryToSql,
    },

    /* 
      Inicializa la BDD.
      El objeto 'db' devuelto por requirejs es un proxy a la verdadera BDD.
      De otro modo, este módulo se enreda en dependencias circulares con 'modules/config'
    */
    initDb: _.once(function(dbObject) {
      this.transaction = function() {
        dbObject.transaction.apply(dbObject, arguments);
      };
    })
  });

  return db;
});