define(
  ['page/poiview', 'page/menuview', 'page/poisview', 'page/homeview', 'page/searchview'],
  function(PoiView, MenuView, PoisView, HomeView, SearchView) {
    return {
      PoiView: PoiView,
      MenuView: MenuView,
      HomeView: HomeView,
      PoisView: PoisView,
      SearchView: SearchView
    };
  }
);