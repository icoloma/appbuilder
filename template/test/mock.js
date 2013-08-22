
_ = require('underscore')
, locales = ['en', 'es', 'it', 'de', 'fr']
, fs = require('fs')  
;

var random = require('./mock/random-data.js')
, i18n = require('./mock/i18n-generator.js')
, poiMetadata = require('./mock/poi-metadata.js')
, poiNumber = 200
, jsonPois
, jsonData = {}
, dataFolder = __dirname + '/data/'
, jsonFile = 'raw_metadata.json'
, sqliteFile = 'appData.db'
;

// Exporta los flags y types
jsonData.flags = poiMetadata.flags;
jsonData.types = poiMetadata.types;
jsonData.data = poiMetadata.dataStrings;

if (!fs.existsSync(dataFolder)) fs.mkdirSync(dataFolder);

// Genera los POIs
console.log('Generando ' + poiNumber + ' pois...');
if (fs.existsSync(dataFolder + jsonFile)) fs.unlinkSync(dataFolder + jsonFile);
jsonData.pois = require('./mock/pois-json.js')(poiNumber);

// Genera la configuración de menús
console.log(' * Configuración de menús.');
jsonData.menuConfig = require('./mock/menu.js')(jsonData.pois);

// Exporta el JSON
console.log(' * ' + jsonFile);
fs.writeFileSync(dataFolder + jsonFile, JSON.stringify(jsonData, null, 2));

// Genera una BDD SQLite
console.log(' * ' + sqliteFile);
if (fs.existsSync(dataFolder + sqliteFile)) fs.unlinkSync(dataFolder + sqliteFile);
require('./mock/pois-sqlite.js')(jsonData.pois, dataFolder + sqliteFile);