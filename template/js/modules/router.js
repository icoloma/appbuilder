/*
  El router central del app
*/
define(
  [ 
    'globals', 'modules/query',
    'page/pages', 'schemas/schemas', 'ui/basedialogview',
    'poi/poi', 'poi/collection'
  ],
  function(Globals, Query, Page, Db, DialogView, Poi) {

    return B.Router.extend({

      routes: {
        '': 'renderHome',
        // 'category/:category': 'renderCategory',
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
        Db.Tag.all().asCollection(function(tags) {
          self.setView(Page.TagsView, {
            collection: tags,
            title: window.appConfig.zone,
          });
        });
      },

      // renderCategory: function(category) {
      //   var self = this;
      //   async.parallel({
      //     subcategories: function(cb) {
      //       Db.SubCategory.all().filter('category', '=', category).asCollection(function(subcats) {
      //         cb(null, subcats);
      //       });
      //     },
      //     category: function(cb) {
      //       Db.Category.findBy('id', category, function(cat) {
      //         cb(null, cat);
      //       });
      //     }
      //   }, function(err, results) {
      //     self.setView(Page.categoryView, {
      //       collection: results.subcategories,
      //       title: results.category.name
      //     });
      //   });
      // },

      renderPois: function(query) {
        var self = this
        , parsedQuery = query ? Query.parseQuery(query) : {}
        ;

        async.parallel({
          pois: function(cb) {
            // TODO: esto tiene que ser más general
            Db.Tag.filter('id', '=', parsedQuery.tag).list(function(tags) {
              tags[0].pois.all().asJSON(function(pois) {
                cb(null, pois);
              });
            });
          },
          title: function(cb) {
            // Busca el título adecuado para la página
            if (parsedQuery.q) {
              cb(null, res.searchResults);
            } else if (parsedQuery.starred) {
              cb(null, res.Starred);
            } else if (parsedQuery.tag) {
              Db.Tags.findBy('id', parsedQuery.tag, function(subcat) {
                cb(null, subcat.name);
              });
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
            model: new Poi.Model(poi.toJSON()),
            title: poi.name
          });
        });
      }
    });
  }
);