define(function() {

  /*
    Configuración de la aplicación en desarrollo.
    Intenta cubrir todos los casos: navegador o nativo, desktop o móvil
  */

  window.appConfig = {
    assets: 'test/assets/',
    data: 'test/data/',
    platform: window.device ? device.platform : 'Android'
  };

  // Detecta eventos 'touch', para distinguir si estamos en un dispositivo o en desktop
  var supportsTouch = (('ontouchstart' in window) ||
    window.DocumentTouch && document instanceof DocumentTouch);
  
  /*
    Dispara eventos 'tap' en un navegador desktop sin necesidad de simular eventos táctiles con
    las dev tools. 
    OJO: usa un API moderna para disparar los eventos 'tap', no funcionará en IE
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

  // En desarrollo, si estamos probando la aplicación como una página web en remoto
  // (en el navegador del desktop, o en el dispositivo sin compilar la aplicación nativa)
  // el API de Phonegap no está disponible, por lo que añadimos una configuración por defecto.
  return function(callback) {
    // Evita llamar al callback más de una vez
    var once = (function() {
      var done = false;
      return function() {
        if (!done) {
          done = true;
          callback();
        }
      };
    })();

    document.addEventListener('deviceready', function () {
      navigator.globalization.getLocaleName(function(locale) {
        window.appConfig.locale = locale.value.match(/^([a-z]{2})/)[1];
        once();
      }, function(err) {
        // TODO: mejor error handling
        window.appConfig.locale = 'en';
      });
    });

    // Un timeout con una configuración por defecto
    // OJO: supone que siempre se levanta un servidor local para depurar en el navegador
    if (location.protocol === 'http:' || location.protocol === 'https:' ) {
      setTimeout(function() {
        window.appConfig.locale = 'es';
        once();
      }, 500);
    }
  };
});