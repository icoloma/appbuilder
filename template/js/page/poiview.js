define(
  ['poi/articleview', 'ui/topbarview'],
  function(ArticleView, TopbarView) {


    return B.View.extend({
      className: 'pageview poiview',

      events: {
        'pageviewready': function() {
          if (this.isNew) {
            this.isNew = false;
            var self = this;
            _.nextFrame(function() {
              self.modelView.swipe();
            });
          }
        }
      },

      initialize: function() {
        this.isNew = true;
        this.topbarView = new TopbarView({
          title: this.options.title,
          actions: ['search']
        });
        this.pass(this.topbarView, 'navigate');

        this.modelView = new ArticleView({
          model: this.model
        });
        this.listenTo(this.modelView, 'star', this.star);
        this.listenTo(this.topbarView, 'search', function() {
          var uri = encodeURIComponent(JSON.stringify({
            nearPoi:{
              id: this.model.get('id'),
              name: this.model.get('name')
            }
          }));
          this.trigger('navigate', '/search?' + uri, 1);
        });
      },

      render: function() {
        this.$el.html(this.topbarView.render().$el);
        this.$el.append(this.modelView.render().$el);
        return this;
      },

      star: function() {
        var self = this
        , starredCount = Number(localStorage.getItem('starredCount'))
        , starring = this.model.get('starred') < 0
        ;
        this.model.set('starred', starring ? starredCount + 1 : -1);
        var message = starring ? res.i18n.bookmarkAdded : res.i18n.bookmarkRemoved;
        this.model.persist(function(err) {
          // TO-DO: error handling
          if (starring) {
            localStorage.setItem('starredCount', starredCount + 1);
          }
          navigator.notification.alert(message, null, res.i18n.Starred);

          self.modelView.render();
        });
      }
    });
  }
);