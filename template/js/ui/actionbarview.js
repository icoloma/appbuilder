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
        '<% if (starredPois) { %><a href="#/pois?starred=true" class="icon-star">{{res.Starred}}</a> <% } %>' +
        '<% if (filter) { %><span data-action="filter" class="filter-button icon-filter"></span> <% } %>' +
        '<% if (sort) { %><span data-action="sort" class="sort-button icon-sort"></span> <% } %>' +
        '<% if (notify) { %><span data-action="notify" class="notify-button">Notify</span> <% } %>' +
        '<% if (star) { %><span data-action="star" class="star-button {{isStarred}}"></span> <% } %>' +
        '<% if (map) { %><span data-action="map" class="map-button"><a href="{{map}}" class="icon-map"></a></span> <% } %>'
      ),

      controlDefaults: function () {
        return {
          starredPois: false,
          filter: false,
          sort: false,
          notify: false,
          map: false,
          star: false
        };
      },

      render: function() {
        this.options.isStarred = this.options.starred ? 'icon-star' : 'icon-star-empty';
        this.$el.html(
          this.tmpl(_.extend(this.controlDefaults(), this.options))
        );
        return this;
      }


    });
  }
);