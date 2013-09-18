/*
  Configuración general del router central de la aplicación
*/
/*
  El router central del app
*/
define(['globals'], function() {

  return B.Router.extend({

    initialize: function(options) {
      this.$el = options.$el;
      this.direction = 1;
      this.cache = [ {uri: ''} ];

      var self = this;
      $(document).on('backbutton', function(e) {
        if (location.hash) {
          e.preventDefault();
          self.navigateTo(null, -1);
        } else {
          // AVISO: esto NO es cross-platform
          navigator.app.exitApp();
        }
      });
    },

    navigateTo: function(uri, dir) {
      if (dir < 0) this.direction = -1;
      if (dir > 0) {
        _.last(this.cache).scroll = window.pageYOffset;
        this.cache.push({
          uri: uri
        });
        window.location.hash = uri;
      } else {
        this.cache.pop();
        var entry = _.last(this.cache);
        this.newScroll = entry.scroll;
        window.location.hash = entry.uri;
      }
    },

    updateUri: function(uriParams) {
      var oldUri = _.last(this.cache).uri
      , rawQueryMatch = location.hash.match(/(^[^?]+)(\?.+)?/)
      , uriObj = rawQueryMatch[2] ? 
        JSON.parse(decodeURIComponent(rawQueryMatch[2].slice(1))) :
        {}
      ;
      _.extend(uriObj, uriParams);
      var newUri = rawQueryMatch[1].slice(1) + '?' +
        encodeURIComponent(JSON.stringify(uriObj));
      _.last(this.cache).uri = newUri;
    },

    setView: function(view, options) {
      var newView = new view(options).render();

      if (this.currentView) {
        this.stopListening(this.currentView);
        this.$el.loadAnimation({
          $oldView: this.currentView.$el,
          $newView: newView.$el,
          newScroll: this.newScroll,
          direction: this.direction
        }, function() {
          newView.$el.trigger('pageviewready');
        });
        this.direction = 1;
      } else {
        this.$el.html(newView.$el.trigger('pageviewready'));
      }

      this.currentView = newView;
      this.listenTo(this.currentView, 'navigate', this.navigateTo);
      this.listenTo(this.currentView, 'updatequery', this.updateUri);
      return this.currentView;
    },

  });
});