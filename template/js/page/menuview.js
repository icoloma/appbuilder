define(
  ['modules/baselistview', 'category/trview', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'pageview menuview',

      initialize: function() {
        var collection = this.collection.map(function(model) {
          var json = model.toJSON();
          if (json.query) {
            json.data = {
              query: json.query,
              title: json.label
            };
          }
          return json;
        });

        this.collectionView = new ListView({
          collection: collection,
          trView: TrView
        });
        this.topbarView = new TopbarView({
          title: this.options.title,
          search: true,
        });
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        this.$el.append(this.collectionView.render().$el);
        return this;
      }
    });
  }
);