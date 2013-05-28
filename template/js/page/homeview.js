define(
  ['modules/baselistview', 'category/trview', 'ui/navbarview'],
  function(ListView, TrView, NavbarView) {

    return B.View.extend({

      render: function() {
        this.navbarview = new NavbarView({
          title: this.options.name,
          root: true
        });
        this.collectionview = new ListView({
          url: '#/category/',
          collection: this.collection,
          trView: TrView
        });
        this.$el.html(this.navbarview.render().$el);
        this.$el.append(this.collectionview.render().$el);
        return this;
      }
    });
  }
);