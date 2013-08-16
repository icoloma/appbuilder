define(['globals'],
  function() {
    /*
      Muestra una colección como un listado usando una <table>

      @options.collection: la colección a mostrar.
        AVISO: puede ser una colección de Backbone o un array, influye en cómo iterar.
      @options.trView: la vista que debe usar para las filas de la tabla

    */

    return B.View.extend({
      className: 'collectionview',

      events: {
        'tap .row': function(e) {
          window.location.href = $(e.currentTarget).data('url');
        }
      },

      initialize: function(options) {
        this.trView = options.trView;
        // this.listenTo(this.collection, 'sort', this.render);
      },

      render: function() {
        var $this = this.$el;
        this.collection.forEach(function(item) {
          $this.append(
            this.trView(item)
          );
        }, this);
        // this.$el.html($tbody);
        return this;
      }
    });

  }
);