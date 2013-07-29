define(
  ['page/poiview', 'page/menuview', 'page/poisview', 'page/homeview'],
  function(PoiView, MenuView, PoisView, HomeView) {
    return {
      PoiView: PoiView,
      MenuView: MenuView,
      HomeView: HomeView,
      PoisView: PoisView
    };
  }
);