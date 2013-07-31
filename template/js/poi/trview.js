define(
  ['modules/basetrview'],
  function(BaseTrView) {
    return BaseTrView.extend({
      url: function() {
       return '#/pois/' + this.model.get('id');
      },
      tmpl: _.template(
        '<td><div class="entry-name">{{name}}</div>' +
          '<div class="entry-description">{{address}}</div> </td>' +
        '<td><img class="poi-thumb" src="{{appConfig.assets+thumb}}"></td>' 
        // +
        // '<td><span class="go">➤</span></td>'
      )
    });
  }
);