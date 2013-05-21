define(
  ['modules/baselistview', 'category/trview'],
  function(BaseListView, TrView) {
    return BaseListView.extend({

      initialize: function(options) {
        _.extend(options, {
          url: '#/tree/',
          trView: TrView
        });
        BaseListView.prototype.initialize.call(this, options);
      }
    });
  }
);