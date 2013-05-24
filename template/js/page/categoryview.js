define(
  ['modules/baselistview', 'category/trview'],
  function(ListView, TrView) {

    return B.View.extend({

      render: function() {
        var list = new ListView({
          collection: this.collection,
          url: '#/tree/' + this.options.category + '/',
          trView: TrView
        });
        this.$el.html(list.render().$el);
        return this;
      }

    });
  }
);