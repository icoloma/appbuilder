var _ = require('underscore')

/* Configuración para el build de require.js */
, rjsConf = require('./js/config/require.conf.js')
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
grunt.loadNpmTasks('grunt-contrib-connect');
grunt.loadNpmTasks('grunt-contrib-watch');


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
      dest: 'less/style.css',
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
    cssComponents: {
      src: ['**/*.css', '**/*.less'],
      cwd: 'bower_components',
      dest: 'less/components',
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
    },
    config: {
      src: 'config/*',
      dest: 'build/'
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
    debug: {
      src: 'build/index.html',
      actions: [{
        search: buildRegex('DEBUG'),
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
  },
  connect: {
    all: {
      options: {
        port: 7000,
        hostname: '*'
      }
    },
  },
  watch: {
    less: {
      files: ['less/**/*.less', '!less/components/**'],
      tasks: ['less:dev'],
      options: {
        livereload: true
      }
    },
    templates: {
      files: ['js/**/*.tpl'],
      tasks: [],
      options: {
        livereload: true
      }
    },
    // No se incluye una tarea para los *.js, en favor de usar las DevTools con Workspaces directamente.
  }
});

/* Tareas internas */
grunt.registerTask('_prepare_project',
  ['copy:cssComponents', 'copy:jsComponents', 'less:dev', 'jshint']);
grunt.registerTask('_prepare_dev',
  ['_prepare_project', 'mock']);
grunt.registerTask('_css_build_dev', ['less:dev', 'copy:css', 'regex-replace:css']);
grunt.registerTask('_css_build_prod', ['less:prod', 'copy:css', 'regex-replace:css']);
grunt.registerTask('_basic_build', ['clean', 'copy:index']);
grunt.registerTask('_rjs_dev', ['requirejs:dev', 'regex-replace:scripts']);
grunt.registerTask('_rjs_prod', ['requirejs:prod', 'regex-replace:scripts']);


// Compilación:  *SIN* hacer el build de requirejs
grunt.registerTask('_device', '[Assembler] Efectúa un build sin optimizar JavaScript.',
  ['_basic_build', 'copy:data', 'copy:config', '_css_build_dev', 'copy:js']);

// Compilación: Optimización de javascript
grunt.registerTask('_optimized', '[Assembler] Efectúa un build juntando todos los scripts.',
  ['_basic_build', 'copy:data', 'copy:config', '_css_build_dev', '_rjs_dev']);

// Producción (necesita ensamblarse con los datos del catálogo)
grunt.registerTask('_prod', '[Assembler] Efectúa un build listo para producción,.',
  ['_basic_build', '_css_build_prod', '_rjs_prod', 'regex-replace:debug', 'copy:config']);

/*  */

// Tarea básica de desarrollo
grunt.registerTask('default', 'Alias de prepare-and-dev\n', ['prepare-and-dev']);
grunt.registerTask('dev', 'Lanza el servidor y Livereload.\n', ['connect', 'watch']);
grunt.registerTask('prepare-and-dev',
  '[DEFAULT] Prepara el proyecto con datos de prueba y lanza "dev".\n',
  ['_prepare_dev', 'dev']);

grunt.registerTask('mock', 'Prepara datos de prueba.\n', function() {
  var done = this.async();
  grunt.util.spawn({
    cmd: 'node',
    args: [ './test/mock.js' ],
    opts: {
      stdio: 'inherit'
    }
  }, function(error, result, code) {
    done();
  });
});

};