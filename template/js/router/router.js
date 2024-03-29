/*
  El router central del app
*/
define(
  [ 
    'router/baserouter', 'page/pages', 'db/db', 'poi/poi', 'menu/model',
  ],
  function(BaseRouter, Page, Db, Poi, Menu) {

    return BaseRouter.extend({

      routes: {
        '': 'renderHome',
        'menu/:menuId': 'renderMenu',
        'pois?(:uri)': 'renderPois',
        'pois/:poiId': 'renderPoi',
        'search?(:prevQuery)': 'renderSearch',
        'bookmarks(?:uri)': 'renderBookmarks',
        'travelplanner/details(?:uri)': 'renderTravelDetails',
        'travelplanner/travel(?:uri)': 'renderTravelPlan'
      },

      initialize: function(options) {
        BaseRouter.prototype.initialize.call(this, options);
      },

      renderHome: function() {
        var menu = res.menus[res.rootMenu.menu]
        , collection = new B.Collection(
          _.map(menu.entries, function(entryID) {
            return new Menu(res.menuEntries[entryID]);
          })
        )
        ;
        this.setView(Page.HomeView, {
          title: menu.title,
          collection: collection,
          pois: res.rootMenu.pois
        });
      },

      renderMenu: function(menuId) {
        var menu = res.menus[menuId]
        , collection = new B.Collection(
          _.map(menu.entries, function(entryID) {
            return new Menu(res.menuEntries[entryID]);
          })
        )
        ;

        this.setView(Page.MenuView, {
          title: menu.title,
          collection: collection
        });
      },

      renderPois: function(uri) {
        var uriObj = JSON.parse(uri)
        , self = this
        , queryConditions = uriObj.queryConditions
        , sqlStr = 'SELECT id,thumb,address,name_' + res.locale + ' FROM Poi WHERE ' + queryConditions
        ;
        Db.sql(sqlStr, [], function(err, pois) {
          pois = new Poi.Collection(_.map(pois, function(poi) {
            return new Poi.Model(poi, {parse: true});
          }));

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
          , type = window.res.types[poi.get('type')]
          ;
          self.setView(Page.PoiView, {
            model: poi,
            title: type.name 
          });
        });
      },

      renderSearch: function(prevQuery) {
        this.setView(Page.SearchView, JSON.parse(prevQuery || null));
      },

      renderBookmarks: function(uri) {
        var sqlStr = 'SELECT id,thumb,address,name_' + res.locale +
        ' FROM Poi WHERE starred > -1 ORDER BY starred'
        , self = this
        , uriObj = uri ? JSON.parse(decodeURIComponent(uri)) : {}
        ;

        Db.sql(sqlStr, [], function(err, pois) {
          pois = new Poi.Collection(_.map(pois, function(poi) {
            return new Poi.Model(poi, {parse: true});
          }));

          self.setView(Page.FavoritesView, {
            collection: pois,
            title: res.i18n.Starred,
            cursor: uriObj.cursor
          });
        });
      },

      renderTravelDetails: function(uri) {
        var uriObj = uri ? JSON.parse(decodeURIComponent(uri)) : {};

        this.setView(Page.TravelDetailsView, _.extend(uriObj, {
          title: res.i18n.TravelPlanner
        }));
      },

      renderTravelPlan: function(uri) {
        var uriObj = uri ? JSON.parse(decodeURIComponent(uri)) : {};

        this.setView(Page.TravelPlanView, _.extend(uriObj, {
          title: res.i18n.TravelPlanner
        }));
      },

    });
  }
);