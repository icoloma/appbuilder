// Karma configuration
// Generated on Wed Sep 18 2013 10:52:24 GMT-0400 (EDT)

var _ = require('underscore')
, fs = require('fs')
, path = require('path')

// Para no incluir todos los js/lib/components, leemos a mano la carpeta /js y 
// construimos la lista de ficheros a incluir
// No parece que se pueda hacer con patrones minimatchs a lo Grunt (i.e. !/pattern) 
, jsDirs = _.filter(fs.readdirSync('js'), function(filename) {
  return filename !== 'lib' && 
    fs.statSync(path.join('js', filename)).isDirectory(); 
})
, files = [
  'js/config/require.conf.js',
  'test/js/lib/test-main.js',
  {pattern: 'js/*.js', included: false},
  {pattern: 'js/lib/*.js', included: false},
  {pattern: 'js/lib/components/**/*.js', included: false, watched: false},
  {pattern: 'test/js/**/*.js', included: false}
].concat(_.map(jsDirs, function(dir) {
  return {
    pattern: 'js/' + dir + '/**/*.js',
    included: false
  }
}))
;

module.exports = function(config) {
  config.set({

    // base path, that will be used to resolve files and exclude
    basePath: '',


    // frameworks to use
    frameworks: ['qunit', 'requirejs'],


    // list of files / patterns to load in the browser
    files: files,

    // list of files to exclude
    exclude: [
    ],


    // test results reporter to use
    // possible values: 'dots', 'progress', 'junit', 'growl', 'coverage'
    reporters: ['dots', 'junit'],

    junitReporter: {
      outputFile: 'template-test-results.xml'
    },


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // Start these browsers, currently available:
    // - Chrome
    // - ChromeCanary
    // - Firefox
    // - Opera
    // - Safari (only Mac)
    // - PhantomJS
    // - IE (only Windows)
    browsers: ['Chrome'],


    // If browser does not capture in given timeout [ms], kill it
    captureTimeout: 60000,


    // Continuous Integration mode
    // if true, it capture browsers, run tests and exit
    singleRun: true
  });
};
