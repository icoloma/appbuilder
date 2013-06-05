define(['globals'],
  function() {
    /*
      Muestra una colección como un listado usando una <table>

      @options.collection: la colección a mostrar.
        OJO: puede ser una colección de Backbone o un array, influye en cómo iterar.
      @options.url: la url de la colección.
      @options.trView: la vista que debe usar para las filas de la tabla

    */

    return B.View.extend({
      tagName: 'table',

      events: {
        'tap tr': function(e) {
          window.location.hash = this.url +
            $(e.target).closest('[data-id]').data('id');
        }
      },

      initialize: function(options) {
        this.url = options.url;
        this.trView = options.trView;
        this.listenTo(this.collection, 'sort', this.render);
      },

      render: function() {
        var $tbody = $('<tbody></tbody>');
        this.collection.forEach(function(model) {
          $tbody.append(
            (new this.trView({
              model: model
            })).render().$el
          );
        }, this);
        this.$el.html($tbody);
        return this;
      }
    });

  }
);