define(
  ['page/homeview', 'page/poiview', 'page/categoryview', 'page/poisview'],
  function(HomeView, PoiView, CategoryView, PoisView) {
    return {
      homeView: HomeView,
      poiView: PoiView,
      categoryView: CategoryView,
      poisView: PoisView
    }
  }
);