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
          actions: ['sort', 'map']
        });
        this.listenTo(this.topbarView, 'sort', this.sort);
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
          self.trigger('updatequery', {
            sort: {
              lat: coords.latitude,
              lon: coords.longitude
            }
          });
        }, function(err) {
          self.topbarView.unblock();
          navigator.notification.alert(res.i18n.geoError, null, res.i18n.Error);
        });
      },

      // filter: function(distance) {
      //   var self = this
      //   , collection = this.collectionView.collection
      //   ;

      //   navigator.geolocation.getCurrentPosition(function(position) {
      //     self.topbarView.unblock();

      //     var coords = position.coords;
      //     collection.comparator = PoiCollection.sortByDistanceTo(coords.latitude,
      //                                                      coords.longitude);

      //     collection
      //       .filterByDistanceTo(coords.latitude, coords.longitude, distance)
      //       .sort();
      //   }, function(err) {
      //     // TO-DO
      //     self.topbarView.unblock();
      //     self.$el.prepend(new DialogView({
      //       content: '<p>' + res.i18n.geoError + '</p>' 
      //     }).render().$el);
      //   });
      // },

    //   filterDialog: function() {
    //     var self = this
    //     , options = _.template(
    //       '<ul>' +
    //         '<li data-distance="1">1 {{res.i18n.kilometers}}</li>' +
    //         '<li data-distance="5">5 {{res.i18n.kilometers}}</li>' +
    //         '<li data-distance="10">10 {{res.i18n.kilometers}}</li>' +
    //         '<li data-distance="20">20 {{res.i18n.kilometers}}</li>' +
    //       '</ul>'
    //     , {})
    //     , dialogView = new DialogView({
    //       content: options
    //     })
    //     ;
    //     dialogView.addEvents({
    //       'tap li': function(e) {
    //         var distance = Number($(e.target).closest('[data-distance]').data('distance'));
    //         this.close();
    //         self.filter(distance);
    //       }
    //     });
    //     dialogView.on('dismiss', function() {
    //       self.topbarView.unblock();
    //     });
    //     this.$el.prepend(dialogView.render().$el);
    //   }

    });
  }
);