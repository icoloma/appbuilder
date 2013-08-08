define(
  [ 
    'jquery', 'underscore', 'backbone',
    'modules/touch', 'SQLitePlugin', 'async', 'modules/i18n', 'swipe'
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