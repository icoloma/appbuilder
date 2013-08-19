define(
  ['modules/baselistview', 'tpl!category/trview.tpl', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'pageview homeview',

      poiTmpl: _.template(
        '<a class="homepois" href={{url}} style="background-image: url({{src}})"></a>'
      ),

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
          root: true
        });

        this.pass(this.topbarView, 'historyback');
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        // TO-DO: añadir una vista para esto
        this.$el.append(this.poiTmpl({
          src: appConfig.assets + this.options.pois[0].thumb,
          url: '#/pois/' + this.options.pois[0].id
        }));
        this.$el.append(this.collectionView.render().$el);
        return this;
      }
    });
  }
);