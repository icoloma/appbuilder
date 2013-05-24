define(
  [],
  function() {
    return B.View.extend({

      className: 'articleview',

      tmpl: _.template(
        '<img src="{{appConfig.assets+thumb}}">' + 
        '<p>{{description}}</p>' +
        '<p>Located in {{lat}}, {{lon}}.</p>'
      ),

      render: function() {
        this.$el.html(this.tmpl(this.model));
        return this;
      }
    });
  }
);