/*
  Vista del listado de favoritos.
  Esta vista es básicamente una vista de POIs, con la salvedad de que necesita un botón para
  el travel planner

*/
define(['page/poisview'], function(PoisView) {
  return PoisView.extend({
    initialize: function() {
      PoisView.prototype.initialize.apply(this, arguments);

      this.topbarView.options.actions = 
        this.collection.length ? ['planner'] : [];

      var self = this;
      this.listenTo(this.topbarView, 'planner', function() {
        self.trigger('navigate', 'planner', 1);
      });
    }
  });
});