define(
  ['poi/articleview', 'ui/topbarview'],
  function(ArticleView, TopbarView) {


    return B.View.extend({
      className: 'pageview poiview',

      initialize: function() {
        this.topbarView = new TopbarView({
          title: this.options.title,
          actions: ['search']
        });
        this.pass(this.topbarView, 'historyback');

        this.modelView = new ArticleView({
          model: this.model
        });
        this.listenTo(this.modelView, 'star', this.star);
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        this.$el.append(this.modelView.render().$el);
        return this;
      },

      star: function() {
        var self = this;
        this.model.set('starred', !this.model.get('starred'));
        var message = this.model.get('starred') ? res.bookmarkAdded : res.bookmarkRemoved;
        this.model.persist(function() {
          navigator.notification.alert(message, null, res.Starred);

          self.modelView.render();
        });
      }
    });
  }
);