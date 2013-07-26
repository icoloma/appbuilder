define(
  ['page/poiview', 'page/menuview', 'page/poisview'],
  function(PoiView, MenuView, PoisView) {
    return {
      PoiView: PoiView,
      MenuView: MenuView,
      PoisView: PoisView
    };
  }
);