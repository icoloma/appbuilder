define(['globals'],
  function() {
    /*
      Muestra una colección como un listado usando una <table>

      @options.collection: la colección a mostrar.
      @options.trView: la vista que debe usar para las filas de la tabla

    */

    return B.View.extend({
      className: 'collectionview',

      events: {
        'activableTap .item-row': function(e) {
          this.trigger('navigate', $(e.currentTarget).data('url'), 1);
        }
      },

      initialize: function(options) {
        this.trView = options.trView;
        // this.listenTo(this.collection, 'sort', this.render);
      },

      render: function() {
        var $this = this.$el;
        $this.html('');
        this.collection.toJSON().forEach(function(item) {
          $this.append(
            this.trView(item)
          );
        }, this);
        return this;
      }
    });

  }
);