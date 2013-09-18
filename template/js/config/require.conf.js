/*
  Configuración de require.js
    * Desarrollo y producción se mantienen aquí: Gruntfile importa este fichero.
    * También actúa como configuración al depurar en un navegador 
*/
(function() {
  var comp = 'lib/components/'
  // Un shim de _.extend para copiar propiedades comunes
  , _extendObject = function(dest, orig) {
    for (var p in orig) {
      dest[p] = orig[p];
    }
    return dest;
  }

  // Rutas para las librerías
  , libPaths = {
    jquery: 'lib/jquery',
    async: 'lib/async',
    swipe: comp + 'swiper/dist/idangerous.swiper-2.0',
    SQLitePlugin: comp + 'PG-SQLitePlugin-Android/Android/assets/www/SQLitePlugin',
    backbone: comp + 'backbone/backbone',
    underscore: comp + 'underscore/underscore',
    almond: comp + 'almond/almond',
    'bootstrap/transition': comp + 'bootstrap/js/transition',
    'bootstrap/collapse': comp + 'bootstrap/js/collapse',
    tpl: 'modules/tpl'
  }

  // Configuración básica en desarrollo *en un navegador*
  , requireConf = {
    baseUrl: './js',
    paths: _extendObject({
      globals: 'lib/globals',
      'config/config': 'config/config-dev',
    }, libPaths),
    shim: {
      backbone: {
        deps: ['jquery', 'underscore']
      },
      swipe: {
        deps: [ 'jquery' ]
      },
      'bootstrap/transition': {
        deps: ['jquery']
      },
      'bootstrap/collapse': {
        deps: ['jquery']
      },
    }
  }
  ;

  /*
    Exporta la configuración de distintas:
     * module.exports para el Gruntfile
     * require global para desarrollo en el navegador
     * requireConf global para test
  */
  if ( typeof module === "object" && typeof module.exports === "object" ) {
    module.exports = {
      basic: requireConf,
      // Opciones para la build de r.js
      build: {
        name: 'main',
        include: [ 'almond' ],
        out: 'build/js/scripts.js',
        optimize: 'none',
        pragmasOnSave: {
          tplExclude: true
        },
      },
      // Opciones exclusivas de la build en desarrollo o producción
      dev: {
        optimize: 'none'
      },
      prod: {
        paths: _extendObject({
          globals: 'lib/globals',
          'config/config': 'config/config-prod',
        }, libPaths),
        optimize: 'uglify2'
      }
    };

  } else if ( typeof requirejs === 'function' ) {

    window.requireConf = requireConf;
    window._extendObject = _extendObject;
  } else {

    window.require = requireConf;

  }
})();