define(
  function() {
    return B.View.extend({
      el: function() {
        return '<tr data-id="' + this.model.id + '"></tr>';
      },

      render: function() {
        this.$el.html(this.tmpl(this.model));
        return this;
      },
    });
  }
);