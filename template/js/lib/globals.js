define(
  [ 
    'modules/i18n',
    'lib/jquery', 'lib/underscore', 'lib/backbone', 'modules/touch',
    'lib/persistence', 'lib/persistence.store.sql', 'lib/persistence.store.websql',
    'lib/async', 'modules/query'
  ],
  function(i18n) {
    window.B = Backbone;

    // Sintaxis tipo handlebars
    _.templateSettings = {
      interpolate : /\{\{([\s\S]+?)\}\}/g,
      escape      : /\{\{-([\s\S]+?)\}\}/g,
      evaluate      : /<%([\s\S]+?)%>/g
    };

    // TODO: get correct locale
    // navigator.globalization.
    window.appConfig.locale = 'es';
    window.res = i18n[window.appConfig.locale];
  }
);