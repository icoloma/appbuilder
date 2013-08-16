define(
  ['globals'],
  function() {
    return _.template(
      '<div class="row"' +
      'data-url="<% if (menu) {  %> #/menu/{{menu}} ' +
                '<% } else {     %> #/pois?{{encodeURIComponent(JSON.stringify(data))}} <% } %>" ' +
      '>' +
        '<div class="menu-tr-name col-{{poiCount ? 10 : 12}}">' +
          '{{label}}' +
        '</div>' +
        '<% if(poiCount) { %> <div class="poiCount col-2">{{poiCount}}</div> <% } %>' +
      '</div>'
    );
  }
);