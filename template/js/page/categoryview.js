define(
  ['modules/baselistview', 'category/trview', 'ui/navbarview'],
  function(ListView, TrView, NavbarView) {

    return B.View.extend({

      render: function() {
        this.navbarView = new NavbarView({
          title: this.options.name
        });
        this.collectionView = new ListView({
          collection: this.collection,
          url: '#/pois?subcategory=',
          trView: TrView
        });

        this.$el.html(this.navbarView.render().$el);
        this.$el.append(this.collectionView.render().$el);
        return this;
      }

    });
  }
);