define(
  ['globals'],
  function() {
    return B.View.extend({

      className: 'articleview',

      tmpl: _.template(
        // '<img class="poi-img" src="{{appConfig.assets+thumb}}">' + 
        // '<p class="description">{{desc}}</p>' +
        // '<p>Located in {{lat}}, {{lon}}.</p>'
        '<div class="titlebar container">' +
          '<div class="details">' + 
            '<div>{{name}}</div>' +
            '<address>{{address}} <span class="icon-map"></span></address>' +
          '</div>' +
          '<span class="icon-star star"></span>' +
        '</div>' +
        '<div class="poi-imgs">' +
          '<img src="{{appConfig.assets + thumb}}"></img>' +
        '</div>' +
        '<p class="description container">{{desc}}</p>'
      ),

      render: function() {
        this.$el.html(this.tmpl(this.model.toJSON()));
        return this;
      }
    });
  }
);