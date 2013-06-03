define(['globals'],
  function() {

    return B.View.extend({
      events: {
        'tap tr': function(e) {
          window.location.hash = this.url +
            $(e.target).closest('[data-id]').data('id');
        }
      },

      initialize: function(options) {
        this.url = options.url;
        this.trView = options.trView;
      },

      render: function() {
        var $tbody = $('<tbody></tbody>');
        _.each(this.collection, function(model) {
          $tbody.append(
            (new this.trView({
              model: model
            })).render().$el
          );
        }, this);
        this.$el.html($('<table></table>').append($tbody));
        return this;
      }
    });

  }
);