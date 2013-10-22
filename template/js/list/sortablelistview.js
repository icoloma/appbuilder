/*
  Una lista con capacidad de ordenarse.

  Se supone que los controles '.sort-control' se activan mediante CSS con los métodos
  startSorting/endSorting

  El evento 'sorted' se emite cuando hay un cambio de orden en la vista.
  La colección this.collection se reordena a mano!
*/

define(['list/baselistview'], function(BaseListView) {
  return BaseListView.extend({

    events: {
      'tap .sort-control': function(e) {
        var $rows = this.$('.item-row')
        , $row = $(e.currentTarget).closest('.item-row')
        , index = $rows.index($row)
        , step = this.collection.at(index)
        , dir = $(e.currentTarget).hasClass('sort-down') ? 1 : -1
        , $next, nextIndex, nextStep
        ;

        // Al borde de la lista, no hay más movimiento
        if (index === (dir > 0 ? $rows.length -1 : 0))
          return;

        nextIndex = index + dir;
        $next = $rows.get(nextIndex);
        nextStep = this.collection.at(nextIndex);

        // TO-DO: animar el cambio
        $row[dir > 0 ? 'before' : 'after' ]($next);

        // Reordena la lista "a mano"
        this.collection.models[index] = nextStep;
        this.collection.models[nextIndex] = step;

        this.trigger('sorted');
      }
    },

    startSorting: function() {
      this.$el.addClass('sorting');
    },

    endSorting: function() {
      this.$el.removeClass('sorting');
    }
  });
});