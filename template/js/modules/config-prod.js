define(['modules/i18n', 'db/db'],
  function(i18n, Db) {

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
    assets: 'assets/'
  };

  // Inicia la BDD SQLite del dispositivo
  Db.initDb(window.sqlitePlugin.openDatabase({name: 'data'}));

  return function(callback) {
    document.addEventListener('deviceready', function () {

      // Obtener la plataforma y el locale
      window.appConfig.platform = device.platform;

      navigator.globalization.getLocaleName(function(locale) {
        locale = locale.value.match(/^([a-z]{2})/)[1];

        // i18n: se busca el idioma del dispositivo en los locales del app, tomando inglés como
        // fallback. AVISO: se asume que los locales del app y de los datos del catálogo son los mismos
        window.appConfig.locale = locale in i18n ? locale : 'en';
        window.res = i18n[window.appConfig.locale];

        callback();
      }, function(err) {
        // TO-DO: mejor error handling

        window.appConfig.locale = 'en';
        window.res = i18n[window.appConfig.locale];

        callback();
      });
    });
  };
});