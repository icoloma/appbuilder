/*
  El router central del app
*/
define(
  [ 
    'page/pages',
    'schemas/schemas',
    'ui/navbarview'
  ],
  function(Page, Db, NavbarView) {

    var parseQuery = function(query) {
      var queryObject = {}
      , pattern = /(.+)=(.+)/
      ;
      query.split('&').forEach(function(param) {
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
        this.$page = this.$el.find('.page');
        this.navbarView = new NavbarView({
          el: this.$el.find('.navbar').first(),
          root: true,
          title: window.appConfig.zone
        }).render();
      },

      setView: function(view, options, navbarOpts) {
        this.currentView = new view(options);
        this.navbarView.options = navbarOpts;
        this.navbarView.render();
        this.$page.html(this.currentView.render().$el);
        return this.currentView;
      },

      renderHome: function() {
        var self = this;
        Db.Category.all().list(function(cats) {
          self.setView(Page.homeView, {
            collection: cats,
          }, {
            title: window.appConfig.zone,
            root: true
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
            });
          }
        ], function(err, results) {
          self.setView(Page.categoryView, {
            collection: results[0]
          }, {
            title: results[1].name,
          });
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
            // Busca el título adecuado para la página
            if (parsedQuery.q) {
              cb(null, res.searchResults);
            } else if (parsedQuery.starred) {
              cb(null, res.Starred);
            } else if (parsedQuery.subcategory) {
              Db.SubCategory.findBy('id', parsedQuery.subcategory, function(subcat) {
                cb(null, subcat.name);
              });
            }
          }
        ], function(err, results) {
          self.setView(Page.poisView, {
            collection: results[0]
          }, {
            title: results[1],
          });
        });
      },

      renderPoi: function(poiId) {
        var self = this;
        Db.Poi.findBy('id', poiId, function(poi) {
          self.setView(Page.poiView, {
            model: poi,
          }, {
            title: poi.name
          });
        });
      }
    });
  }
);