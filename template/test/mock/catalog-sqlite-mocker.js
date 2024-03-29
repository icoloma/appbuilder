/*
  Carga los POIs en una BDD SQLite
*/
var sqlite3 = require('sqlite3').verbose()
, poiSchema = require('./schema.js')
, originalSchema = _.clone(poiSchema)
;

_.each(poiSchema, function(value, field) {
  if (value == 'i18n') {
    _.each(locales, function(locale) {
      poiSchema[field + '_' + locale] = 'TEXT';
    });
    delete poiSchema[field];
  } else if (value === 'JSON') {
    poiSchema[field] = 'TEXT';
  } else if (value === 'BOOLEAN'){
    poiSchema[field] = 'INTEGER';
  }
});

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

      _.each(originalSchema, function(type, field) {
        if (type === 'JSON') {
          poi[field] = JSON.stringify(poi[field]);
        } else if (type === 'BOOLEAN') {
          poi[field] = poi[field] ? 1 : 0;
        }
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