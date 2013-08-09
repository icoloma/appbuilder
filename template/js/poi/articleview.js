define(
  ['globals'],
  function() {
    return B.View.extend({

      className: 'articleview',

      events: {
        'tap .star': function() {
          this.trigger('star');
        }
      },

      tmpl: _.template(
        '<div class="titlebar row app-container">' +
          '<div class="details col-10">' + 
            '<div class="name hideable">{{name}}</div>' +
            '<address><a href="{{geoLink}}">{{address}} <span class="icon-map"></span></a></address>' +
          '</div>' +
          '<div class="col-2">' + 
            '<span class="star icon-{{isStarred}}"></span>' +
          '</div>' +
        '</div>' +
        '<div class="poi-imgs swiper-container">' +
          '<div class="swiper-wrapper">' + 
            '<img class="swiper-slide" src="{{appConfig.assets + thumb}}">' +
            '<% for (var i in imgs) { var img = imgs[i]; %>' + 
              '<img class="swiper-slide" src={{appConfig.assets+img}}>' +
            '<% } %>' +
          '</div>' +
        '</div>' +
        '<div class="row app-container">' + 
          '<p class="description col-12">{{desc}}</p>' +
        '</div>'
      ),

      render: function() {
        var json = this.model.toJSON();
        _.extend(json, {
          isStarred: json.starred ? 'star' : 'star-empty',
          geoLink: this.model.geoLink()
        });
        this.$el.html(this.tmpl(json));
        this.swipe();
        return this;
      },

      swipe: function() {
        this.$('.swiper-container').swiper({
          mode: 'horizontal',
          loop: true
        });
      }
    });
  }
);