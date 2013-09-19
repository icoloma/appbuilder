define(['db/db', 'config/loaddb', 'modules/i18nUtils', 'poi/model'],
  function(Db, LoadDb, i18nUtils, PoiModel) {

  /*
    Configuración de la aplicación en desarrollo.
    Intenta cubrir todos los casos: navegador o nativo, desktop o móvil
    VÉASE config/config-prod
  */

  console.log('Configurando aplicación...'); //DEBUG

  window.appConfig = {
    assets: 'assets/',
    dbName: 'appData',
    // Estos campos solo son necesarios en desarrollo para un browser
    data: 'test/mocked-data/',
    configFolder: 'config/'
  };

  // Búsqueda *síncrona* de los metadatos y los recursos i18n
  var loadJSONResource = function(url) {
    var req = new XMLHttpRequest()
    , result
    ;
    req.onload = function() {
      result = JSON.parse(this.responseText);
    };
    req.open('get', url, false);
    req.send();
    return result;
  };

  // Detecta eventos 'touch', para distinguir si estamos en un dispositivo o en desktop
  var supportsTouch = (('ontouchstart' in window) ||
    window.DocumentTouch && document instanceof DocumentTouch);

  /*
    Dispara eventos 'tap' en un navegador desktop sin necesidad de simular eventos táctiles con
    las dev tools. 
  */
  if (!supportsTouch) {
    var tapEvent = new CustomEvent('tap', {bubbles: true});
    document.addEventListener('click', function(e) {
      e.target.dispatchEvent(tapEvent);

      // AVISO: esto no debería usarse con la history del router
      if (e.target.tagName === 'A') {
        window.location = e.target.href;
      }
    });
  }

  /*
    En desarrollo, si estamos probando la aplicación como una página web en remoto
    (en el navegador del desktop o de un dispositivo) el API de Phonegap no está disponible,
    por lo que añadimos una configuración por defecto.
    AVISO: supone que siempre se levanta un servidor local para depurar en el navegador
  */
  if (location.protocol === 'http:' || location.protocol === 'https:') {
    console.log('Simulando evento deviceready!'); //DEBUG

    console.warn(
      '%cLa navegación de la app NO usa el API nativa de history del navegador. ' +
      'Si has refrescado en una página distinta a la home, el botón "back" dará errores en modules/router.',
      'color: #c00; font-weight: bold'); //DEBUG


    var catalog_metadata = loadJSONResource(appConfig.data + 'catalog-metadata.json')
    , i18n_strings = loadJSONResource(appConfig.configFolder + 'i18n.json')
    , flagIcons = loadJSONResource(appConfig.configFolder + 'flag-icons.json')
    ;

    // Inicializa la BDD
    Db.initDb(openDatabase(appConfig.dbName, 1, 'foobar', 5*1024*1024));

    _.extend(window.appConfig, {
      platform: 'Android',
      locale: 'es',
      // Sobrescribimos la carpeta de assets
      assets: 'test/mocked-data/assets/'
    });

    // Shim para el API de notificaciones de Phonegap
    navigator.notification = {
      alert: _.bind(alert, window)
    };

    return function(callback) {
      i18nUtils.config(_.extend(i18n_strings, catalog_metadata, flagIcons));
      PoiModel.initSchema(catalog_metadata.schema);

      // Rellenar la BDD SQL del navegador
      LoadDb(catalog_metadata._pois_dev, callback);
    };

  } else {

    /*
      Configuración para aplicación nativa
      Idéntica a modules/config-prod
    */

    return function(callback) {

      console.log('Esperando el evento deviceready...'); //DEBUG

      document.addEventListener('deviceready', function () {

        console.log('deviceready nativo disparado!'); //DEBUG

        var appMetadata = loadJSONResource('appMetadata.json');

        // Inicia la BDD SQLite del dispositivo
        Db.initDb(window.sqlitePlugin.openDatabase({name: appConfig.dbName}));

        // Obtener la plataforma y el locale
        window.appConfig.platform = device.platform;

        navigator.globalization.getLocaleName(function(locale) {
          locale = locale.value.match(/^([a-z]{2})/)[1];

          // i18n: se busca el idioma del dispositivo en los locales del app, tomando inglés como
          // fallback. AVISO: se asume que los locales del app y de los datos del catálogo son los mismos
          window.appConfig.locale = locale in appMetadata.i18n ? locale : 'en';

          i18nUtils.config(appMetadata);
          PoiModel.initSchema(res.schema);

          callback();
        }, function(err) {
          // TO-DO: mejor error handling

          window.appConfig.locale = 'en';
          window.res = appMetadata.i18n[window.appConfig.locale];

          i18nUtils.config(appMetadata);
          PoiModel.initSchema(res.schema);

          callback();
        });
      });
    };
  }
});