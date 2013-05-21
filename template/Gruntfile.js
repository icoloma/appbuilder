module.exports = function(grunt) {

grunt.loadNpmTasks('grunt-contrib-copy');
grunt.loadNpmTasks('grunt-contrib-less');
grunt.loadNpmTasks('grunt-contrib-jshint');
grunt.loadNpmTasks('grunt-contrib-requirejs');

grunt.initConfig({
  less: {
    // Compilar LESS
    dev: {
      src: 'less/style.less',
      dest: 'less/style.css'
    },
    // Compilar y minificar LESS
    build: {
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
    dev: ['js/**/*.js', '!js/lib/*.js'],
    build: ['js/**/*.js', '!js/lib/*.js']
  },
  requirejs: {
    build: {
      options: {
        baseUrl: './js',
        name: 'main',
        include:
          [
            'lib/almond', 'lib/globals'
          ],
        out: 'build/js/main.js',
      }
    },
  },
  copy: {
    build: {
      src: 'index-prod.html',
      dest: 'build/index.html'
    }
  }
});

grunt.registerTask('build',
  ['less:build', 'jshint:build', 'requirejs:build', 'copy:build']);

}