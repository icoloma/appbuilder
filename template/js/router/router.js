/*
  El router central del app
*/
define(
  [ 
    'router/baserouter', 'page/pages', 'db/db', 'poi/poi', 'menu/model',
  ],
  function(BaseRouter, Page, Db, Poi, Menu) {

    var menuConfig;

    return BaseRouter.extend({

      routes: {
        '': 'renderHome',
        'menu/:menuId': 'renderMenu',
        'pois?(:uri)': 'renderPois',
        'pois/:poiId': 'renderPoi',
        'search?(:query)': 'renderSearch'
      },

      initialize: function(options) {
        menuConfig = window.res._metadata.menuConfig;
        BaseRouter.prototype.initialize.call(this, options);
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
        , queryConditions = uriObj.queryConditions
        , sqlStr = 'SELECT id,thumb,address,name_' + appConfig.locale + ' FROM Poi WHERE ' + queryConditions
        ;
        Db.sql(sqlStr, [], function(err, pois) {
          pois = new Poi.Collection(_.map(pois, function(poi) {
            return new Poi.Model(poi, {parse: true});
          }));

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
        Db.sql('SELECT ' + Poi.Model.sqlFields + ' FROM Poi WHERE `id`=?', [poiId], function(err, pois) {
          var poi = new Poi.Model(pois[0], {parse: true})
          , type = window.res._metadata.types[poi.get('type')]
          ;
          self.setView(Page.PoiView, {
            model: poi,
            title: type.name 
          });
        });
      },

      renderSearch: function(prevQuery) {
        this.setView(Page.SearchView, JSON.parse(prevQuery || null));
      }
    });
  }
);