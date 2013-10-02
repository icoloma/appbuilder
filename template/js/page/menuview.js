define(
  ['list/baselistview', 'tpl!menu/trview.tpl', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'pageview menuview',

      initialize: function() {
        this.collectionView = new ListView({
          collection: this.collection,
          trView: TrView
        });
        this.topbarView = new TopbarView({
          title: this.options.title,
          actions: ['search']
        });
        this.pass(this.topbarView, 'navigate');
        this.pass(this.collectionView, 'navigate');

        this.listenTo(this.topbarView, 'search', function() {
          this.trigger('navigate', '/search?', 1);
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