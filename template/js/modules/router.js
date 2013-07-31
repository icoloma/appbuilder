/*
  El router central del app
*/
define(
  [ 
    'globals', 'menu.config', 'db/utils',
    'page/pages', 'schemas/schemas', 'ui/basedialogview',
    'poi/poi', 'poi/collection'
  ],
  function(Globals, MenuConfig, DbUtils, Page, Db, DialogView, Poi) {
    return B.Router.extend({

      routes: {
        '': 'renderHome',
        'menu/:menuId': 'renderMenu',
        'pois(?:query)': 'renderPois',
        'pois/:poiId': 'renderPoi'
      },

      initialize: function(options) {
        this.$el = options.$el;
      },

      setView: function(view, options) {
        this.currentView = new view(options).render();
        this.$el.html(this.currentView.$el);
        return this.currentView;
      },

      dialog: function(dialogView) {
        this.$el.prepend(dialogView.render().$el);
      },

      renderHome: function() {
        var menu = MenuConfig.menus[MenuConfig.root.menu]
        , collection = new B.Collection(
          menu.entries.map(function(entry) {
            return new B.Model(entry);
          })
        )
        ;
        this.setView(Page.HomeView, {
          title: menu.title,
          collection: collection,
          pois: MenuConfig.root.pois
        });
      },

      renderMenu: function(menuId) {
        var menu = MenuConfig.menus[menuId]
        , collection = new B.Collection(
          menu.entries.map(function(entry) {
            return new B.Model(entry);
          })
        )
        ;

        this.setView(Page.MenuView, {
          title: menu.title,
          collection: collection
        });
      },

      renderPois: function(query) {
        var self = this
        , parsedQuery = query ? DbUtils.parseQuery(query) : {}
        ;

        async.parallel({
          pois: function(cb) {
            // TODO: esto tiene que ser más general
            Db.Poi.all()
              .query(parsedQuery)
              .asJSON(Db.Poi, function(pois) {
                cb(null, pois);
              });
          },
          title: function(cb) {
            // Busca el título adecuado para la página
            if (parsedQuery.q) {
              cb(null, res.searchResults);
            } else if (parsedQuery.starred) {
              cb(null, res.Starred);
            } else if (parsedQuery.title) {
              cb(null, parsedQuery.title);
            } else {
              cb(null, '');
            }
          }
        }, function(err, results) {
          var collection = new Poi.Collection(results.pois.map(function(poi) {
            return new Poi.Model(poi);
          }));
          self.setView(Page.PoisView, {
            collection: collection,
            title: results.title,
          });
        });
      },

      renderPoi: function(poiId) {
        var self = this;
        Db.Poi.findBy('id', poiId, function(poi) {
          self.setView(Page.PoiView, {
            model: new Poi.Model(DbUtils.localizedJSON(poi)),
            title: poi.name
          });
        });
      }
    });
  }
);