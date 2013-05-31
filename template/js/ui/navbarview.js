define(
  [],
  function() {
    return B.View.extend({

      events: {
        'tap .back-button': function(e) {
          // TODO: para navegar entre jerarquías hará distinguir entre las acciones 'back' y 'up'
          // Ej: moverse entre los Pois resultados de una búsquedas 
          // Necesitará entonces mantener un historial de navegación con jerarquías
          history.back();
        }
      },

      tmpl: _.template(
        '<% if (!root) { %><span data-action="back" class="back-button">Back</span><% } %>' +
        '<h1 class="pagetitle">{{title}}</h1>' +
        '<span class="options-button">Options</span>'
      ),

      render: function() {
        var options = _.defaults(this.options || {}, { root: false });
        this.$el.html(this.tmpl(options));
        return this;
      },

    });
  }
);