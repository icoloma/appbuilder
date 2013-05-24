define(
  ['page/homeview', 'page/poiview', 'page/categoryview', 'page/subcategoryview'],
  function(HomeView, PoiView, CategoryView, SubcategoryView) {
    return {
      homeView: HomeView,
      poiView: PoiView,
      categoryView: CategoryView,
      subcategoryView: SubcategoryView
    }
  }
);