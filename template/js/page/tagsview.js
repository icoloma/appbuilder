define(
  ['modules/baselistview', 'category/trview', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'tagsview',

      initialize: function() {
        this.collectionView = new ListView({
          collection: this.collection,
          url: '#/pois?tag=',
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