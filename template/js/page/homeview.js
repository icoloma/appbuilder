define(
  ['modules/baselistview', 'category/trview', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'homeview',

      initialize: function() {
        this.collectionView = new ListView({
          url: '#/category/',
          collection: this.collection,
          trView: TrView
        });
        this.topbarView = new TopbarView({
          starredPois: true,
          root: true
        });
      },


      render: function() {
        this.$el.html(this.topbarView.render().$el);
        this.$el.append(this.collectionView.render().$el);
        return this;
      }
    });
  }
);