define(
  ['modules/baselistview', 'poi/trview', 'ui/topbarview', 'modules/geo',
   'poi/collection', 'ui/basedialogview'],
  function(ListView, TrView, TopbarView, Geo, PoiCollection, DialogView) {

    return B.View.extend({
      className: 'poisview',

      initialize: function() {
        this.topbarView = new TopbarView({
          title: this.options.name,
          filter: true,
          sort: true
        });
        this.listenTo(this.topbarView, 'sort', this.sort);
        this.listenTo(this.topbarView, 'filter', this.filterDialog);
        this.collectionView = new ListView({
          className: 'poicollectionview',
          collection: this.collection,
          url: '#/pois/',
          trView: TrView
        });
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        this.$el.append(this.collectionView.render().$el);
        return this;
      },

      sort: function() {
        var self = this
        , collection = this.collectionView.collection
        ;
        navigator.geolocation.getCurrentPosition(function(position) {
          self.topbarView.unblock();
          var coords = position.coords;
          collection.comparator = PoiCollection.sortByDistanceTo(coords.latitude,
                                                           coords.longitude);
          collection.sort();
        }, function(err) {
          // TODO
          self.topbarView.unblock();
          self.trigger('dialog', new DialogView({
            content: '<p>' + res.geoError + '</p>' 
          }));
        });
      },

      filter: function(distance) {
        var self = this
        , collection = this.collectionView.collection
        ;

        navigator.geolocation.getCurrentPosition(function(position) {
          self.topbarView.unblock();
          
          var coords = position.coords;
          collection.comparator = PoiCollection.sortByDistanceTo(coords.latitude,
                                                           coords.longitude);

          collection
            .filterByDistanceTo(coords.latitude, coords.longitude, distance)
            .sort();
        }, function(err) {
          // TODO
          self.topbarView.unblock();
          self.trigger('dialog', new DialogView({
            content: '<p>' + res.geoError + '</p>' 
          }));
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