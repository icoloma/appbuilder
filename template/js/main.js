define(
  [ 
    'lib/jquery', 'lib/jquery-mobile', 'lib/underscore', 'lib/backbone',
    'lib/persistence', 'lib/persistence.store.sql', 'lib/persistence.store.websql',
    'config', 'modules/initdb',
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