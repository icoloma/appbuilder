define(
  ['modules/baselistview', 'category/trview'],
  function(ListView, TrView) {

    return B.View.extend({

      initialize: function() {
        this.collectionView = new ListView({
          collection: this.collection,
          url: '#/pois?subcategory=',
          trView: TrView
        });
      },

      render: function() {
        this.$el.html(this.collectionView.render().$el);
        return this;
      }

    });
  }
);