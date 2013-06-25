/*
  Configuración de require.js
    * Desarrollo y producción se mantienen aquí: Gruntfile importa este fichero.
    * También actúa como configuración al depurar en un navegador 
*/

// Configuración básica en desarrollo *en un navegador*
var require = {
  baseUrl: './js',
  paths: {
    globals: 'lib/globals',
    'modules/config': 'modules/config-dev',
    'db/initdb': 'db/initdb-dev'
  },
  shim: {
    'lib/backbone': {
      deps: ['lib/jquery', 'lib/underscore']
    },
    'lib/persistence.websql': {
      deps: ['lib/persistence.store.sql']
    },
    'lib/persistence.sql': {
      deps: ['lib/persistence', 'lib/SQLitePlugin']
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
      include: [ 'lib/almond' ],
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
        'db/initdb': 'db/initdb-prod'
      },
    }
  };
}