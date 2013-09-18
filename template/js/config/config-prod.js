define(['db/db', 'modules/i18nUtils', 'poi/model'],
  function(Db, i18nUtils, PoiModel) {

  /*
    Configuración de la aplicación.
      - Inicia la BDD
      - Obtener detalles del dispositivo (locale, platform, etc).
      - Configura el idioma de la app.
  */

  /*
    window.appConfig contiene:
    - assets: la carpeta de assets (imágenes y demás)
    - locale: el idioma de la aplicación
    - platform: la plataforma en la que corre la app
  */
  window.appConfig = {
    assets: 'assets/',
    dbName: 'appData',
    locale: null,
    platform: null
  };

  return function(callback) {
    async.parallel({
      device: function(cb) {
        document.addEventListener('deviceready', function () {

          // Obtener la plataforma y el locale
          window.appConfig.platform = device.platform;

          // Inicia la BDD SQLite del dispositivo.
          Db.initDb(window.sqlitePlugin.openDatabase({name: appConfig.dbName}));

          navigator.globalization.getLocaleName(function(locale) {
            cb(null, locale);
          }, function(err) {
            // TO-DO: mejor error handling
            cb(null, {value: 'en'});
          });
        });
      },
      // Búsqueda *asíncrona* de los metadatos. 
      config: function(cb) {
        var req = new XMLHttpRequest();
        req.onload = function() {
          // TO-DO: mejor error handling
          cb(null, JSON.parse(this.responseText));
        };
        req.open('get', 'appMetadata.json', true);
        req.send();
      }
    }, function(err, results) {


      locale = results.device.value.match(/^([a-z]{2})/)[1];

      // i18n: se busca el idioma del dispositivo en los locales del app, tomando inglés como
      // fallback. AVISO: se asume que los locales del app y de los datos del catálogo son los mismos
      window.appConfig.locale = locale in results.config.i18n ? locale : 'en';

      /*
        Configura el global 'res'.
        El global 'res' contiene:
          * Los metadatos de la aplicación
          * Las cadenas i18n 
      */
      i18nUtils.config(results.config.i18n, results.config.metadata);

      /*
        Configura el schema de los POIs.
      */
      PoiModel.initSchema(results.config.metadata.schema);

      callback();
    });
  };
});