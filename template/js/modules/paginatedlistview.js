define(['modules/baselistview'],
function(BaseListView) {
  return BaseListView.extend({
    /*
      @cursor: número de elementos a mostrar

    */
    initialize: function(options) {
      BaseListView.prototype.initialize.call(this, options);
      this.pageCount = Math.ceil(window.innerHeight / 80);
      this.cursor = this.options.cursor || this.pageCount;
    },

    monitorScroll: function() {
      var self = this;

      this.monitorId = setInterval(function() {

        // Para si la lista no está en el DOM, o todos los elementos son visibles
        if (! $.contains(document.documentElement, self.el) ||
              self.cursor >= self.collection.length
        ) {
          clearInterval(self.monitorId);
          return;
        }

        // Muestra más elementos cuando el borde de la ventana está cerca del final
        // de la vista (hay un marge de ~80px debido a la topbar).
        var screenBottomY = window.innerHeight + window.pageYOffset
        , viewHeight = self.el.clientHeight
        ;
        if (screenBottomY >= viewHeight) {
          self.showMore();
        }
      }, 100);
    },

    render: function() {
      this.$el.html('');
      if (this.collection.length === 0) {
        var message = '<div class="item-row empty-list"><b>' + res.EmptyList + '</b></div>';
        this.$el.append(message);
      } else {
        _.each(this.collection.toJSON(), function(item, i) {
          if (i < this.cursor) {
            this.$el.append(this.trView(item));
          }
        }, this);
      }
      return this;
    },
    
    showMore: function() {
      var currentCount = this.$('.item-row').length;

      this.cursor += Math.floor(this.pageCount);
      this.cursor = Math.min(this.cursor, this.collection.length);
      this.trigger('updatequery', {cursor: this.cursor});

      _.each(this.collection.toJSON(), function(item, i) {
        if (i < this.cursor && i >= currentCount) {
          this.$el.append(this.trView(item));
        }
      }, this);
    }    
  });
});