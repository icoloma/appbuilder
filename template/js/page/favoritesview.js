define(['page/poisview'], function(PoisView) {
  return PoisView.extend({
    initialize: function() {
      PoisView.prototype.initialize.apply(this, arguments);

      this.topbarView.options.actions = ['planner'];

      var self = this;
      this.listenTo(this.topbarView, 'planner', function() {
        self.trigger('navigate', 'planner', 1);
      });
    }
  });
});