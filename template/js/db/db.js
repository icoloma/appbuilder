define(['modules/i18nUtils'], function(i18nUtils) {
  // Parsea una query como un statement SQL
  var queryToSql = function(table, queryObj) {
    var conditions = _.map(queryObj, function(value, cond) {
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
        i18nUtils.localizeJSON(_.clone(rows.item(i)))
      );
    }
    return results;
  }
  ;

  return {
    // Una abreviatura para ejecutar una transacción y parsear sus resultados
    sql: function(sqlStr, params, callback) {
      this.transaction(function(tx) {
        tx.executeSql(sqlStr, params, function(tx, results) {
          callback(null, parseAndLocalizeResults(results.rows));
        }, function(tx, err) {
         callback(err, null);
        });
      });
    },

/*    // Ejecuta una transacción y devuelve el resultado como una Backbone.Collection @coll
    sqlAsCollection: function(coll, sqlStr, params, callback) {
      this.sql(sqlStr, params, function(err, results) {
        callback(err, new coll(_.map(results, function(item) {
          return new coll.model(item);
        })));
      });
    },
*/
    /* 
      Inicializa la BDD, de modo que el módulo funciona como un proxy a la verdadera BDD.
      (De otro modo, este módulo se enreda en dependencias circulares con 'config/config')
      Este método *sólo tiene efecto una vez*. Para testing, Db.transaction se sobrescribe a mano.
    */
    initDb: _.once(function(dbObject) {
      this.transaction = function() {
        dbObject.transaction.apply(dbObject, arguments);
      };
    })
  };
});