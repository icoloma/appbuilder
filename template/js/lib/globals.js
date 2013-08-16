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

    Backbone.View.prototype.pass = function(obj, event) {
      var self = this;
      this.listenTo(obj, event, function() {
        self.trigger(event);
      });
    };
  }
);