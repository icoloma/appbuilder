define(['globals', 'tpl!ui/topbarview.tpl'],
function(Globals, Tmpl) {
  return B.View.extend({

    /*

      topbarview: este componente actúa como barra de navegación y "actionbar".

      @options.root: indica si la barra ha de mostrar el botón back (root == false) o no (root == true)
      @options.actions: un array de acciones para mostrar en la action bar 

      this.isBlocked: indica que la actionbar está bloqueada por alguna operación asíncrona
      this.$button: el botón principal de la action bar.
    */

    className: 'topbar',

    events: {
      'tap [data-action]': function(e) {
        var action = $(e.target).data('action');

        if (action === 'historyback')  {
          this.trigger('navigate', null, -1);
        } else {
          if (!this.isBlocked) {
            this.trigger(action);
            this.block();
          }
          this.toggleMenu();
        }
      },
      'tap .menu-button': function(e) {
        if (!this.isBlocked) {
          this.toggleMenu();
        }
      }
    },

    render: function() {
      // Si hay más de una acción, se muestran las acciones en un menú desplegable
      var wrappedMenu = this.options.actions && this.options.actions.length > 1;

      _.extend(this.options, {
        hideBackButton: this.options.root ? 'invisible' : '',
        hideMenuButton: wrappedMenu ? '' : 'hide',
        wrapActions: wrappedMenu ? 'wrapped-action-menu' : '',
        actions: this.options.actions
      });

      this.$el.html(Tmpl(this.options));
      return this;
    },

    block: function() {
      this.isBlocked = true;
      this.$('.actionbar').addClass('blocked');
    },

    unblock: function() {
      this.isBlocked = false;
      this.$('.actionbar').removeClass('blocked');
    },

    toggleMenu: function() {
      this.$('.wrapped-action-menu').toggle();
    }
  });
});