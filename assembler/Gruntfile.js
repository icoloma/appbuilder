
var path = require('path')
, fs = require('fs')
, _ = require('underscore')

// Un path puede contener ':' (e.g. Windows) y fastidiar los args de grunt
, escapePath = function(path) {
  return path.replace(':', '\\:', 'g');
}
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
      tasks: ['_prepare_dev', 'optimized']
    },
    prod: {
      src: ['../template/Gruntfile.js'],
      tasks: ['_prepare_project', 'prod']
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

/*
  Tareas internas
*/

// Se encarga de unir la configuración que viene del openCatalog con la configuración propia
// del template
grunt.registerTask('_join_config', '[Tarea interna]', function() {
  var metadata = JSON.parse(fs.readFileSync('app-data/metadata.json', 'utf-8'))
  , i18n = JSON.parse(fs.readFileSync('tmp-www/config/i18n.json', 'utf-8'))
  , flag_icons = JSON.parse(fs.readFileSync('tmp-www/config/flag-icons.json', 'utf-8'))
  , appConfig
  ;

  appConfig = _.extend({i18n: i18n}, {metadata: metadata});
  _.extend(appConfig.metadata, flag_icons);

  fs.writeFileSync('app-data/appConfig.json', JSON.stringify(appConfig), 'utf-8');
});

// Crea el proyecto de phonegap
grunt.registerTask('_pg_create_android', '[Tarea interna]\n', function(phonegapPath) {
  if (!phonegapPath) {
    throw new Error('No has proporcinado un path para PhoneGap.');
  }

  var done = this.async()
  , normalizedPath = phonegapPath.match(RegExp(path.sep + '$')) ? 
                     phonegapPath : phonegapPath + path.sep
  , command = process.platform === 'win32' ? 'create.bat' : 'create'
  ;

  grunt.util.spawn({
    cmd: path.join(normalizedPath, 'lib/android/bin/' + command),
    args: [ 'apps/segittur', 'com.segittur', 'Segittur' ],
    opts: {
      stdio: 'inherit'
    }
  }, function(error, result, code) {
    done();
  });
});

grunt.registerTask('android-build', 'Ejecuta ant.\n', function() {
  var done = this.async();
  grunt.util.spawn({
    cmd: 'ant',
    args: [ 'debug', '-f', 'apps/segittur/build.xml' ],
    opts: {
      stdio: 'inherit'
    }
  }, function(error, result, code) {
    done();
  })
});

grunt.registerTask('android-install', 'Ejecuta adb install.\n', function() {
  var done = this.async();
  // Hacemos un uninstall + install para sobrescribir los datos
  grunt.log.writeln('Desinstalando la aplicación.')
  grunt.util.spawn({
    cmd: 'adb',
    args: [ 'uninstall', 'com.segittur' ],
  }, function(error, result, code) {
    grunt.log.writeln('Instalando el apk.')
    grunt.util.spawn({
      cmd: 'adb',
      args: [ 'install', 'apps/segittur/bin/Segittur-debug.apk' ],
      opts: {
        stdio: 'inherit'
      }

    }, function(error, result, code) {
      done();
    });
  });
});

grunt.registerTask('test',
  'Crea e instala una aplicación Android de test, con datos aleatorios de prueba.\ngrunt test:{carpeta de PhoneGap}\n',
  function(phonegapPath) {
    grunt.task.run([
      'hub:dev', 'clean:app', 'clean:tmp', '_pg_create_android:' + escapePath(phonegapPath),
      'copy:appBuild', 'copy:plugin', 'regex-replace:androidConfig',
      'clean:www', 'copy:www', 'copy:appData', 'copy:testAppData', 'clean:tmp',
      'android-build', 'android-install'
    ]);
});

grunt.registerTask('prod',
  'Crea e instala una aplicación Android en producción, con datos suministrados en "app-data" (Ver README).\ngrunt prod:{carpeta de PhoneGap}\n',
  function(phonegapPath) {
    grunt.task.run([
      'hub:prod', 'clean:app', 'clean:tmp', '_pg_create_android:' + escapePath(phonegapPath),
      'copy:appBuild', 'copy:plugin', 'regex-replace:androidConfig', 'clean:www',
      '_join_config', 'copy:www-prod', 'copy:appData', 'copy:appConfig', 'copy:appAssets', 'clean:tmp',
      'android-build', 'android-install'
    ]);
});

};