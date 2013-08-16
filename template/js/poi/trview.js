define(
  ['globals'],
  function() {
    return _.template(
      '<div data-url="#/pois/{{id}}" class="row">' +
        '<div class="col-9"><div class="poi-tr-name hideable">{{name}}</div>' +
          '<div class="poi-tr-description small hideable">{{address}}</div> </div>' +
        '<div class="col-3 poi-tr-thumb" style="background-image: url({{appConfig.assets+thumb}})">' +
          // '<img class="poi-thumb" src="{{appConfig.assets+thumb}}">' +
        '</div>' +
      '</div>'
    );
  }
);