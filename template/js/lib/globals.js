define(
  [ 
    'jquery', 'underscore', 'backbone', 'backbone.history',
    'modules/touch', 'SQLitePlugin', 'async', 'swipe',
    'bootstrap/transition', 'bootstrap/collapse'
  ],
  function() {
    window.B = Backbone;

    // Sintaxis tipo handlebars
    _.templateSettings = {
      interpolate : /\{\{([\s\S]+?)\}\}/g,
      escape      : /\{\{-([\s\S]+?)\}\}/g,
      evaluate      : /<%([\s\S]+?)%>/g
    };

    Backbone.View.prototype.pass = function(obj, event) {
      var self = this;
      this.listenTo(obj, event, function() {
        self.trigger.apply(self, [].concat(event, Array.prototype.slice.call(arguments)));
      });
    };
  }
);