define(
  ['modules/baselistview', 'poi/trview', 'ui/navbarview'],
  function(ListView, TrView, NavbarView) {

    return B.View.extend({


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

      render: function() {
        this.navbarView = new NavbarView({
          title: this.options.name,
          filter: true,
          sort: true
        });
        this.collectionView = new ListView({
          collection: this.collection,
          url: '#/pois/',
          trView: TrView
        });
        this.$el.html(this.navbarView.render().$el);
        this.$el.append(this.collectionView.render().$el);
        return this;
      }

    });
  }
);