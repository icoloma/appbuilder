define(
  ['entity/model'],
  function(Model) {
    return B.View.extend({
      el: function() {
        return '<tr data-id="' + this.model.get('id') + '"></tr>';
      },
      
      tmpl: _.template(
        '<td>{{name}}</td>' +
        '<td><img src="{{thumb}}"></td>' +
        '<td><span class="go">Go</span></td>'
      ),

      render: function() {
        var json = this.model.toJSON();
        this.$el.html(this.tmpl(json));
        return this;
      }
    });
  }
);