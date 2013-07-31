define(['globals'], function() {
  return B.View.extend({

    className: 'topbar',

    events: {
      'tap .back-button': function(e) {
        // TODO: para navegar entre jerarquías hará distinguir entre las acciones 'back' y 'up'
        // Ej: moverse entre los Pois resultados de una búsquedas 
        // Necesitará entonces mantener un historial de navegación con jerarquías
        history.back();
      },
      'tap [data-action]': function(e) {
        var $target = $(e.target)
        , action = $target.data('action')
        , async = $target.data('async')
        ;
        if (!this.blocked$El) {
          this.trigger(action);
          if (async) this.block($target);
        }
      }
    },

    tmpl: _.template(
      '<div class="navbar">' +
        '<span class="<% if (!root) { %>back-button icon-back<% } %> bar-button"></span>' +
        '<h1 class="pagetitle">{{title}}</h1>' +
        // '<span class="options-button">Options</span>'
      '</div>' +
      '<div class="actionbar">' +
        '<% if (starredPois) { %><a href="#/pois?starred=true" class="icon-star bar-button">{{res.Starred}}</a> <% } %>' +
        '<% if (filter) { %><span data-action="filter" data-async="true" class="filter-button icon-filter bar-button"></span><% } %>' +
        '<% if (sort) { %><span data-action="sort" data-async="true" class="sort-button icon-sort bar-button"></span><% } %>' +
        '<% if (notify) { %><span data-action="notify" class="notify-button">Notify</span><% } %>' +
        '<% if (star) { %><span data-action="star" class="star-button bar-button {{isStarred}}"></span><% } %>' +
        '<% if (map) { %><span data-action="map" class="map-button bar-button"><a href="{{map}}" class="icon-map"></a></span><% } %>' +
      '</div>'
    ),

    controlDefaults: function () {
      return {
        root: false,
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
    },

    block: function($el) {
      this.blocked$El = $el.addClass('busy');
    }, 
    unblock: function() {
      this.blocked$El.removeClass('busy');
      this.blocked$El = false;
    }

  });
});