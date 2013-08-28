
var path = require('path');

module.exports = function(grunt) {

grunt.loadNpmTasks('grunt-contrib-clean');
grunt.loadNpmTasks('grunt-contrib-copy');
grunt.loadNpmTasks('grunt-hub');
grunt.loadNpmTasks('grunt-regex-replace');


grunt.initConfig({
  hub: {
    dev: {
      src: ['../template/Gruntfile.js'],
      tasks: ['prepare-project', 'optimized']
    }
  },
  copy: {
    appBuild: {
      cwd: '../template/build/',
      expand: true,
      src: ['**'],
      dest: 'tmp-www'
    },
    plugin: {
      expand: true,
      cwd: 'node_modules/pgsqlite-android/Android/src',
      src: [ '**' ],
      dest: 'apps/segittur/src/'
    },
    www: {
      expand: true,
      cwd: 'tmp-www',
      src: ['**'],
      dest: 'apps/segittur/assets/www/'
    },
    appData: {
      expand: true,
      cwd: 'apps/segittur/assets/www/test/data/',
      src: [ 'appData.db' ],
      dest: 'apps/segittur/assets/',
    }
  },
  clean: {
    app: ['apps/segittur'],
    tmp: ['tmp-*'],
    www: ['apps/segittur/assets/www/*', '!apps/segittur/assets/www/cordova.js']
  },
  'regex-replace': {
    androidConfig: {
      src: 'apps/segittur/res/xml/config.xml',
      actions: [{
        search: '<plugins>',
        replace: '<plugins>\n<plugin name="SQLitePlugin" value="com.phonegap.plugin.sqlitePlugin.SQLitePlugin"/>'
      }]
    }
  }
});

grunt.registerTask('create-phonegap-android', 'Crea un proyecto de phonegap',
function(phonegapPath) {
  if (!phonegapPath) {
    throw new Error('No has proporcinado un path para PhoneGap.');
  }

  var done = this.async()
  , normalizedPath = phonegapPath.match(RegExp(path.sep + '$')) ? 
                     phonegapPath : phonegapPath + path.sep
  ;

  grunt.util.spawn({
    cmd: path.join(normalizedPath, 'lib/android/bin/create'),
    args: [ 'apps/segittur', 'com.segittur', 'Segittur' ],
    opts: {
      stdio: 'inherit'
    }
  }, function(error, result, code) {
    done();
  });
});

grunt.registerTask('test', 'Crea una aplicación con datos de prueba', function(phonegapPath) {
  grunt.task.run([
  'hub', 'clean:app', 'clean:tmp', 'create-phonegap-android:' + phonegapPath,
  'copy:appBuild', 'copy:plugin', 'regex-replace:androidConfig',
  'clean:www', 'copy:www', 'copy:appData', 'clean:tmp'
  ]);
});

};