define(
  ['modules/baselistview', 'category/trview', 'ui/actionbarview'],
  function(ListView, TrView, ActionbarView) {

    return B.View.extend({

      initialize: function() {
        this.collectionView = new ListView({
          url: '#/category/',
          collection: this.collection,
          trView: TrView
        });
      },

      starTmpl: _.template(
        '<a href="#/pois?starred=true"><span class="starred"></span> {{res.Starred}}</a>'
      ),

      render: function() {
        this.$el.html(this.starTmpl());
        this.$el.append(this.collectionView.render().$el);
        return this;
      }
    });
  }
);