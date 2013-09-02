define(['globals', 'tpl!ui/topbarview.tpl'],
function(Globals, Tmpl) {
  return B.View.extend({

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
      'tap .menu-button': 'toggleMenu'
    },

    buttonTmpl: _.template(
      '<span data-action="{{action}}" class="titlesize icon-{{action}} bar-button"></span>'
    ),

    render: function() {
      var wrappedMenu = this.options.actions.length > 1;
      _.extend(this.options, {
        hideBackButton: this.options.root ? 'invisible' : '',
        hideMenuButton: wrappedMenu ? '' : 'hide',
        wrapActions: wrappedMenu ? 'wrapped-action-menu' : '',
        actions: _.map(this.options.actions, function(action) {
          return this.buttonTmpl({action: action});
        }, this).join('')
      });
      this.$el.html(Tmpl(this.options));
      this.$button = wrappedMenu ? this.$('.menu-button') : this.$('.actionbar').first();
      return this;
    },

    block: function() {
      this.isBlocked = true;
      this.$button.addClass('blocked');
    },

    unblock: function() {
      this.isBlocked = false;
      this.$button.removeClass('blocked');
    },

    toggleMenu: function() {
      this.$('.wrapped-action-menu').toggle();
    }
  });
});