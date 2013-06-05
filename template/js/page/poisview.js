define(
  ['modules/baselistview', 'poi/trview', 'ui/actionbarview', 'modules/geo', 'poi/collection'],
  function(ListView, TrView, ActionbarView, Geo, PoiCollection) {

    return B.View.extend({

      initialize: function() {
        this.actionbarView = new ActionbarView({
          title: this.options.name,
          filter: true,
          sort: true
        });
        this.listenTo(this.actionbarView, 'sort', this.sort);
        this.listenTo(this.actionbarView, 'filter', function() {
          this.filter(50);
        });
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
        var collection = this.collectionView.collection;
        navigator.geolocation.getCurrentPosition(function(position) {
          var coords = position.coords;
          collection.comparator = PoiCollection.sortByDistanceTo(coords.latitude,
                                                           coords.longitude);
          collection.sort();
        }, function(err) {
          // TODO
          alert(res.geoError);
        });
      },

      filter: function(distance) {
        var collection = collectionView.collection;

        navigator.geolocation.getCurrentPosition(function(position) {
          var coords = position.coords;
          collection.comparator = PoiCollection.sortByDistanceTo(coords.latitude,
                                                           coords.longitude);

          collection
            .filterByDistanceTo(coords.latitude, coords.longitude, distance)
            .sort();
        }, function(err) {
          // TODO
          alert(res.geoError);
        });
      }

    });
  }
);