define(
  ['page/poiview', 'page/menuview', 'page/poisview', 'page/homeview', 'page/searchview', 'page/favoritesview'],
  function(PoiView, MenuView, PoisView, HomeView, SearchView, FavoritesView) {
    return {
      PoiView: PoiView,
      MenuView: MenuView,
      HomeView: HomeView,
      PoisView: PoisView,
      SearchView: SearchView,
      FavoritesView: FavoritesView
    };
  }
);