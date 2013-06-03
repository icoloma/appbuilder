define(function() {
  window.appConfig = {
    assets: 'test/assets/',
    data: 'test/data/'
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
        // TODO: error handling
      });
    });

    // Un timeout con una configuración por defecto
    // OJO: supone que siempre se levanta un servidor local para depurar
    if (location.protocol === 'http:' || location.protocol === 'https:' ) {
      setTimeout(function() {
        window.appConfig.locale = 'es';
        once();
      }, 500);
    }
  };
});