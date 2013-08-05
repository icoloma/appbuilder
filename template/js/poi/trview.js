define(
  ['globals'],
  function() {
    return _.template(
      '<tr data-url="#/pois/{{id}}">' +
        '<td><div class="entry-name">{{name}}</div>' +
          '<div class="entry-description">{{address}}</div> </td>' +
        '<td><img class="poi-thumb" src="{{appConfig.assets+thumb}}"></td>' +
      '</tr>'
    );
  }
);