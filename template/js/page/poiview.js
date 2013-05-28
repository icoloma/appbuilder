define(
  ['poi/articleview', 'ui/navbarview'],
  function(ArticleView, NavbarView) {


    return B.View.extend({
      className: 'poiview',

      initialize: function() {
        this.navbarView = new NavbarView({
          title: this.model.name,
          map: true,
          star: true,
          notify: true
        });
        this.listenTo(this.navbarView, 'star', this.star);
        this.modelView = new ArticleView({
          model: this.model
        });
      },

      render: function() {
        this.$el.html(this.navbarView.render().$el);
        this.$el.append(this.modelView.render().$el);
        return this;
      },

      star: function() {
        this.model.starred = !this.model.starred;
        var message = this.model.starred ? res.bookmarkAdded : res.bookmarkRemoved;
        persistence.flush(function() {
          alert(message);
        });
      }

    });
  }
);