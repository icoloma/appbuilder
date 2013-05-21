define(
  ['modules/basetrview'],
  function(BaseTrView) {
    return BaseTrView.extend({
      tmpl: _.template(
        '<td>{{name}}</td>' +
        '<td><img src="{{thumb}}"></td>' +
        '<td><span class="go">Go</span></td>'
      )
    });
  }
);