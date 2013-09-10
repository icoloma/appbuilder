define(
  ['modules/baselistview', 'tpl!menu/trview.tpl', 'ui/topbarview'],
  function(ListView, TrView, TopbarView) {

    return B.View.extend({
      className: 'pageview homeview',

      events: {
        'tap .homepois': function(e) {
          this.trigger('navigate', $(e.target).data('url'), 1);
        }
      },

      poiTmpl: _.template(
        '<div class="homepois" data-url={{url}} style="background-image: url({{src}})"></div>'
      ),

      initialize: function() {
        this.collectionView = new ListView({
          collection: this.collection,
          trView: TrView
        });
        this.topbarView = new TopbarView({
          title: this.options.title,
          actions: ['search'],
          root: true
        });

        this.pass(this.topbarView, 'navigate');
        this.pass(this.collectionView, 'navigate');
        this.listenTo(this.topbarView, 'search', function() {
          this.trigger('navigate', '/search?', 1);
        });
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