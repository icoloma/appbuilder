define(
  ['poi/articleview', 'ui/topbarview', 'ui/basedialogview'],
  function(ArticleView, TopbarView, DialogView) {


    return B.View.extend({
      className: 'pageview poiview',

      initialize: function() {
        this.topbarView = new TopbarView({
          title: this.model.get('name'),
          // TODO: implementar notificaciones
          // notify: true
        });
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
          var dialogView = new DialogView({
            content: '<p>' + message + '</p>'
          });
          self.$el.prepend(dialogView.render().$el);

          self.modelView.render();
        });
      }
    });
  }
);