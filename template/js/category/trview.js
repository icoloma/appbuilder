define(
  ['globals'],
  function() {
    return _.template(
      '<tr ' +
      'data-url="<% if (menu) {  %> #/menu/{{menu}} ' +
                '<% } else {     %> #/pois?title={{label}}&{{query}} <% } %>" ' +
      'data-title="{{label}}">' +
        '<td>' +
          '{{label}}' +
        '</td>' +
        '<% if(poiCount) { %> <td class="poiCount">{{poiCount}}</td> <% } %>' +
      '</tr>'
    );
  }
);