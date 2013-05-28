define(
  ['poi/articleview', 'ui/navbarview'],
  function(ArticleView, NavbarView) {


    return B.View.extend({
      className: 'poiview',

      render: function() {
        this.navbarView = new NavbarView({
          title: this.model.name,
          map: true,
          star: true,
          notify: true
        });
        this.modelView = new ArticleView({
          model: this.model
        });
        this.$el.html(this.navbarView.render().$el);
        this.$el.append(this.modelView.render().$el);
        return this;
      }

    });
  }
);