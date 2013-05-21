define(
  ['modules/basetrview'],
  function(BaseTrView) {
    return BaseTrView.extend({
      tmpl: _.template(
        '<td>{{name}}</td>' +
        '<td class="go">Go</td>'
      )
    });
  }
);