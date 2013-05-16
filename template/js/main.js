define(
  ['lib/jquery', 'lib/jquery-mobile', 'lib/underscore', 'lib/backbone', 'lib/lawnchair', 'lib/lawnchair-websql'],
  function() {
    window.B = Backbone;

    // Sintaxis tipo handlebars
    _.templateSettings = {
      interpolate : /\{\{(.+?)\}\}/g,
      escape      : /\{\{-(.+?)\}\}/g
    };
  }
);