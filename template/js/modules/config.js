define(function() {

  /*
    window.appConfig contiene todos los detalles de configuración de la aplicación:
    - assets: la carpeta de assets (imágenes y demás)
    - data: la carpeta con los datos volcados del catálogo
    - locale: el idioma de la aplicación
    - zone: el nombre de la región de la aplicación, en el locale apropiado
  */

  window.appConfig = {
    assets: 'assets/',
    data: 'data/',
    platform: device.platform
  };

  return function(callback) {
    document.addEventListener('deviceready', function () {
      navigator.globalization.getLocaleName(function(locale) {
        window.appConfig.locale = locale.value.match(/^([a-z]{2})/)[1];
        callback();
      }, function(err) {
        // TODO: mejor error handling
        window.appConfig.locale = 'en';
      });
    });
  };
});