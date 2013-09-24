define(['db/db', 'config/loaddb', 'modules/i18nUtils', 'poi/model'],
  function(Db, LoadDb, i18nUtils, PoiModel) {

  /*
    Configuración de la aplicación en desarrollo.
    Intenta cubrir todos los casos: navegador o nativo, desktop o móvil
    VÉASE config/config-prod
  */

  console.log('Configurando aplicación...'); //DEBUG

  window.res = {
    resources: 'resources/',
    dbName: 'catalog-dump',
    // Estos campos solo son necesarios en desarrollo para un browser
    _data_dev: 'test/mocked-data/',
    _configFolder_dev: 'config/'
  };

  // Búsqueda *síncrona* de la configuración y los recursos i18n
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


    var catalog_config = loadJSONResource(res._data_dev + 'dump/catalog-dump-config.json')
    , appMetadata = loadJSONResource(res._data_dev + 'app-metadata/app-metadata.json')
    , i18n_strings = loadJSONResource(res._configFolder_dev + 'i18n.json')
    , flagIcons = loadJSONResource(res._configFolder_dev + 'flag-icons.json')
    , appConfig = _.extend(i18n_strings, catalog_config)
    ;

    _.each(appConfig.flagGroups, function(group, id) {
      group.icon = flagIcons.flagIcons[id] || flagIcons.flagIcons.COMMON;
    });

    // Inicializa la BDD
    Db.initDb(openDatabase(res.dbName, 1, 'foobar', 5*1024*1024));

    _.extend(window.res, {
      platform: 'Android',
      locale: 'es',
      // Sobrescribimos la carpeta de resources
      resources: 'test/mocked-data/resources/',
      name: appMetadata.name,
      version: appMetadata.version
    });

    // Shim para el API de notificaciones de Phonegap
    navigator.notification = {
      alert: _.bind(alert, window)
    };

    return function(callback) {
      i18nUtils.config(_.extend(i18n_strings, catalog_config, flagIcons));
      PoiModel.initSchema(catalog_config.schema);

      // Rellenar la BDD SQL del navegador
      LoadDb(catalog_config._pois_dev, callback);
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

        var appConfig = loadJSONResource('app-config.json');

        // Inicia la BDD SQLite del dispositivo
        Db.initDb(window.sqlitePlugin.openDatabase({name: res.dbName}));

        // Obtener la plataforma y el locale
        window.res.platform = device.platform;

        navigator.globalization.getLocaleName(function(locale) {
          locale = locale.value.match(/^([a-z]{2})/)[1];

          // i18n: se busca el idioma del dispositivo en los locales del app, tomando inglés como
          // fallback. AVISO: se asume que los locales del app y de los datos del catálogo son los mismos
          window.res.locale = locale in appConfig.i18n ? locale : 'en';

          i18nUtils.config(appConfig);
          PoiModel.initSchema(res.schema);

          callback();
        }, function(err) {
          // TO-DO: mejor error handling

          window.res.locale = 'en';

          i18nUtils.config(appConfig);
          PoiModel.initSchema(res.schema);

          callback();
        });
      });
    };
  }
});