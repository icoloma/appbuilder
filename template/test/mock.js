
_ = require('underscore')
, locales = ['en', 'es', 'it', 'de', 'fr']
, fs = require('fs')  
;

var random = require('./mock/random-data.js')
, i18n = require('./mock/i18n-generator.js')
, flagsAndTypes = require('./mock/flags-and-types')
, poiNumber = 200
, jsonPois
, data = {}
, dataFolder = __dirname + '/data/'
, jsonFile = 'raw_metadata.json'
, sqliteFile = 'appData.db'
;

// Exporta los flags y types
data.flags = flagsAndTypes.flags;
data.types = flagsAndTypes.types;

if (!fs.existsSync(dataFolder)) fs.mkdirSync(dataFolder);

// Genera los POIs
console.log('Generando ' + poiNumber + ' pois...');
if (fs.existsSync(dataFolder + jsonFile)) fs.unlinkSync(dataFolder + jsonFile);
data.pois = require('./mock/pois-json.js')(poiNumber);

// Genera la configuración de menús
console.log(' * Configuración de menús.');
data.menuConfig = require('./mock/menu.js')(data.pois);

// Exporta el JSON
console.log(' * ' + jsonFile);
fs.writeFileSync(dataFolder + jsonFile, JSON.stringify(data, null, 2));

// Genera una BDD SQLite
console.log(' * ' + sqliteFile);
if (fs.existsSync(dataFolder + sqliteFile)) fs.unlinkSync(dataFolder + sqliteFile);
require('./mock/pois-sqlite.js')(data.pois, dataFolder + sqliteFile);