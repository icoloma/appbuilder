define(
  ['globals'],
  function() {
    return B.View.extend({

      className: 'articleview',

      events: {
        'tap .star': function() {
          this.trigger('star');
        },
        'tap .name': function() {
          this.$('.name').toggleClass('uncovered');
        }
      },

      tmpl: _.template(
        '<div class="titlebar row app-container">' +
          '<div class="details col-10">' + 
            '<div class="name hideable">{{name}}</div>' +
            '<address class="small">' +
              '<a href="{{geoLink}}"> <span class="icon-map"></span> {{address}}</a>' +
            '</address>' +
          '</div>' +
          '<div class="col-2">' + 
            '<span class="star icon-{{isStarred}}"></span>' +
          '</div>' +
        '</div>' +
        '<div class="poi-imgs swiper-container">' +
          '<div class="swiper-wrapper">' + 
            '<div class="swiper-slide" style="background-image: url({{appConfig.assets+thumb}})"></div>' +
            '<% for (var i in imgs) { var img = imgs[i]; %>' + 
              '<div class="swiper-slide" style="background-image: url({{appConfig.assets+img}})"></div>' +
            '<% } %>' +
          '</div>' +
        '</div>' +
        '<div class="row app-container">' + 
          '<p class="small description col-12">{{desc}}</p>' +
        '</div>'
      ),

      render: function() {
        var json = this.model.toJSON();
        _.extend(json, {
          isStarred: json.starred ? 'star' : 'star-empty',
          geoLink: this.model.geoLink()
        });
        this.$el.html(this.tmpl(json));
        _.delay(function() {
          this.$('.swiper-container').swiper({
            mode: 'horizontal',
            loop: true
          });
        }, 250);
        return this;
      },
    });
  }
);