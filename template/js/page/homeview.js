define(
  ['modules/baselistview', 'category/trview'],
  function(ListView, TrView) {

    return B.View.extend({

      render: function() {
        var list = new ListView({
          url: '#/tree/',
          collection: this.collection,
          trView: TrView
        });
        this.$el.html(list.render().$el);
        return this;
      }
    });
  }
);