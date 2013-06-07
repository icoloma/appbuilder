define(
  ['globals'],
  function() {
    return B.View.extend({

      className: 'articleview',

      tmpl: _.template(
        '<img class="poi-img" src="{{appConfig.assets+thumb}}">' + 
        '<p class="description">{{description}}</p>' +
        '<p>Located in {{lat}}, {{lon}}.</p>'
      ),

      render: function() {
        this.$el.html(this.tmpl(this.model.toJSON()));
        return this;
      }
    });
  }
);