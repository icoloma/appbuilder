
var path = require('path')
, fs = require('fs')
, _ = require('underscore')
;

module.exports = function(grunt) {

grunt.loadNpmTasks('grunt-contrib-clean');
grunt.loadNpmTasks('grunt-contrib-copy');
grunt.loadNpmTasks('grunt-hub');
grunt.loadNpmTasks('grunt-regex-replace');


grunt.initConfig({
  hub: {
    dev: {
      src: ['../template/Gruntfile.js'],
      tasks: ['prepare-dev', 'optimized']
    },
    prod: {
      src: ['../template/Gruntfile.js'],
      tasks: ['prepare-project', 'prod']
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
    'www-prod': {
      expand: true,
      cwd: 'tmp-www',
      src: ['**', '!config/**'],
      dest: 'apps/segittur/assets/www/'
    },
    testAppData: {
      expand: true,
      cwd: 'apps/segittur/assets/www/test/data/',
      src: [ 'appData.db' ],
      dest: 'app-data/',
    },
    appData: {
      expand: true,
      cwd: 'app-data',
      src: [ 'appData.db' ],
      dest: 'apps/segittur/assets/'
    },
    appConfig: {
      expand: true,
      cwd: 'app-data',
      src: [ 'appConfig.json' ],
      dest: 'apps/segittur/assets/www'
    },
    appAssets: {
      expand: true,
      cwd: 'app-data',
      src: [ 'assets/**' ],
      dest: 'apps/segittur/assets/www/'
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

grunt.registerTask('join-config', '', function() {
  var metadata = JSON.parse(fs.readFileSync('app-data/metadata.json', 'utf-8'))
  , i18n = JSON.parse(fs.readFileSync('tmp-www/config/i18n.json', 'utf-8'))
  , flag_icons = JSON.parse(fs.readFileSync('tmp-www/config/flag-icons.json', 'utf-8'))
  , appConfig
  ;

  appConfig = _.extend({i18n: i18n}, {metadata: metadata});
  _.extend(appConfig.metadata, flag_icons);

  fs.writeFileSync('app-data/appConfig.json', JSON.stringify(appConfig), 'utf-8');
});

grunt.registerTask('test', 'Crea una aplicación con datos de prueba', function(phonegapPath) {
  grunt.task.run([
  'hub', 'clean:app', 'clean:tmp', 'create-phonegap-android:' + phonegapPath,
  'copy:appBuild', 'copy:plugin', 'regex-replace:androidConfig',
  'clean:www', 'copy:www', 'copy:appData', 'copy:testAppData', 'clean:tmp'
  ]);
});

grunt.registerTask('prod', 'Crea una aplicación con datos arbitrarios', function(phonegapPath) {
  grunt.task.run([
  'hub:prod', 'clean:app', 'clean:tmp', 'create-phonegap-android:' + phonegapPath,
  'copy:appBuild', 'copy:plugin', 'regex-replace:androidConfig', 'clean:www',
  'join-config', 'copy:www-prod', 'copy:appData', 'copy:appConfig', 'copy:appAssets', 'clean:tmp'
  ]);
});

};