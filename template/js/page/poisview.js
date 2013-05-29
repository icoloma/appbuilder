define(
  ['modules/baselistview', 'poi/trview', 'ui/actionbarview'],
  function(ListView, TrView, ActionbarView) {

    return B.View.extend({

      initialize: function() {
        this.actionbarView = new ActionbarView({
          title: this.options.name,
          filter: true,
          sort: true
        });
        this.listenTo(this.actionbarView, 'sort', this.sort);
        this.collectionView = new ListView({
          collection: this.collection,
          url: '#/pois/',
          trView: TrView
        });
      },

      render: function() {
        this.$el.html(this.actionbarView.render().$el);
        this.$el.append(this.collectionView.render().$el);
        return this;
      },

      sort: function() {

      },

      sortByDistance: function(lat, lon) {
        this.collection.sort(function(poi) {
          var normLon = lon*Math.sin(lat/180*Math.PI)
          ;
          return Math.pow((lat-poi.lat)/180*Math.PI,2) +
                  Math.pow(normLon-poi.normLon,2);
        });
        this.collectionView.render();
        return this;
      },

    });
  }
);