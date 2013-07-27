/*
  Opciones para compilar:
  * 'prod'
  * 'optimized'
  * 'device'
*/

var _ = require('underscore')

/* Configuración para el build de require.js */
, rjsConf = require('./js/modules/require.conf.js')
, rjsBasicConf = _.extend(rjsConf.basic, rjsConf.build)
, rjsDev = _.extend(_.clone(rjsBasicConf), rjsConf.dev)
, rjsProd = _.extend(_.clone(rjsBasicConf), rjsConf.prod)

/* Regex para editar index.html */
, buildOpenTag = function(opt) {
  return '<!-- << GRUNT: ' + opt + ' -->';
}
, buildCloseTag = function(opt) {
  return '<!-- GRUNT: ' + opt + ' >> -->';
}
, buildRegex = function(opt) {
  return new RegExp(buildOpenTag(opt) + '[\\s\\S]+' + buildCloseTag(opt));
}
;


module.exports = function(grunt) {

grunt.loadNpmTasks('grunt-contrib-clean');
grunt.loadNpmTasks('grunt-contrib-copy');
grunt.loadNpmTasks('grunt-contrib-less');
grunt.loadNpmTasks('grunt-contrib-jshint');
grunt.loadNpmTasks('grunt-contrib-requirejs');
grunt.loadNpmTasks('grunt-regex-replace');


grunt.initConfig({
  clean: ['build/'],
  less: {
    // Compilar y minificar LESS
    dev: {
      src: 'less/style.less',
      dest: 'less/style.css',
    },
    prod: {
      src: 'less/style.less',
      dest: 'build/css/style.css',
      yuicompress: true
    }
  },
  jshint: {
    options: {
      laxcomma: true
    },
    // JSHint a todo el código salvo las librerias
    all: ['js/**/*.js', '!js/lib/**'],
  },
  requirejs: {
    prod: {
      options: rjsProd
    },
    dev: {
      options: rjsDev
    },
  },
  copy: {
    index: {
      src: 'index.html',
      dest: 'build/index.html'
    },
    js: {
      src: ['js/**'],
      dest: 'build/'
    },
    jsComponents: {
      src: ['**/*.js'],
      cwd: 'bower_components',
      dest: 'js/lib/components/',
      expand: true
    },
    data: {
      src: 'test/**',
      dest: 'build/',
    },
    css: {
      src: [ 'style.css', 'fonts/*', 'img/*', '!fonts/config*' ],
      dest: 'build/css/',
      expand: true,
      cwd: 'less/'
    }
  },
  'regex-replace': {
    css: {
      src: 'build/index.html',
      actions: [{
        search: buildRegex('CSS'),
        replace: '<link rel="stylesheet" href="css/style.css">'
      }]  
    },
    weinre: {
      src: 'build/index.html',
      actions: [{
        search: buildRegex('WEINRE'),
        replace: ''
      }]  
    },
    scripts: {
      src: 'build/index.html',
      actions: [{
        search: buildRegex('SCRIPTS'),
        replace: '<script src="js/scripts.js"></script>'
      }]  
    }
  }
});

/* Tareas internas */
grunt.registerTask('css-build-dev', ['less:dev', 'copy:css', 'regex-replace:css']);
grunt.registerTask('css-build-prod', ['less:prod', 'regex-replace:css']);
grunt.registerTask('basic-build', ['jshint', 'clean', 'copy:index']);
grunt.registerTask('rjs-dev', ['requirejs:dev', 'regex-replace:scripts']);
grunt.registerTask('rjs-prod', ['requirejs:prod', 'regex-replace:scripts']);
/*  */

grunt.registerTask('dev', ['less:dev', 'jshint', 'copy:jsComponents']);
grunt.registerTask('device', ['basic-build', 'copy:data', 'css-build-dev', 'copy:js']);
grunt.registerTask('optimized', ['basic-build', 'copy:data', 'css-build-dev', 'rjs-dev']);
grunt.registerTask('prod', ['basic-build', 'css-build-prod', 'rjs-prod', 'regex-replace:weinre']);

};