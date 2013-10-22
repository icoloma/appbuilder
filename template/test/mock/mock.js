
_ = require('underscore')
, locales = ['en', 'es', 'it', 'de', 'fr']
, path = require('path')  
, fs = require('fs')  
;

var random = require('./random-data.js')
, i18n = require('./i18n-generator.js')
, catalogConfig = require('./catalogconfig-mocker.js')
, poiNumber = 50
, jsonPois
, jsonData = {}
, configFolder = path.join(__dirname, '/../mocked-data')
, dumpFolder = path.join(configFolder, '/dump')
, jsonFile = 'catalog-dump-config.json'
, sqliteFile = 'catalog-dump.db'
, metadataFolder = path.join(configFolder, 'app-metadata')
, metadataFile = 'app-metadata.json'
;

// Exporta los metadatos
jsonData.flags = catalogConfig.flags;
jsonData.flagGroups = catalogConfig.flagGroups;
jsonData.types = catalogConfig.types;
jsonData.data = catalogConfig.data;

if (!fs.existsSync(configFolder)) fs.mkdirSync(configFolder);

if (!fs.existsSync(dumpFolder)) fs.mkdirSync(dumpFolder);

// Genera los POIs
console.log('Generando ' + poiNumber + ' pois...');
if (fs.existsSync(path.join(dumpFolder, jsonFile))) fs.unlinkSync(path.join(dumpFolder, jsonFile));
jsonData._pois_dev = require('./catalog-json-mocker.js')(poiNumber);

jsonData._starredCounter_dev = 0;
_.each(jsonData._pois_dev, function(poi) {
  if (poi.starred > -1) {
    jsonData._starredCounter_dev++;
  }
});
jsonData._

// Genera la configuración de menús
console.log(' * Configuración de menús.');
_.extend(jsonData, require('./appconfig-mocker.js')(jsonData._pois_dev));

_.extend(jsonData, {
  schema: require('./schema.js'),
  _locales_dev: locales,
});

// Exporta el JSON
console.log(' * ' + jsonFile);
fs.writeFileSync(path.join(dumpFolder, jsonFile), JSON.stringify(jsonData, null, 2));

// Exporta metadatos
console.log(' * ' + metadataFile);
if (!fs.existsSync(metadataFolder)) fs.mkdirSync(metadataFolder);
if (fs.existsSync(path.join(metadataFolder, metadataFile)))
  fs.unlinkSync(path.join(metadataFolder, metadataFile)); 
fs.writeFileSync(path.join(metadataFolder, metadataFile), JSON.stringify({
  name: 'Visite Huesca',
  'package': 'com.segittur.visite_huesca',
  version: '0.1.0',
  gmaps_api_key: 'AIzaSyCjW-Jr88QxddY_iPdPGquPPfo60pp95_Y'
}, null, 2), 'utf-8');

// Genera una BDD SQLite
console.log(' * ' + sqliteFile);
if (fs.existsSync(path.join(dumpFolder, sqliteFile))) fs.unlinkSync(path.join(dumpFolder, sqliteFile));
require('./catalog-sqlite-mocker.js')(jsonData._pois_dev, path.join(dumpFolder, sqliteFile));
