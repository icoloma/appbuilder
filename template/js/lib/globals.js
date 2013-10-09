/*
  Librerías y configuración.

  Exporta los siguientes globales:

    $  jQuery  _  B  Backbone moment async Hammer sqlitePlugin Swiper
*/
define(
  [ 
    'jquery', 'underscore', 'backbone', 'moment',
    'ui/touch', 'hammer', 'SQLitePlugin', 'async', 'swipe',
    'bootstrap/transition', 'bootstrap/button', 'bootstrap/collapse'
  ],
  function() {
    window.B = Backbone;

    // Sintaxis tipo handlebars
    _.templateSettings = {
      interpolate : /\{\{([\s\S]+?)\}\}/g,
      escape      : /\{\{-([\s\S]+?)\}\}/g,
      evaluate      : /<%([\s\S]+?)%>/g,

      // Pasar un nombre de variable a _.template mejora el rendimiento
      variable: 'tpl'
    };

    Backbone.View.prototype.pass = function(obj, event) {
      var self = this;
      this.listenTo(obj, event, function() {
        self.trigger.apply(self, [].concat(event, Array.prototype.slice.call(arguments)));
      });
    };
  }
);