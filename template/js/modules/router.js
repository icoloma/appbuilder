define(
  [
    'category/homeview', 'category/subcatsview', 'poi/collectionview', 'poi/view',
    'db/entities'
  ],
  function(HomeView, SubCatsView, PoisView, PoiView, Db) {
    return B.Router.extend({

      routes: {
        '': 'renderHome',
        'tree/:category': 'renderCategory',
        'tree/:category/:subcategory': 'renderSubCategory',
        'pois/:poiId': 'renderPoi'
      },

      initialize: function(options) {
        this.$el = options.$el;
      },

      setView: function(view, options) {
        var v = new view(options);
        this.$el.html(v.render().$el);
        return v;
      },

      renderHome: function() {
        var self = this;
        Db.Category.all().list(function(cats) {
          self.setView(HomeView, {
            collection: cats
          });
        });
      },

      renderCategory: function(category) {
        var self = this;
        Db.SubCategory.all().filter('category', '=', category).list(function(subcats) {
          self.setView(SubCatsView, {
            url: '#/tree/' + category + '/',
            collection: subcats
          });
        });
      },

      renderSubCategory: function(category, subcategory) {
        var self = this;
        Db.Poi.all().filter('subcategory', '=', subcategory).list(function(pois) {
          self.setView(PoisView, {
            url: '#/pois/',
            collection: pois
          });
        });
      },

      renderPoi: function(poiId) {
        var self = this;
        Db.Poi.findBy('id', poiId, function(poi) {
          self.setView(PoiView, {
            model: poi
          });
        });
      }
    });
  }
);