define(
  [ 
    'lib/jquery', 'lib/underscore', 'lib/backbone', 'modules/touch',
    'lib/persistence', 'lib/persistence.store.sql', 'lib/persistence.store.websql',
    'lib/async', 'modules/query'
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