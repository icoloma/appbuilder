define(
  ['modules/baselistview', 'poi/trview'],
  function(ListView, TrView) {

    return B.View.extend({

      render: function() {
        var list = new ListView({
          collection: this.collection,
          url: '#/pois/',
          trView: TrView
        });
        this.$el.html(list.render().$el);
        return this;
      }

    });
  }
);