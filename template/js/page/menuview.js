define(
  ['modules/baselistview', 'category/trview', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'menuview',

      initialize: function() {
        this.collectionView = new ListView({
          collection: this.collection,
          trView: TrView
        });
        this.topbarView = new TopbarView({
          title: this.options.title,
          search: true,
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