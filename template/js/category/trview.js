define(
  ['modules/basetrview'],
  function(BaseTrView) {
    return BaseTrView.extend({
      url: function() {
        return this.model.get('menu') ? 
          '#/menu/' + this.model.get('menu') : this.model.get('query');
      },

      tmpl: _.template(
        '<td>{{label}} <span>{{desc}}</span></td>' +
        '<td>{{poiCount}}</td>'
      )
    });
  }
);