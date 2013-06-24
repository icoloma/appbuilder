define(
  ['page/poiview', 'page/tagsview', 'page/poisview'],
  function(PoiView, TagsView, PoisView) {
    return {
      // homeView: HomeView,
      PoiView: PoiView,
      TagsView: TagsView,
      PoisView: PoisView
    };
  }
);