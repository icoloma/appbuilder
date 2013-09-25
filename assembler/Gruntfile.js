
var path = require('path')
, fs = require('fs')
, _ = require('underscore')
;

module.exports = function(grunt) {

grunt.loadNpmTasks('grunt-contrib-clean');
grunt.loadNpmTasks('grunt-contrib-copy');
grunt.loadNpmTasks('grunt-hub');
grunt.loadNpmTasks('grunt-regex-replace');
grunt.loadNpmTasks('grunt-phonegap');

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
      dest: 'tmp/template'
    },
    testData: {
      expand: true,
      cwd: '../template/test/mocked-data',
      src: ['**'],
      dest: 'tmp/catalog'
    },
    appConfigAndResources: {
      expand: true,
      cwd: 'tmp/catalog',
      src: [ 'resources/**', 'app-config.json' ],
      dest: 'tmp/www'
    },
    appDatabase: {
      expand: true,
      cwd: 'tmp/catalog/dump',
      src: [ '*.db' ],
      dest: 'tmp/www/sqliteplugin'
    },
    appSrc: {
      expand: true,
      cwd: 'tmp/template/',
      src: [ '**', '!test/**', '!config/**' ],
      dest: 'tmp/www'
    }
  },
  clean: {
    app: ['app/*'],
    src: ['tmp/template', 'tmp/www']

    // tmpWWW: ['tmp/www'],
    // www: ['apps/segittur/www/*'],
    // tmpWWW_browser: ['tmp-www/config', 'tmp-www/test'],
    // dumpDb: ['tmp-app-data/dump/*.db']
  },
  phonegap: {
    config: {
      root: 'tmp/www',
      config: 'config.xml',
      cordova: '.cordova/',
      path: 'app',
      path: 'app/segittur',
      plugins: [
        'https://git-wip-us.apache.org/repos/asf/cordova-plugin-device.git',
        'https://git-wip-us.apache.org/repos/asf/cordova-plugin-geolocation.git',
        'https://git-wip-us.apache.org/repos/asf/cordova-plugin-dialogs.git',
        'https://git-wip-us.apache.org/repos/asf/cordova-plugin-globalization.git',
        // Fix temporal
        'https://github.com/jrvidal/PG-SQLitePlugin-Android.git'
      ],
      platforms: ['android']
    }
  }
});


// Se encarga de unir la configuración que viene del openCatalog con la configuración propia
// del template
grunt.registerTask('_join_config', '[Tarea interna]', function() {
  var catalog_config = JSON.parse(fs.readFileSync('tmp/catalog/dump/catalog-dump-config.json', 'utf-8'))
  , i18n = JSON.parse(fs.readFileSync('tmp/template/config/i18n.json', 'utf-8'))
  , flag_icons = JSON.parse(fs.readFileSync('tmp/template/config/flag-icons.json', 'utf-8'))
  , appMetadata = JSON.parse(fs.readFileSync('tmp/catalog/app-metadata/app-metadata.json', 'utf-8'))
  , appConfig = _.extend(i18n, catalog_config, appMetadata);
  ;

  // Añade iconos a los flag groups
  _.each(appConfig.flagGroups, function(group, id) {
    group.icon = flag_icons.flagIcons[id] || flag_icons.flagIcons['COMMON'];
  }); 

  fs.writeFileSync('tmp/catalog/app-config.json', JSON.stringify(appConfig), 'utf-8');
});


// Compila el código en appbuilder/template
grunt.registerTask('_template_build', '[Tarea interna]', function(buildMode) {
  buildMode = buildMode || 'optimized';
  grunt.task.run('hub:' + buildMode);
});

// grunt.registerTask('_basic_build', '[Tarea interna]',
//   ['clean:tmpWWW', 'copy:templateBuild', 'clean:www', 'copy:www', '_join_config', 'copy:appData', '_android_deploy']);


grunt.registerTask('mock', 'Genera unos datos de prueba aleatorios para "tmp-app-data".\n', function() {
  grunt.task.run(['hub:mock', 'copy:testData']);
});

grunt.registerTask('build', 
  'Compila el código web de template, genera un proyecto Android y lo compila.\n' +
  'Acepta un modo de compilación opcional (c.f. template/Gruntfile.js, tareas [Assembler]).\n' +
  'Ejemplo: grunt build:prod\n', 
  function(buildMode) {
    buildMode = buildMode || 'optimized';
    grunt.task.run(['clean:src', 'hub:' + buildMode, 'copy:templateBuild', '_join_config', 'copy:appSrc',
      'copy:appConfigAndResources', 'copy:appDatabase', 'phonegap:build'
    ]);
});

grunt.registerTask('install', 'Instala la aplicación (e.g. \'grunt install:android\').',
  function() {
    var target = this.target || 'android';
    if (target === 'android') {
      var done = this.async();
      grunt.util.spawn({
        cmd: 'phonegap',
        args: ['install', 'android'],
        opts: {
          cwd: 'app/segittur',
          stdio: 'inherit'
        }
      }, function(err, result, code) {
        done();
      })
    }
  });

};