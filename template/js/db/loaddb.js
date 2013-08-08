
/* 
  Introduce los datos en la BDD WebSQL
  Se llama al inicio de la app, en un navegador.
*/

define(
  ['db/db', 'db/schemas/poi', 'poi/model'],
  function(Db, PoiSchema, PoiModel) {
    // Introduce los datos en la BDD. Se devuelve como un callback para la
    // petición "AJAX" de los datos json
    var loadData = function(callback) {
      return function() {
        var json = JSON.parse(this.responseText)
        // Parejas clave/valor *ordenadas* (iterar sobre Poi directamente no asegura ningún orden)
        , pairs = _.pairs(PoiSchema)
        ;

        Db.transaction(function(tx) {
          var i
          // (?, ?, ..., ?)
          , placeholder = '(' + 
            _.map(PoiSchema, function() {
              return '?,';
            }).join('').slice(0, -1) +
            ')'
          , values = function(poi) {
            var row = (new PoiModel(poi)).toRow();
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

          for (i = 0; i < json.pois.length; i++) {
            tx.executeSql('INSERT INTO Poi VALUES' + placeholder, values(json.pois[i]));
          }
        }, function(e) {
          console.log(e);
        }, callback);
      };
    };

    return function(callback) {
      var req = new XMLHttpRequest();
      req.onload = loadData(callback);
      // Comprueba si ya la BDD ya está cargada (después de F5 por ejemplo)
      Db.transaction(function(tx) {
        tx.executeSql(
          'SELECT name FROM sqlite_master WHERE type="table" AND name="Poi"',
          [], function(tx, res) {
            if (res.rows.length) {
              callback();
            } else {
              console.log('Cargando la base de datos'); //DEBUG
              req.open('get', window.appConfig.data + 'data.json', true);
              req.send();
            }
        });
      });
    };
  }
);