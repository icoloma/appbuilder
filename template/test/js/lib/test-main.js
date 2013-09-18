// Tests unitarios
var tests = [];
for (var file in window.__karma__.files) {
  if (window.__karma__.files.hasOwnProperty(file)) {
    if (/\/test\/js\/[^\/]+-test\.js$/.test(file)) {
      tests.push(file);
    }
  }
}


// Eventos touch para depurar
var touchstart = new CustomEvent('touchstart', {bubbles: true})
, touchmove = new CustomEvent('touchmove', {bubbles: true})
, touchend = new CustomEvent('touchend', {bubbles: true})
;
touchstart.touches = touchend.touches = [];

// Configuración del app para tests
window.appConfig = {
  locale: 'en',
  platform: 'Android',
  assets: 'assets/',
  dbName: 'appData'
};

// Cambios en la configuración de requirejs por defecto
_extendObject(requireConf, {
  baseUrl: '/base/js',
  callback: window.__karma__.start,
  deps: tests
});

_extendObject(requireConf.paths, {
  mocksql: '/base/test/js/lib/mocksql'
});

requirejs.config(requireConf);