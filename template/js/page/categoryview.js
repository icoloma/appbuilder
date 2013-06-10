define(
  ['modules/baselistview', 'category/trview', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'categoryview',

      initialize: function() {
        this.collectionView = new ListView({
          collection: this.collection,
          url: '#/pois?subcategory=',
          trView: TrView
        });
        this.topbarView = new TopbarView();
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        this.$el.append(this.collectionView.render().$el);
        return this;
      }

    });
  }
);