define(['ui/topbarview', 'travelplanner/planner', 'list/sortablelistview',
        'tpl!poi/plannertrview.tpl'],
  function(TopbarView, Planner, ListView, TrView) {
  return B.View.extend({

    className: 'pageview',

    initialize: function() {
      this.topbarView = new TopbarView({
        title: this.options.title
      });

      this.pass(this.topbarView, 'navigate');

      this.getPlan();
    },

    render: function() {
      this.$el.html(this.topbarView.render().$el);

      // Arregla la pérdida de listeners en el re-render
      this.topbarView.delegateEvents();

      if (this.collectionView) {
        this.$el.append(this.collectionView.render().$el);
      }

      return this;
    },

    getPlan: function() {
      var self = this;

      this.topbarView.block();

      Planner.getTravelPlan(this.options, function(err, plan) {
        // TO-DO: error handling
        if (err) console.log(err);
        self.topbarView.unblock();

        if (self.collectionView) {
          self.stopListening(self.collectionView);
        }

        var planArray = Array.prototype.concat.apply(plan[0], plan.slice(1));
        self.collectionView = new ListView({
          className: 'poisplanview collectionview',
          collection: new B.Collection(_.map(planArray, function(step) {
            step.poi = step.poi && step.poi.toJSON();
            return new B.Model(step);
          })),
          trView: TrView
        });
        
        self.render();
      });
    }

  });
});