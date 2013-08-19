define(['globals', 'tpl!ui/topbarview.tpl'],
function(Globals, Tmpl) {
  return B.View.extend({

    className: 'topbar',

    events: {
      'tap .back-button': function(e) {
        // TO-DO: para navegar entre jerarquías hará distinguir entre las acciones 'back' y 'up'
        // Ej: moverse entre los Pois resultados de una búsquedas 
        // Necesitará entonces mantener un historial de navegación con jerarquías
        this.trigger('historyback');
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
        Tmpl(_.extend(this.controlDefaults(), this.options))
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