define( ['entity/trview'],
  function(TrView) {
    return B.View.extend({
      tagName: 'table',

      events: {
        'tap tr': function(e) {
          var id = $(e.target).closest('tr[data-id]').data('id');
          window.location.hash = '#entities/' + id;
        }
      },

      render: function() {
        var $tbody = $('<tbody></tbody>');
        _.each(this.collection, function(item) {
          $tbody.append(
            new TrView({
              model: item
            }).render().$el
          );
        });
        this.$el.append($tbody);
        return this;
      }
    });
  }
);