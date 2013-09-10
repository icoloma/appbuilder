
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
    optimized: {
      src: ['../template/Gruntfile.js'],
      tasks: ['_prepare_project', '_optimized']
    },
    device: {
      src: ['../template/Gruntfile.js'],
      tasks: ['_prepare_project', '_device']
    },
    prod: {
      src: ['../template/Gruntfile.js'],
      tasks: ['_prepare_project', '_prod']
    },
    mock: {
      src: ['../template/Gruntfile.js'],
      tasks: ['mock']
    }
  },
  copy: {
    templateBuild: {
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
      src: ['**', '!config/**'],
      dest: 'apps/segittur/assets/www/'
    },
    testData: {
      expand: true,
      cwd: '../template/test/data',
      src: ['**'],
      dest: 'tmp-app-data'
    },
    testAssets: {
      expand: true,
      cwd: '../template/test/',
      src: ['assets/**'],
      dest: 'tmp-app-data/www'
    },
    appData: {
      expand: true,
      cwd: 'tmp-app-data',
      src: [ '**' , '!catalog_metadata.json' ],
      dest: 'apps/segittur/assets/'
    },
  },
  clean: {
    app: ['apps/segittur'],
    tmpWWW: ['tmp-www'],
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


// Se encarga de unir la configuración que viene del openCatalog con la configuración propia
// del template
grunt.registerTask('_join_config', '[Tarea interna]', function() {
  var catalog_metadata = JSON.parse(fs.readFileSync('tmp-app-data/catalog-metadata.json', 'utf-8'))
  , i18n = JSON.parse(fs.readFileSync('tmp-www/config/i18n.json', 'utf-8'))
  , flag_icons = JSON.parse(fs.readFileSync('tmp-www/config/flag-icons.json', 'utf-8'))
  , appConfig
  ;

  appMetadata = _.extend({i18n: i18n}, {metadata: catalog_metadata});
  _.extend(appMetadata.metadata, flag_icons);

  fs.writeFileSync('tmp-app-data/www/appMetadata.json', JSON.stringify(appMetadata), 'utf-8');
});

// Crea el proyecto Android
grunt.registerTask('_pg_create_android', '[Tarea interna]', function() {
  var phonegapPath = process.env.PHONEGAP_PATH;

  if (!phonegapPath) {
    throw new Error('La variable PHONEGAP_PATH no existe.');
  }

  var done = this.async()
  , command = process.platform === 'win32' ? 'create.bat' : 'create'
  , fullComand = path.join(phonegapPath, 'lib/android/bin', command)
  ;

  if (!fs.existsSync(fullComand)) {
    throw new Error('El ejecutable ' + fullComand + ' no existe.')
  }

  grunt.util.spawn({
    cmd: fullComand,
    args: [ 'apps/segittur', 'com.segittur', 'Segittur' ],
    opts: {
      stdio: 'inherit'
    }
  }, function(error, result, code) {
    done();
  });
});

// Compila el código en appbuilder/template
grunt.registerTask('_template_build', '[Tarea interna]', function(buildMode) {
  buildMode = buildMode || 'optimized';
  grunt.task.run('hub:' + buildMode);
});

// Compila e instala el apk
grunt.registerTask('_android_deploy', '[Tarea interna]', ['android-build', 'android-install']);

grunt.registerTask('_basic_build', '[Tarea interna]',
  ['clean:tmpWWW', 'copy:templateBuild', 'copy:plugin', 'regex-replace:androidConfig',
    'clean:www', 'copy:www', '_join_config', 'copy:appData', '_android_deploy']);

grunt.registerTask('android-build', 'Compila la app con Ant.\n', function() {
  var done = this.async();
  grunt.util.spawn({
    cmd: 'ant',
    args: [ 'debug', '-f', 'apps/segittur/build.xml' ],
    opts: {
      stdio: 'inherit'
    }
  }, function(error, result, code) {
    if (error) grunt.fail.warn("Error en al intentar efectuar el build del proyecto Android.")
    done();
  });
});

grunt.registerTask('android-install', 'Instala la app mediante adb.\n', function() {
  var done = this.async();
  // Hacemos un uninstall + install para sobrescribir los datos
  grunt.log.writeln('Desinstalando la aplicación.');
  grunt.util.spawn({
    cmd: 'adb',
    args: [ 'uninstall', 'com.segittur' ],
  }, function(error, result, code) {
    grunt.log.writeln('Instalando el apk.');
    grunt.util.spawn({
      cmd: 'adb',
      args: [ 'install', 'apps/segittur/bin/Segittur-debug.apk' ],
      opts: {
        stdio: 'inherit'
      }
    }, function(error, result, code) {
      if (error) grunt.fail.warn("Error al intentar instalar el apk.")
      done();
    });
  });
});

grunt.registerTask('mock', 'Genera unos datos de prueba aleatorios para "tmp-app-data".\n', function() {
  grunt.task.run(['hub:mock', 'copy:testData', 'copy:testAssets']);
});

grunt.registerTask('build',
'Compila el código web de template, genera un proyecto Android, compila e instala el apk.\n' +
'Requiere una variable de entorno PHONEGAP_PATH apuntando a la instalación de PhoneGap.\n' +
'Acepta un modo de compilación opcional (c.f. template/Gruntfile.js, tareas [Assembler]).\n' +
'Ejemplo: grunt build:prod\n', 
  function(buildMode) {
    buildMode = buildMode || 'optimized';
    grunt.task.run(['clean:app', '_pg_create_android',
      '_template_build:' + buildMode, '_basic_build']);

});

grunt.registerTask('compile',
'Compila el código web de template, recompila e instala el apk, SIN generar un nuevo proyecto Android.\n' +
'Acepta un modo de compilación opcional, igual que \'build\'.\n' +
'Ejemplo: grunt compile:prod\n', 
  function(buildMode) {
    buildMode = buildMode || 'optimized';
    grunt.task.run(['_template_build:' + buildMode, '_basic_build']);
});

};