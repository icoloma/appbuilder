define(['db/db', 'modules/i18nUtils', 'poi/model', 'modules/gmaps'],
  function(Db, i18nUtils, PoiModel, GMaps) {

  /*
    Configuración de la aplicación.
      - Inicia la BDD
      - Obtener detalles del dispositivo (locale, platform, etc).
      - Configura el idioma de la app.


    window.res contiene toda la configuración de la aplicación, suma de:
      * La configuración "estática" en los config/*.json
      * La configuración del catálogo, 'catalog-dump-config.json'.
      * Los siguientes campos extra:
        - resources: la carpeta de resources (imágenes y demás)
        - dbName: el nombre de la BDD
        - locale: el idioma de la aplicación
        - platform: la plataforma en la que corre la app


    Se usa localStorage para guardar algunos datos persistentes:
     * starredCounter: el campo 'starred' de un POI es un entero que permite ordenar los favoritos según la
       preferencia a la hora de visitarlos. 'starredCounter' es un contador incremental con el valor necesario
       para incluir un nuevo bookmark al final del plan de viaje.
  */
  window.res = {
    resources: 'resources/',
    dbName: 'catalog-dump',
    locale: null,
    platform: null
  };

  if (!localStorage.getItem('starredCounter')) {
    localStorage.setItem('starredCounter', 0);
  }

  return function(callback) {
    async.parallel({
      device: function(cb) {
        document.addEventListener('deviceready', function () {

          // Obtener la plataforma y el locale
          window.res.platform = device.platform;

          // Inicia la BDD SQLite del dispositivo.
          Db.initDb(window.sqlitePlugin.openDatabase({name: res.dbName}));

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
        req.open('get', 'app-config.json', true);
        req.send();
      }
    }, function(err, results) {


      locale = results.device.value.match(/^([a-z]{2})/)[1];

      // i18n: se busca el idioma del dispositivo en los locales del app, tomando inglés como
      // fallback. AVISO: se asume que los locales del app y de los datos del catálogo son los mismos
      window.res.locale = locale in results.config.i18n ? locale : 'en';

      /*
        Configura el global 'res'.
        El global 'res' contiene:
          * Los metadatos de la aplicación
          * Las cadenas i18n 
      */
      i18nUtils.config(results.config);

      /*
        Configura el schema de los POIs.
      */
      PoiModel.initSchema(res.schema);

      /*
        Trata de cargar el API de GMaps
      */

      GMaps.load(function() {});

      callback();
    });
  };
});