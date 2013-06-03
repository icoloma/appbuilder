/*
  Opciones para compilar:
  * 'prod'
  * 'optimized'
  * 'device'
*/

module.exports = function(grunt) {

grunt.loadNpmTasks('grunt-contrib-clean');
grunt.loadNpmTasks('grunt-contrib-copy');
grunt.loadNpmTasks('grunt-contrib-less');
grunt.loadNpmTasks('grunt-contrib-jshint');
grunt.loadNpmTasks('grunt-contrib-requirejs');
grunt.loadNpmTasks('grunt-regex-replace');

var buildOpenTag = function(opt) {
  return '<!-- << GRUNT: ' + opt + ' -->';
}
, buildCloseTag = function(opt) {
  return '<!-- GRUNT: ' + opt + ' >> -->';
}
, buildRegex = function(opt) {
  return new RegExp(buildOpenTag(opt) + '[\\s\\S]+' + buildCloseTag(opt));
}
;

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
    all: ['js/**/*.js', '!js/lib/*.js'],
  },
  requirejs: {
    prod: {
      options: {
        baseUrl: './js',
        name: 'main',
        paths: {
          globals: 'lib/globals'
        },
        include:
          [
            'lib/require'
          ],
        out: 'build/js/scripts.js',
        optimize: 'none'
      }
    },
    dev: {
      options: {
        baseUrl: './js',
        name: 'main',
        paths: {
          globals: 'lib/globals',
          'modules/config': 'modules/config-dev'
        },
        include:
          [
            'lib/require'
          ],
        out: 'build/js/scripts.js',
        optimize: 'none'
      }
    },
  },
  copy: {
    index: {
      src: 'index.html',
      dest: 'build/index.html'
    },
    js: {
      // expand: true,
      src: ['js/**'],
      dest: 'build/'
    },
    data: {
      src: 'test/**',
      dest: 'build/',
    },
    css: {
      src: 'less/style.css',
      dest: 'build/css/style.css'
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

// Internal tasks
grunt.registerTask('css-build-dev', ['less:dev', 'copy:css', 'regex-replace:css']);
grunt.registerTask('css-build-prod', ['less:prod', 'regex-replace:css']);
grunt.registerTask('basic-build', ['jshint', 'clean', 'copy:index']);
grunt.registerTask('optimize-js-dev', ['requirejs:dev', 'regex-replace:scripts']);
grunt.registerTask('optimize-js-prod', ['requirejs:dev', 'regex-replace:scripts']);

grunt.registerTask('dev', ['less:dev', 'jshint']);
grunt.registerTask('device', 
  ['basic-build', 'copy:data', 'css-build-dev', 'copy:js']);
grunt.registerTask('optimized', 
  ['basic-build', 'copy:data', 'css-build-dev', 'optimize-js-dev']);
grunt.registerTask('prod', 
  ['basic-build', 'css-build-prod', 'optimize-js-prod', 'regex-replace:weinre']);

};