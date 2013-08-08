define(
  ['globals'],
  function() {
    return _.template(
      '<tr ' +
      'data-url="<% if (menu) {  %> #/menu/{{menu}} ' +
                '<% } else {     %> #/pois?{{encodeURIComponent(JSON.stringify(data))}} <% } %>" ' +
      'data-title="{{label}}">' +
        '<td>' +
          '{{label}}' +
        '</td>' +
        '<% if(poiCount) { %> <td class="poiCount">{{poiCount}}</td> <% } %>' +
      '</tr>'
    );
  }
);