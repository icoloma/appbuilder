define(
  [ 
    'jquery', 'underscore', 'backbone',
    'persistence', 'persistence.store.sql', 'persistence.store.websql',
    'modules/touch', 'SQLitePlugin', 'async', 'modules/i18n'
  ],
  function() {
    window.B = Backbone;

    // Sintaxis tipo handlebars
    _.templateSettings = {
      interpolate : /\{\{([\s\S]+?)\}\}/g,
      escape      : /\{\{-([\s\S]+?)\}\}/g,
      evaluate      : /<%([\s\S]+?)%>/g
    };
  }
);