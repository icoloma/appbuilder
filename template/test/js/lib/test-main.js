/*
  Configuración para los tests unitarios
  Entornos: Karma y navegador con QUnit
*/

// Dependencias de require para Karma
if (window.__karma__) {
  var tests = [];
  for (var file in window.__karma__.files) {
    if (window.__karma__.files.hasOwnProperty(file)) {
      if (/\/test\/js\/[^\/]+-test\.js$/.test(file)) {
        tests.push(file);
      }
    }
  }
}

// Configuración del app
window.res = {
  locale: 'en',
  platform: 'Android',
  resources: 'resources/',
  appData: 'catalog-dump.db'
};

/*
  Configuración de requirejs para los tests
*/

// Karma
if (window.__karma__) {
  _extendObject(requireConf, {
    baseUrl: '/base/js',
    callback: window.__karma__.start,
    deps: tests
  });

  _extendObject(requireConf.paths, {
    'test': '/base/test/js/lib'
  });

// Navegador
} else {
  _extendObject(requireConf.paths, {
    'test': '/test/js/lib',
    'unit-test': '/test/js'
  });
  QUnit.config.autostart = false;
}

requirejs.config(requireConf);