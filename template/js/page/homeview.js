define(
  ['modules/baselistview', 'category/trview', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'pageview homeview',

      poiTmpl: _.template(
        '<a class="homepois" href={{url}} style="background-image: url({{src}})"></a>'
      ),

      initialize: function() {
        this.collectionView = new ListView({
          collection: this.collection,
          trView: TrView
        });
        this.topbarView = new TopbarView({
          title: this.options.title,
          search: true,
          root: true
        });
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        // TODO: añadir una vista para esto
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