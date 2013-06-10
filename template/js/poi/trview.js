define(
  ['modules/basetrview'],
  function(BaseTrView) {
    return BaseTrView.extend({
      tmpl: _.template(
        '<td>{{name}}</td>' +
        '<td><img class="poi-thumb" src="{{appConfig.assets+thumb}}"></td>' 
        // +
        // '<td><span class="go">➤</span></td>'
      )
    });
  }
);