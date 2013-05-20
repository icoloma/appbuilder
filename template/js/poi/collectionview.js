define( ['poi/trview'],
  function(TrView) {
    return B.View.extend({
      tagName: 'table',

      events: {
        'tap tr': function(e) {
          var id = $(e.target).closest('tr[data-id]').data('id');
          window.location.hash = '#pois/' + id;
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
        this.$el.html($tbody);
        return this;
      }
    });
  }
);