var sqlite3 = require('sqlite3').verbose()
, fs = require('fs')
, _ = require('underscore')
, seed = require('seed-random')


, db, json = {}
, jsonFile = __dirname + '/data/data.json'
, dbFile = __dirname + '/data/data.db'
, languages = ['en', 'es', 'de', 'fr', 'it']
, name = 'lorem ipsum place'
, desc = 'Proin vehicula nisl ac libero blandit, nec feugiat odio facilisis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer a porttitor purus, a vulputate nisl. Morbi eros diam, lacinia a faucibus sed, elementum mattis orci.'
, types = [ 
  'BEACH', 'NATURAL_SPACE', 'HOTEL', 'CAMPING', 'APARTMENT', 'MUSEUM', 'MONUMENT',
  'PARK_GARDEN', 'ECO_TOURISM', 'GOLF', 'NAUTICAL_STATION',
  ]
, pois = 100


// Campos i18n para la generación de la tabla
, i18nField = function(field) {
  return languages.map(function(lang) {
    return field + '_' + lang;
  });
}

// Valores i18n para insertar en la tabla
, i18nValue = function(val) {
  return languages.map(function(lang) {
    return lang.toUpperCase() + ': ' + val;
  });
}

// Extraido de github.com/zefhemel/persistencejs/blob/7b34341a6027284b635bb97ba7059848adfe685a/lib/persistence.js#L1259 
// Math.random sustituido por una función determinista

, createUUID = function() {
  var s = []
  , hexDigits = "0123456789ABCDEF"
  ;
  for ( var i = 0; i < 32; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
  }
  s[12] = "4";
  s[16] = hexDigits.substr((s[16] & 0x3) | 0x8, 1);

  var uuid = s.join("");
  return uuid;
}
;

// Sobrescribe Math.random
seed('Secret seed', true);

if (fs.existsSync(dbFile)) fs.unlinkSync(dbFile);
if (fs.existsSync(jsonFile)) fs.unlinkSync(jsonFile);

/* Generar POIs en json */
json.pois = [];
var poi;
for (var i = 0; i < pois; i++) {
  poi = {
    id: createUUID(),
    created: 100000,
    lastModified: 100000,
    lat: 40 + (i % 20)/10,
    lon: -1 + (i % 21)/10,
    normLon: Math.sin((40 + (i % 20)/10)/180*Math.PI) * (-1 + (i % 21)/10),
    type: types[ i % types.length ],
    prices: {
      foo: 'bar'
    },
    contact: {
      bar: 'baz'
    },
    timetables: {
      baz: 'foo'
    },
    languages: ['es', 'en'],
    data: {
      'Staff gender ratio': 1,
      'Chairs/tables ratio': 0.1 
    },
    address: 'Plaza de Sol, Madrid',
    flags: {
      ACCESSIBILITY: [
        'GUIDE_DOG_ALLOWED', 'LIFT_ACCESIBLE'
      ],
      COMMON: [
        'AIR_CONDITIONING', 'PARKING'
      ]
    },
    thumb: 'thumb.png',
    imgs: [ 'img1.png', 'img2.png', 'img3.png' ],
    starred: false
  };
  i18nField('name').forEach(function(field) {
    poi[field] = name;
  });
  i18nField('desc').forEach(function(field) {
    poi[field] = desc;
  });
  json.pois[i] = poi;
}
fs.writeFileSync(jsonFile, JSON.stringify(json, null, 2));


/* Generar POIs en sqlite */
db = new sqlite3.Database(dbFile);
db.serialize(function() {
  db.run('CREATE TABLE IF NOT EXISTS Pois (\
    id VARCHAR(32) PRIMARY KEY, \
    type TEXT, ' +
    i18nField('name').map(function(field) {
      return '`' + field + '` TEXT,';
    }).join('') +
    i18nField('desc').map(function(field) {
      return '`' + field + '` TEXT,';
    }).join('') +
    [
      'prices', 'address', 'contact', 'timetables', 'languages', 'data', 'flags'
    ].map(function(field) {
      return '`' + field + '` TEXT, ';
    }).join('') +
    'created DATE, lastModified DATE, \
    lat numeric, lon numeric, normLon numeric, \
    thumb TEXT, imgs TEXT, \
    starred BOOLEAN \
    )');

  var smnt = db.prepare('INSERT INTO Pois VALUES \
    (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)')
  ;
  for (var i = 0; i < pois; i++) {
    poi = json.pois[i];
    [
    'prices', 'contact', 'timetables', 'languages', 'data', 'flags', 'img'
    ].forEach(function(field) {
      poi[field] = JSON.stringify(poi[field]);
    });
    smnt.run.apply(smnt, [].concat(
      poi.id, poi.type,
      i18nField('name').map(function(field) {
        return poi[field];
      }),
      i18nField('desc').map(function(field) {
        return poi[field];
      }),
      [ 'prices', 'address', 'contact', 'timetables', 'languages', 'data', 'flags',
        'created', 'lastModified', 'thumb', 'imgs', 'lat', 'lon', 'normLon', 'starred'
      ].map(function(field) {
        return poi[field];
      })
    ));
  }
  smnt.finalize();

});

db.close();