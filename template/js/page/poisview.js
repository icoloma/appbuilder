define(
  ['modules/paginatedlistview', 'tpl!poi/trview.tpl', 'ui/topbarview', 'modules/geo',
   'poi/collection'],
  function(ListView, TrView, TopbarView, Geo, PoiCollection) {

    return B.View.extend({
      className: 'pageview poisview',

      events: {
        'pageviewready': function() {
          this.collectionView.monitorScroll();
        }
      },

      initialize: function() {
        this.topbarView = new TopbarView({
          title: this.options.title,
          actions: ['map']
        });

        // TO-DO: implementar vista de mapas
        // this.listenTo(this.topbarView, 'map', ...);
        this.pass(this.topbarView, 'navigate');

        this.collectionView = new ListView({
          className: 'collectionview poicollectionview',
          collection: this.collection,
          trView: TrView,
          cursor: this.options.cursor
        });
        this.pass(this.collectionView, 'updatequery');
        this.pass(this.collectionView, 'navigate');
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        this.$el.append(this.collectionView.render().$el);
        return this;
      },

    });
  }
);