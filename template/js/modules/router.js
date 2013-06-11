/*
  El router central del app
*/
define(
  [ 
    'globals', 'modules/query',
    'page/pages', 'schemas/schemas', 'ui/basedialogview',
    'poi/model', 'poi/collection'
  ],
  function(Globals, Query, Page, Db, DialogView, PoiModel, PoiCollection) {

    return B.Router.extend({

      routes: {
        '': 'renderHome',
        'category/:category': 'renderCategory',
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
        var self = this;
        Db.Category.all().asCollection(function(cats) {
          self.setView(Page.homeView, {
            collection: cats,
            title: window.appConfig.zone,
          });
        });
      },

      renderCategory: function(category) {
        var self = this;
        async.parallel({
          subcategories: function(cb) {
            Db.SubCategory.all().filter('category', '=', category).asCollection(function(subcats) {
              cb(null, subcats);
            });
          },
          category: function(cb) {
            Db.Category.findBy('id', category, function(cat) {
              cb(null, cat);
            });
          }
        }, function(err, results) {
          self.setView(Page.categoryView, {
            collection: results.subcategories,
            title: results.category.name
          });
        });
      },

      renderPois: function(query) {
        var self = this
        , parsedQuery = query ? Query.parseQuery(query) : {}
        ;

        async.parallel({
          pois: function(cb) {
            Db.Poi.all().query(parsedQuery).asJSON(function(pois) {
              cb(null, pois);
            });
          },
          title: function(cb) {
            // Busca el título adecuado para la página
            if (parsedQuery.q) {
              cb(null, res.searchResults);
            } else if (parsedQuery.starred) {
              cb(null, res.Starred);
            } else if (parsedQuery.subcategory) {
              Db.SubCategory.findBy('id', parsedQuery.subcategory, function(subcat) {
                cb(null, subcat.name);
              });
            } else {
              cb(null, '');
            }
          }
        }, function(err, results) {
          var collection = new PoiCollection(results.pois.map(function(poi) {
            return new PoiModel(poi);
          }));
          self.setView(Page.poisView, {
            collection: collection,
            title: results.title,
          });
        });
      },

      renderPoi: function(poiId) {
        var self = this;
        Db.Poi.findBy('id', poiId, function(poi) {
          self.setView(Page.poiView, {
            model: new PoiModel(poi.toJSON()),
            title: poi.name
          });
        });
      }
    });
  }
);