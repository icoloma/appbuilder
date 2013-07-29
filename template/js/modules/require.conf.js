/*
  Configuración de require.js
    * Desarrollo y producción se mantienen aquí: Gruntfile importa este fichero.
    * También actúa como configuración al depurar en un navegador 
*/

var comp = 'lib/components/';

// Configuración básica en desarrollo *en un navegador*
var require = {
  baseUrl: './js',
  paths: {
    globals: 'lib/globals',
    'modules/config': 'modules/config-dev',
    'db/initdb': 'db/initdb-dev',
    'menu.config': '../test/data/menu',
    // Librerías
    jquery: 'lib/jquery',
    async: 'lib/async',
    SQLitePlugin: comp + 'PG-SQLitePlugin-Android/Android/assets/www/SQLitePlugin',
    backbone: comp + 'backbone/backbone',
    underscore: comp + 'underscore/underscore',
    almond: comp + 'almond/almond',
    persistence: comp + 'persistencejs/lib/persistence',
    'persistence.store.sql': comp + 'persistencejs/lib/persistence.store.sql',
    'persistence.store.websql': comp + 'persistencejs/lib/persistence.store.websql',
  },
  shim: {
    'backbone': {
      deps: ['jquery', 'underscore']
    },
    'persistence.websql': {
      deps: ['persistence.store.sql']
    },
    'persistence.sql': {
      deps: ['persistence', 'SQLitePlugin']
    }
  }
}
;

// Exportamos la configuración para el Gruntfile
if ( typeof module === "object" && typeof module.exports === "object" ) {
  module.exports = {
    basic: require,
    // Opciones para la build de r.js
    build: {
      name: 'main',
      include: [ 'almond' ],
      out: 'build/js/scripts.js',
      optimize: 'none'
    },
    // Opciones exclusivas de la build en desarrollo o producción
    dev: {
      optimize: 'none'
    },
    prod: {
      paths: {
        globals: 'lib/globals',
        'modules/config': 'modules/config-prod',
        'db/initdb': 'db/initdb-prod',
        'menu': 'data/menu'
      },
    }
  };
}