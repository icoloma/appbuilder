/*
  El router central del app
*/
define(
  [ 
    'page/pages', 'db/db', 'ui/basedialogview',
    'poi/poi', 'poi/collection', 'db/metadata'
  ],
  function(Page, Db, DialogView, Poi, PoiCollection, Metadata) {

    var menuConfig;

    return B.Router.extend({

      routes: {
        '': 'renderHome',
        'menu/:menuId': 'renderMenu',
        'pois?(:data)': 'renderPois',
        'pois/:poiId': 'renderPoi'
      },

      initialize: function(options) {
        this.$el = options.$el;
        menuConfig = Metadata.menuConfig;
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
        var menu = menuConfig.menus[menuConfig.root.menu]
        , collection = new B.Collection(
          _.map(menu.entries, function(entryID) {
            return new B.Model(menuConfig.entries[entryID]);
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
            return new B.Model(menuConfig.entries[entryID]);
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
          var poi = new Poi.Model(pois[0])
          , type = Metadata.types[poi.get('type')]
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