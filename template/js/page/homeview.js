define(
  ['modules/baselistview', 'category/trview', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'homeview',

      initialize: function() {
        this.collectionView = new ListView({
          collection: this.collection,
          trView: TrView
        });
        this.topbarView = new TopbarView({
          title: this.options.title,
          root: true
        });
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        // TODO: añadir una vista para esto
        this.$el.append(
          _.template('<a class="homepois" href="{{href}}"><img src="{{src}}"></img></a>', {
            src: appConfig.assets + this.options.pois[0].thumb,
            href: '#/pois/' + this.options.pois[0].id
          })
        );
        this.$el.append(this.collectionView.render().$el);
        return this;
      }
    });
  }
);