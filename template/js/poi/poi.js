define(['poi/model', 'poi/collection', 'poi/articleview', 'tpl!poi/trview.tpl'],
  function(Model, Collection, ArticleView, TrView) {
    return {
      Model: Model,
      Collection: Collection,
      ArticleView: ArticleView,
      TrView: TrView 
    };
  }
);