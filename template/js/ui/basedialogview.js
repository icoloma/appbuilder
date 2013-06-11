define(['globals', 'modules/touch'], function(Globals, Touch) {
  return B.View.extend({
    className: 'dialog-container',

    events: {
      'tap .dialog-mask': 'dismiss'
    },

    addEvents: function(events) {
      this.delegateEvents(_.extend(_.clone(this.events), events));
    },

    tmpl: 
      '<div class="dialog-mask"></div>' +
      '<div class="dialog"></div>'
  ,

    render: function() {
      this.$el.html(this.tmpl);
      this.$el.find('.dialog').append($.parseHTML(this.options.content));

      Touch.delegateScroll();
      this.bindBackButton();
      return this;
    },

    close: function() {
      Touch.undelegateScroll();
      $(document).off('backbutton.dismiss');
      this.remove();
    },

    dismiss: function() {
      this.trigger('dismiss');
      this.close();
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