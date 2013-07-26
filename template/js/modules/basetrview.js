define(['globals'],
  function() {
    return B.View.extend({
      el: function() {
        return '<tr data-item="' + this.url() + '"></tr>';
      },

      render: function() {
        this.$el.html(this.tmpl(this.model.toJSON()));
        return this;
      },
    });
  }
);