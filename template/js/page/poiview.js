define(
  ['poi/articleview', 'ui/actionbarview'],
  function(ArticleView, ActionbarView) {


    return B.View.extend({
      className: 'poiview',

      initialize: function() {
        this.actionbarView = new ActionbarView({
          title: this.model.get('name'),
          map: this.model.geoLink(),
          star: true,
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
        this.model.set('starred', !this.model.get('starred'));
        var message = this.model.get('starred') ? res.bookmarkAdded : res.bookmarkRemoved;
        this.model.persist(function()  {
          alert(message);
        });
      }

    });
  }
);