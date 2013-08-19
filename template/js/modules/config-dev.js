define(['modules/i18n', 'db/db', 'db/loaddb', 'db/metadata'],
  function(i18n, Db, LoadDb, Metadata) {

  /*
    Configuración de la aplicación en desarrollo.
    Intenta cubrir todos los casos: navegador o nativo, desktop o móvil
    VÉASE modules/config-prod
  */

  console.log('Configurando aplicación...'); //DEBUG

  window.appConfig = {
    assets: 'test/assets/',
    dbName: 'appData',
    // Este campo no es necesario en producción
    data: 'test/data/',
  };

  // Búsqueda *síncrona* de los metadatos.
  var req = new XMLHttpRequest()
  , raw_metadata
  ;
  req.onload = function() {
    raw_metadata = JSON.parse(this.responseText);
  };
  req.open('get', appConfig.data + 'raw_metadata.json', false);
  req.send();

  // Detecta eventos 'touch', para distinguir si estamos en un dispositivo o en desktop
  var supportsTouch = (('ontouchstart' in window) ||
    window.DocumentTouch && document instanceof DocumentTouch);

  /*
    Dispara eventos 'tap' en un navegador desktop sin necesidad de simular eventos táctiles con
    las dev tools. 
    AVISO: usa un API moderna para disparar los eventos 'tap', no funcionará en IE
  */
  if (!supportsTouch) {
    var tapEvent = new CustomEvent('tap', {bubbles: true});
    document.addEventListener('click', function(e) {
      e.target.dispatchEvent(tapEvent);
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

    // Inicializa la BDD
    Db.initDb(openDatabase(appConfig.dbName, 1, 'foobar', 5*1024*1024));

    _.extend(window.appConfig, {
      platform: 'Android',
      locale: 'es'
    });

    // Shim para el API de notificaciones de Phonegap
    navigator.notification = {
      alert: _.bind(alert, window)
    };
  
    // Parsea los metadatos
    Metadata.initMetadata(raw_metadata);

    return function(callback) {
      window.res = i18n[window.appConfig.locale];
      // Rellenar la BDD SQL del navegador
      LoadDb(Metadata.pois, callback);
    };

  } else {

    /*
      Configuración para aplicación nativa
      Idéntica a modules/config-prod
    */

    return function(callback) {

      // Inicia la BDD SQLite del dispositivo
      Db.initDb(window.sqlitePlugin.openDatabase({name: appConfig.dbName}));

      console.log('Esperando el evento deviceready...'); //DEBUG

      document.addEventListener('deviceready', function () {

        console.log('deviceready nativo disparado!'); //DEBUG

        // Obtener la plataforma y el locale
        window.appConfig.platform = device.platform;

        navigator.globalization.getLocaleName(function(locale) {
          locale = locale.value.match(/^([a-z]{2})/)[1];

          // i18n: se busca el idioma del dispositivo en los locales del app, tomando inglés como
          // fallback. AVISO: se asume que los locales del app y de los datos del catálogo son los mismos
          window.appConfig.locale = locale in i18n ? locale : 'en';
          window.res = i18n[window.appConfig.locale];

          // Parsea los metadatos
          Metadata.initMetadata(raw_metadata);

          callback();
        }, function(err) {
          // TO-DO: mejor error handling

          window.appConfig.locale = 'en';
          window.res = i18n[window.appConfig.locale];

          callback();
        });
      });
    };
  }
});