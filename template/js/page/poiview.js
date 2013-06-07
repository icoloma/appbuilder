define(
  ['poi/articleview', 'ui/actionbarview', 'ui/basedialogview'],
  function(ArticleView, ActionbarView, DialogView) {


    return B.View.extend({
      className: 'poiview',

      initialize: function() {
        this.actionbarView = new ActionbarView({
          title: this.model.get('name'),
          map: this.model.geoLink(),
          star: true,
          starred: this.model.get('starred')
          // TODO: implementar notificaciones
          // notify: true
        });
        this.listenTo(this.actionbarView, 'star', this.star);
        this.modelView = new ArticleView({
          model: this.model
        });
      },

      render: function() {
        this.$el.html(this.actionbarView.render().$el);
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
          self.trigger('dialog', dialogView);

          self.actionbarView.options.starred = this.get('starred');
          self.actionbarView.render();
        });
      }
    });
  }
);