define(
  [],
  function() {
    return B.View.extend({

      className: 'navbar',

      events: {
        'tap .back-button': function() {
          // TODO: para navegar entre jerarquías hará falta
          // mantener un historial de navegación y de niveles
          history.back();
        }
      },

      tmpl: _.template(
        '<% if (!root) { %><div class="back-button">Back</div><% } %>' +
        '<h1 class="pagetitle">{{title}}</h1>' +
        '<% if (filter) { %><span class="filter-button">Filter</span> <% } %>' +
        '<% if (sort) { %><span class="sort-button">Sort</span> <% } %>' +
        '<% if (notify) { %><span class="notify-button">Notify</span> <% } %>' +
        '<% if (star) { %><span class="star-button">Star</span> <% } %>' +
        '<% if (map) { %><span class="map-button">Map</span> <% } %>'
      ),

      controlDefaults: function () {
        return {
          root: false,
          filter: false,
          sort: false,
          notify: false,
          map: false,
          star: false
        }
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