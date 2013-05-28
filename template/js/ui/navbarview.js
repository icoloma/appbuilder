define(
  [],
  function() {
    return B.View.extend({

      className: 'navbar',

      events: {
        'tap [data-action]': function(e) {
          var action = $(e.target).data('action')
          ;
          if (action === 'back') {
            this.back();
          } else {
            this.trigger(action);
          }
        }
      },

      back: function() {
          // TODO: para navegar entre jerarquías hará falta
          // mantener un historial de navegación y de niveles
          history.back();
      },

      tmpl: _.template(
        '<% if (!root) { %><span data-action="back" class="back-button">Back</span><% } %>' +
        '<h1 class="pagetitle">{{title}}</h1>' +
        '<span class="options-button">Options</span>' +
        '<div class="actions">' +
          '<% if (filter) { %><span data-action="filter" class="filter-button">Filter</span> <% } %>' +
          '<% if (sort) { %><span data-action="sort" class="sort-button">Sort</span> <% } %>' +
          '<% if (notify) { %><span data-action="notify" class="notify-button">Notify</span> <% } %>' +
          '<% if (star) { %><span data-action="star" class="star-button">Star</span> <% } %>' +
          '<% if (map) { %><span data-action="map" class="map-button">Map</span> <% } %>' +
        '</div>'
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