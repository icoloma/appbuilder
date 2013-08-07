define(['globals'], function() {

  /*
    Configuración de la aplicación en desarrollo.
    Intenta cubrir todos los casos: navegador o nativo, desktop o móvil
  */

  console.log('Configuring application...'); //DEBUG
  window.appConfig = {
    assets: 'test/assets/',
    data: 'test/data/',
  };



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

  // En desarrollo, si estamos probando la aplicación como una página web en remoto
  // (en el navegador del desktop, o en el dispositivo sin compilar la aplicación nativa)
  // el API de Phonegap no está disponible, por lo que añadimos una configuración por defecto.
  return function(callback) {
    // Evita llamar al callback más de una vez (en teoría no debería pasar nunca)
    var once = _.once(function() {
      console.log('Deviceready fired!'); //DEBUG
      callback();    
    });

    console.log('Waiting for deviceready...'); //DEBUG
    document.addEventListener('deviceready', function () {
      // Reemplazar el API WebSQL del WebView por el API nativa de SQLitePlugin en un dispositivo
      window.openDatabase = window.sqlitePlugin.openDatabase.bind(window);
      // Obtener la plataforma y el locale
      window.appConfig.platform = device.platform;

      navigator.globalization.getLocaleName(function(locale) {
        window.appConfig.locale = locale.value.match(/^([a-z]{2})/)[1];
        once();
      }, function(err) {
        // TO-DO: mejor error handling
        window.appConfig.locale = 'en';
      });
    });

    // Un timeout con una configuración por defecto
    // AVISO: supone que siempre se levanta un servidor local para depurar en el navegador
    if (location.protocol === 'http:' || location.protocol === 'https:' ) {
      setTimeout(function() {
        console.log('Simulando deviceready!');
        _.extend(window.appConfig, {
          platform: 'Android',
          locale: 'es'
        });
        once();
      }, 500);
    }
  };
});