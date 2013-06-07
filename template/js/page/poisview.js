define(
  ['modules/baselistview', 'poi/trview', 'ui/actionbarview', 'modules/geo',
   'poi/collection', 'ui/basedialogview'],
  function(ListView, TrView, ActionbarView, Geo, PoiCollection, DialogView) {

    return B.View.extend({
      className: 'poisview',

      initialize: function() {
        this.actionbarView = new ActionbarView({
          title: this.options.name,
          filter: true,
          sort: true
        });
        this.listenTo(this.actionbarView, 'sort', this.sort);
        this.listenTo(this.actionbarView, 'filter', this.filterDialog);
        this.collectionView = new ListView({
          className: 'poicollectionview',
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
        var collection = this.collectionView.collection;

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
      },

      filterDialog: function() {
        var self = this
        , options = _.template(
          '<ul>' +
            '<li data-distance="1">1 {{res.kilometers}}</li>' +
            '<li data-distance="5">5 {{res.kilometers}}</li>' +
            '<li data-distance="10">10 {{res.kilometers}}</li>' +
            '<li data-distance="20">20 {{res.kilometers}}</li>' +
          '</ul>'
        , {})
        , dialogView = new DialogView({
          content: options
        })
        ;
        dialogView.delegateEvents(_.extend(_.clone(dialogView.events), {
          'tap li': function(e) {
            var distance = Number($(e.target).closest('[data-distance]').data('distance'));
            this.dismiss();
            self.filter(distance);
          }
        }));
        this.trigger('dialog', dialogView);
      }

    });
  }
);