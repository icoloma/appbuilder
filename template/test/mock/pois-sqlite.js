/*
  Carga los POIs en una BDD SQLite
*/
var sqlite3 = require('sqlite3').verbose();

// Schema de un POI. Duplicado de db/schemas/poi
var poiSchema = {

  id: 'VARCHAR(32) PRIMARY KEY',
  type: 'TEXT',
  name_es: 'TEXT', name_en: 'TEXT', name_de: 'TEXT', name_it: 'TEXT', name_fr: 'TEXT',
  desc_es: 'TEXT', desc_en: 'TEXT', desc_de: 'TEXT', desc_it: 'TEXT', desc_fr: 'TEXT',

  /*CAMPOS JSON*/
  prices: 'TEXT',
  contact: 'TEXT',
  timetables: 'TEXT',
  languages: 'TEXT',
  data: 'TEXT',
  flags: 'TEXT',
  /* */

  address: 'TEXT',
  created: 'DATE', lastModified: 'DATE',
  lat: 'REAL', lon: 'REAL', normLon: 'REAL',
  thumb: 'TEXT', imgs: 'TEXT',
  starred: 'BOOLEAN'
};


module.exports = function(pois, dbFile) {
  var db = new sqlite3.Database(dbFile)
  , poi
  , pairs, fields, values
  ;


  pairs = _.pairs(poiSchema);
  fields = _.zip.apply(_, pairs)[0];
  values = _.zip.apply(_, pairs)[1];

  db.serialize(function() {

    db.run('CREATE TABLE IF NOT EXISTS Poi (' + 
      _.map(pairs, function(pair) {
        return pair[0] + ' ' + pair[1] + ',';
      }).join('').slice(0, -1) +
      ')'
    );

    var smnt = db.prepare('INSERT INTO Poi VALUES (' +
      _.map(poiSchema, function() {
        return '?,';
      }).join('').slice(0, -1) +
      ')'
    );

    for (var i = 0; i < pois.length; i++) {
      poi = pois[i];

      _.each([
      'prices', 'contact', 'timetables', 'languages', 'data', 'flags', 'imgs'
      ], function(field) {
        poi[field] = JSON.stringify(poi[field]);
      });

      smnt.run(
        _.map(pairs, function(pair) {
          return poi[pair[0]];
        })
      );
    }
    smnt.finalize();
  });

  db.close();
};