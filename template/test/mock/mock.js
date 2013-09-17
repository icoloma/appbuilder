
_ = require('underscore')
, locales = ['en', 'es', 'it', 'de', 'fr']
, fs = require('fs')  
;

var random = require('./random-data.js')
, i18n = require('./i18n-generator.js')
, poiMetadata = require('./poi-metadata.js')
, poiNumber = 200
, jsonPois
, jsonData = {}
, dataFolder = __dirname + '/../data/'
, jsonFile = 'catalog-metadata.json'
, sqliteFile = 'appData.db'
;

// Exporta los metadatos
jsonData.flags = poiMetadata.flags;
jsonData.flagGroups = poiMetadata.flagGroups;
jsonData.types = poiMetadata.types;
jsonData.data = poiMetadata.data;

if (!fs.existsSync(dataFolder)) fs.mkdirSync(dataFolder);

// Genera los POIs
console.log('Generando ' + poiNumber + ' pois...');
if (fs.existsSync(dataFolder + jsonFile)) fs.unlinkSync(dataFolder + jsonFile);
jsonData._pois_dev = require('./pois-json.js')(poiNumber);

// Genera la configuración de menús
console.log(' * Configuración de menús.');
_.extend(jsonData, require('./menu.js')(jsonData._pois_dev));

jsonData.schema = require('./schema.js');

jsonData._locales_dev = locales;

// Exporta el JSON
console.log(' * ' + jsonFile);
fs.writeFileSync(dataFolder + jsonFile, JSON.stringify(jsonData, null, 2));

// Genera una BDD SQLite
console.log(' * ' + sqliteFile);
if (fs.existsSync(dataFolder + sqliteFile)) fs.unlinkSync(dataFolder + sqliteFile);
require('./pois-sqlite.js')(jsonData._pois_dev, dataFolder + sqliteFile);