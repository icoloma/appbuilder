define(
  [ 
    'page/pages',
    'poi/articleview',
    'schemas/schemas'
  ],
  function(Page, PoiArticleView, Db) {

    var parseQuery = function(query) {
      var queryObject = {}
      , pattern = /(.+)=(.+)/
      ;
      query.split('\&').forEach(function(param) {
        var parts = param.match(pattern);
        queryObject[parts[1]] = parts[2];
      });
      return queryObject;
    }
    ;

    return B.Router.extend({

      routes: {
        '': 'renderHome',
        'category/:category': 'renderCategory',
        'pois?:query': 'renderPois',
        'pois/:poiId': 'renderPoi'
      },

      initialize: function(options) {
        this.$el = options.$el;

        var self = this;
      },

      setView: function(view, options) {
        this.currentView = new view(options);
        this.$el.html(this.currentView.render().$el);
        return this.currentView;
      },

      renderHome: function() {
        var self = this;
        Db.Category.all().list(function(cats) {
          self.setView(Page.homeView, {
            collection: cats,
            name: window.appConfig.name
          });
        });
      },

      renderCategory: function(category) {
        var self = this;
        async.parallel([
          function(cb) {
            Db.SubCategory.all().filter('category', '=', category).list(function(subcats) {
              cb(null, subcats);
            });
          },
          function(cb) {
            Db.Category.findBy('id', category, function(cat) {
              cb(null, cat);
            })
          }
        ], function(err, results) {
          self.setView(Page.categoryView, {
            name: results[1].name,
            collection: results[0]
          })
        });
      },

      renderPois: function(query) {
        var self = this
        , parsedQuery = parseQuery(query)
        ;

        async.parallel([
          function(cb) {
            Db.Poi.all().query(parsedQuery).list(function(pois) {
              cb(null, pois);
            });
          },
          function(cb) {
            Db.SubCategory.findBy('id', parsedQuery['subcategory'], function(subcat) {
              cb(null, subcat);
            })
          }
        ], function(err, results) {
          self.setView(Page.poisView, {
            name: results[1].name,
            collection: results[0]
          })
        });
      },

      renderPoi: function(poiId) {
        var self = this;
        Db.Poi.findBy('id', poiId, function(poi) {
          self.setView(Page.poiView, {
            model: poi
          });
        });
      }
    });
  }
);