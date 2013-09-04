/*
  El router central del app
*/
define(
  [ 
    'page/pages', 'db/db', 'poi/poi', 'poi/collection', 'menu/model'
  ],
  function(Page, Db, Poi, PoiCollection, Menu) {

    var menuConfig;

    return B.Router.extend({

      routes: {
        '': 'renderHome',
        'menu/:menuId': 'renderMenu',
        'pois?(:uri)': 'renderPois',
        'pois/:poiId': 'renderPoi'
      },

      initialize: function(options) {
        this.$el = options.$el;
        menuConfig = window.res._metadata.menuConfig;
        this.direction = 1;
        this.cache = [ {uri: ''} ];

        var self = this;
        $(document).on('backbutton', function(e) {
          if (location.hash) {
            e.preventDefault();
            self.navigateTo(null, -1);
          } else {
            // AVISO: esto NO es cross-platform
            navigator.app.exitApp();
          }
        });
      },

      navigateTo: function(uri, dir) {
        if (dir < 0) this.direction = -1;
        if (dir > 0) {
          _.last(this.cache).scroll = window.pageYOffset;
          this.cache.push({
            uri: uri
          });
          window.location.hash = uri;
        } else {
          this.cache.pop();
          var entry = _.last(this.cache);
          this.newScroll = entry.scroll;
          window.location.hash = entry.uri;
        }
      },

      updateUri: function(uriParams) {
        var oldUri = _.last(this.cache).uri
        , rawQueryMatch = location.hash.match(/(^[^?]+)(\?.+)?/)
        , uriObj = rawQueryMatch[2] ? 
          JSON.parse(decodeURIComponent(rawQueryMatch[2].slice(1))) :
          {}
        ;
        _.extend(uriObj, uriParams);
        var newUri = rawQueryMatch[1].slice(1) + '?' +
          encodeURIComponent(JSON.stringify(uriObj));
        _.last(this.cache).uri = newUri;
      },

      setView: function(view, options) {
        var newView = new view(options).render();

        if (this.currentView) {
          this.stopListening(this.currentView);
          this.$el.loadAnimation({
            $oldView: this.currentView.$el,
            $newView: newView.$el,
            newScroll: this.newScroll,
            direction: this.direction
          }, function() {
            newView.$el.trigger('pageviewready');
          });
          this.direction = 1;
        } else {
          this.$el.html(newView.$el.trigger('pageviewready'));
        }

        this.currentView = newView;
        this.listenTo(this.currentView, 'navigate', this.navigateTo);
        this.listenTo(this.currentView, 'updatequery', this.updateUri);
        return this.currentView;
      },

      renderHome: function() {
        var menu = menuConfig.menus[menuConfig.root.menu]
        , collection = new B.Collection(
          _.map(menu.entries, function(entryID) {
            return new Menu(menuConfig.entries[entryID]);
          })
        )
        ;
        this.setView(Page.HomeView, {
          title: menu.title,
          collection: collection,
          pois: menuConfig.root.pois
        });
      },

      renderMenu: function(menuId) {
        var menu = menuConfig.menus[menuId]
        , collection = new B.Collection(
          _.map(menu.entries, function(entryID) {
            return new Menu(menuConfig.entries[entryID]);
          })
        )
        ;

        this.setView(Page.MenuView, {
          title: menu.title,
          collection: collection
        });
      },

      renderPois: function(uri) {
        var uriObj = JSON.parse(decodeURIComponent(uri))
        , self = this
        , query = uriObj.query
        , sqlStr = Db.utils.queryToSql('Poi', query)
        ;
        Db.sqlAsCollection(PoiCollection, sqlStr, [], function(err, pois) {
          if (uriObj.sort) {
            pois.comparator = 
              PoiCollection.sortByDistanceTo(uriObj.sort.lat, uriObj.sort.lon);
            pois.sort();
          }
          self.setView(Page.PoisView, {
            collection: pois,
            title: uriObj.title,
            cursor: uriObj.cursor
          });
        });
      },

      renderPoi: function(poiId) {
        var self = this;
        Db.sql('SELECT * FROM Poi WHERE `id`=?', [poiId], function(err, pois) {
          var poi = new Poi.Model(pois[0])
          , type = window.res._metadata.types[poi.get('type')]
          ;
          self.setView(Page.PoiView, {
            model: new Poi.Model(pois[0]),
            title: type.name 
          });
        });
      }
    });
  }
);