define(
  ['globals'],
  function() {
    return _.template(
      '<tr class="row"' +
      'data-url="<% if (menu) {  %> #/menu/{{menu}} ' +
                '<% } else {     %> #/pois?{{encodeURIComponent(JSON.stringify(data))}} <% } %>" ' +
      'data-title="{{label}}">' +
        '<td class="col-{{poiCount ? 9 : 12}}">' +
          '{{label}}' +
        '</td>' +
        '<% if(poiCount) { %> <td class="poiCount col-3">{{poiCount}}</td> <% } %>' +
      '</tr>'
    );
  }
);