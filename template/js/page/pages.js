define(
  ['page/poiview', 'page/menuview', 'page/poisview', 'page/homeview',
    'page/searchview', 'page/favoritesview', 'page/traveldetailsview', 'page/travelplanview'],
  function(PoiView, MenuView, PoisView, HomeView, SearchView, FavoritesView,
            TravelDetailsView, TravelPlanView) {
    return {
      PoiView: PoiView,
      MenuView: MenuView,
      HomeView: HomeView,
      PoisView: PoisView,
      SearchView: SearchView,
      FavoritesView: FavoritesView,
      TravelDetailsView: TravelDetailsView,
      TravelPlanView: TravelPlanView,
    };
  }
);