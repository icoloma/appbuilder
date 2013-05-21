define(
  [],
  function() {
    return B.View.extend({
      tmpl: _.template(
        '<div>' + 
          '<img src="{{appConfig.assets+thumb}}">' + 
          '<p>{{description}}</p>' +
          '<p>Located in {{lat}}, {{lon}}.</p>' +
        '</div>'      
      ),

      render: function() {
        this.$el.html(this.tmpl(this.model));
        return this;
      }
    });
  }
);