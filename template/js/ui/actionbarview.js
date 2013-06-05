define(
  ['globals'],
  function() {
    return B.View.extend({

      className: 'actionbar',

      events: {
        'tap [data-action]': function(e) {
          var action = $(e.target).data('action')
          ;
          this.trigger(action);
        }
      },

      tmpl: _.template(
        '<% if (filter) { %><span data-action="filter" class="filter-button">Filter</span> <% } %>' +
        '<% if (sort) { %><span data-action="sort" class="sort-button">Sort</span> <% } %>' +
        '<% if (notify) { %><span data-action="notify" class="notify-button">Notify</span> <% } %>' +
        '<% if (star) { %><span data-action="star" class="star-button">Star</span> <% } %>' +
        '<% if (map) { %><span data-action="map" class="map-button"><a href="{{map}}">Map</a></span> <% } %>'
      ),

      controlDefaults: function () {
        return {
          root: false,
          filter: false,
          sort: false,
          notify: false,
          map: false,
          star: false
        };
      },

      render: function() {
        this.$el.html(
          this.tmpl(_.extend(this.controlDefaults(), this.options))
        );
        return this;
      }


    });
  }
);