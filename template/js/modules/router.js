/*
  El router central del app
*/
define(
  [ 
    'menu.config', 'page/pages', 'db/db', 'ui/basedialogview',
    'poi/poi', 'poi/collection'
  ],
  function(MenuConfig, Page, Db, DialogView, Poi, PoiCollection) {
    return B.Router.extend({

      routes: {
        '': 'renderHome',
        'menu/:menuId': 'renderMenu',
        'pois?(:data)': 'renderPois',
        'pois/:poiId': 'renderPoi'
      },

      initialize: function(options) {
        this.$el = options.$el;
      },

      setView: function(view, options) {
        if (this.currentView) {
          var newView = new view(options).render();
          // this.$el.loadAnimation(this.currentView.$el, newView.$el, -1);
          this.$el.html(newView.$el);
          this.currentView = newView;
          return this.currentView;
        } else {
          this.currentView = new view(options).render();
          this.$el.html(this.currentView.$el);
          return this.currentView;
        }
      },

      // dialog: function(dialogView) {
      //   this.$el.prepend(dialogView.render().$el);
      // },

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

      renderPois: function(data) {
        data = JSON.parse(decodeURIComponent(data));
        var self = this
        , query = data.query
        , sqlStr = Db.utils.queryToSql('Poi', query)
        ;
        Db.sqlAsCollection(PoiCollection, sqlStr, [], function(err, pois) {
          self.setView(Page.PoisView, {
            collection: pois,
            title: data.title
          });
        });
      },

      renderPoi: function(poiId) {
        var self = this;
        Db.sql('SELECT * FROM Poi WHERE `id`=?', [poiId], function(err, pois) {
          self.setView(Page.PoiView, {
            model: new Poi.Model(pois[0]),
            title: pois[0].name
          });
        });
      }
    });
  }
);