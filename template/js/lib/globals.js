define(
  [ 
    'lib/jquery', 'lib/underscore', 'lib/backbone', 'modules/touch',
    'lib/persistence', 'lib/persistence.store.sql', 'lib/persistence.store.websql',
    'lib/async'
  ],
  function() {
    window.B = Backbone;

    // Sintaxis tipo handlebars
    _.templateSettings = {
      interpolate : /\{\{(.+?)\}\}/g,
      escape      : /\{\{-(.+?)\}\}/g
    };
  }
);