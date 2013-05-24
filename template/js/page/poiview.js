define(
  ['poi/articleview'],
  function(ArticleView) {


    return B.View.extend({

      className: 'poiview',


      render: function() {
        this.$el.html(
          new ArticleView({
            model: this.model
          }).render().$el
        );
        return this
      }

    });
  }
);