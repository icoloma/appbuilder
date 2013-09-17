require.baseUrl = '/js';
require.deps = ['globals'];

var touchstart = new CustomEvent('touchstart', {bubbles: true})
, touchmove = new CustomEvent('touchmove', {bubbles: true})
, touchend = new CustomEvent('touchend', {bubbles: true})
;
touchstart.touches = touchend.touches = [];

window.appConfig = {
  locale: 'es',
  platform: 'android',
  dbName: 'appData',
  assets: 'assets/'
}

QUnit.config.autostart = false;

