/*
  Configuración de require.js
  Desarrollo, producción y tests se mantienen aquí

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
    backbone: comp + 'backbone/backbone',
    underscore: comp + 'underscore/underscore',
    almond: comp + 'almond/almond',
    'bootstrap': comp + 'bootstrap/js',
    hammer: comp + 'hammerjs/dist/jquery.hammer',
    moment: comp + 'momentjs/moment',
    tpl: 'modules/tpl'
  }

  // Configuración básica en desarrollo *en un navegador*
  , requireConf = {
    baseUrl: '/js',
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
      'bootstrap/button': {
        deps: ['jquery']
      },
      hammer: {
        deps: ['jquery']
      }
    }
  }
  ;

  /*
    Exporta la configuración:
     * module.exports para el Gruntfile, junto con las opciones de build
     * "require" y "requireConf" como globales para configurar requirejs en un navegador.
        (Los tests modifican y cargan la configuración *después* de cargar requirejs)
  */
  if ( typeof module === "object" && typeof module.exports === "object" ) {
    // Gruntfile

    module.exports = {
      basic: requireConf,
      // Opciones para la build de r.js
      build: {
        baseUrl: './js',
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
    // Tests: requirejs ya definido

    window.requireConf = requireConf;
    window._extendObject = _extendObject;
  } else {
    // Desarrollo en navegador

    window.require = requireConf;
  }
})();