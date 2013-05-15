module.exports = function(grunt) {

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
      dest: 'less/style.css',
      yuicompress: true
    }
  },
  jshint: {
    // JSHint a todo el código salvo las librerias
    dev: ['**/*.js', '!js/lib/*.js'],
    build: ['**/*.js', '!js/lib/*.js']
  },
  // Para el futuro
  // requirejs: {
  //   build: {
  //     options: {
  //       baseUrl: './js',
  //       name: 'main',
  //       include: 'lib/almond',
  //       out: 'js/built.js',
  //       optimize: 'none',
  //     }
  //   } 
  // }
});

}