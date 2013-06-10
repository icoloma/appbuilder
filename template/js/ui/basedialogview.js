define(['globals', 'modules/touch'], function(Globals, Touch) {
  return B.View.extend({
    className: 'dialog-container',

    events: {
      'tap': 'dismiss'
    },

    tmpl: 
    // _.template(
      '<div class="dialog-mask"></div>' +
      '<div class="dialog">' +
        // '<span class="close-dialog icon-close"></span>' +
      '</div>'
    // )
  ,

    render: function() {
      this.$el.html(this.tmpl);
      this.$el.find('.dialog').append($.parseHTML(this.options.content));

      Touch.delegateScroll();
      this.bindBackButton();
      return this;
    },

    dismiss: function() {
      Touch.undelegateScroll();
      $(document).off('backbutton.dismiss');
      this.remove();
    },

    bindBackButton: function() {
      var self = this;
      $(document).on('backbutton.dismiss', function(e) {
        e.preventDefault();
        self.dismiss();
      });
    }
  });
});