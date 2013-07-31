define(
  ['modules/basetrview'],
  function(BaseTrView) {
    return BaseTrView.extend({
      url: function() {
        return this.model.get('menu') ? 
          '#/menu/' + this.model.get('menu') : this.model.get('query');
      },

      tmpl: _.template(
        '<td>' +
          '{{label}}' +
          // '<div class="name">{{label}}</div>' +
          // '<div class="entry-description">{{desc}}</div>' +
        '</td>' +
        '<% if(poiCount) { %> <td class="poiCount">{{poiCount}}</td> <% } %>'
      )
    });
  }
);