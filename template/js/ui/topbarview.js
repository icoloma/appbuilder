define(['globals'], function() {
  return B.View.extend({

    className: 'topbar',

    events: {
      'tap .back-button': function(e) {
        // TO-DO: para navegar entre jerarquías hará distinguir entre las acciones 'back' y 'up'
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
        this.toggleMenu();
      },
      'tap .menu-button': 'toggleMenu'
    },

    tmpl: _.template(
      '<div class="navbar">' +
        '<span class="<% if (!root) { %>back-button icon-back<% } %> bar-button"></span>' +
        '<h1 class="pagetitle hideable">{{title}}</h1>' +
        // '<span class="options-button">Options</span>'
      '</div>' +
      '<div class="actionbar">' +
        '<% if (search) { %>' +
          '<span data-action="search"' +
            'data-async="true" class="titlesize search-button icon-search bar-button">' +
          '</span>' +
        '<% } else {%>' +
          '<span class="bar-button icon-menu menu-button"></span>' +
          '<div class="action-menu">' +
            '<% if (filter) { %>' +
              '<span data-action="filter"' +
                'data-async="true" class="titlesize filter-button icon-filter bar-button">' +
              '</span>' +
            '<% } %><% if (sort) { %>' +
              '<span data-action="sort"' +
                'data-async="true" class="titlesize sort-button icon-sort bar-button">' +
              '</span>' +
            '<% } %><% if (notify) { %>' +
              '<span data-action="notify"' +
                'class="titlesize notify-button">Notify' +
              '</span>' +
            '<% } %><% if (map) { %>' +
              '<span data-action="map"' +
                'class="titlesize map-button bar-button icon-map">' +
              '</span>' +
            '<% } %>' +
          '</div>' +
        '<% } %>' +
      '</div>'
    ),

    controlDefaults: function () {
      return {
        root: false,
        filter: false,
        sort: false,
        notify: false,
        map: false,
        search: false
      };
    },

    render: function() {
      // this.options.isStarred = this.options.starred ? 'icon-star' : 'icon-star-empty';
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
    },
    toggleMenu: function() {
      this.$('.action-menu').toggle();
    }
  });
});