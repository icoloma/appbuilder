
/* 
  Introduce los datos en la BDD WebSQL
  Se llama al inicio de la app, en un navegador.
*/

define(
  ['db/db', 'poi/model'],
  function(Db, PoiModel) {
    // Introduce los datos en la BDD.
    var loadData = function(pois, callback) {
      var poiSchema = _.clone(res._metadata.schema)
      , pairs
      ;

      _.each(poiSchema, function(type, field) {
        if (type === 'JSON' ) {
          poiSchema[field] = type;
        } else if (type === 'i18n') {
          _.each(res._metadata._locales_dev, function(locale) {
            poiSchema[field + '_' + locale] = type;
          });
          delete poiSchema[field];
        }
      });

      // Parejas clave/valor *ordenadas* (iterar sobre Poi directamente no asegura ningún orden)
      pairs = _.pairs(poiSchema);

      Db.transaction(function(tx) {
        var i
        // (?, ?, ..., ?)
        , placeholder = '(' + 
          _.map(poiSchema, function() {
            return '?,';
          }).join('').slice(0, -1) +
          ')'
        , values = function(poi) {
          var row = (new PoiModel(poi, {parse: true})).toRow();
          return _.map(pairs, function(entry) {
            return row[entry[0]];
          });
        }
        ;

        tx.executeSql(
          'CREATE TABLE Poi (' +
          _.map(pairs, function(entry) {
            return entry[0] + ' ' + entry[1] + ',';
          }).join('').slice(0, -1) +
          ')'
        );

        for (i = 0; i < pois.length; i++) {
          tx.executeSql('INSERT INTO Poi VALUES' + placeholder, values(pois[i]));
        }
      }, function(e) {
        console.log(e);
      }, callback);

    };

    return function(pois, callback) {
      // Recarga la BDD siempre, después de cada refresh, etc.
      Db.transaction(function(tx) {
        tx.executeSql('DROP TABLE IF EXISTS Poi', [], function(tx, res) {
          console.log('Cargando la base de datos'); //DEBUG
          loadData(pois, callback);
        });
      });
    };
  }
);