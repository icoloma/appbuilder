define(
  ['globals'],
  function() {
    return _.template(
      '<tr data-url="#/pois/{{id}}" class="row">' +
        '<td class="col-9"><div class="entry-name hideable">{{name}}</div>' +
          '<div class="entry-description small hideable">{{address}}</div> </td>' +
        '<td class="col-3 td-poi-thumb" style="background-image: url({{appConfig.assets+thumb}})">' +
          // '<img class="poi-thumb" src="{{appConfig.assets+thumb}}">' +
        '</td>' +
      '</tr>'
    );
  }
);