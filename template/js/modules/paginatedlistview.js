define(['modules/baselistview'],
function(BaseListView) {
  return BaseListView.extend({

    events: _.extend(BaseListView.prototype.events, {
      'tap .showmore': function() {
        this.cursor += Math.floor(this.pageCount/2);
        this.cursor = Math.min(this.cursor, this.collection.length);
        this.trigger('updatequery', {cursor: this.cursor});
        this.showMore();
      }
    }),

    initialize: function(options) {
      BaseListView.prototype.initialize.call(this, options);
      this.pageCount = Math.ceil(window.innerHeight / 80);
      this.cursor = this.options.cursor || this.pageCount;
    },

    button: function() {
      return '<div class="row showmore"><div class="col-xs-12">' +
            '<span class="icon-show-more"></span> ' + window.res.ShowMore +
            '</div></div>';
    },

    render: function() {
      this.$el.html('');
      _.each(this.collection.toJSON(), function(item, i) {
        if (i < this.cursor) {
          this.$el.append(this.trView(item));
        }
      }, this);
      if (this.cursor < this.collection.length) {
        this.$el.append(this.button());
      }
      return this;
    },
    
    showMore: function() {
      var currentCount = this.$('.item-row').length
      , button = this.$('.showmore').remove()
      ;
      _.each(this.collection.toJSON(), function(item, i) {
        if (i < this.cursor && i >= currentCount) {
          this.$el.append(this.trView(item));
        }
      }, this);
      if (this.cursor < this.collection.length) {
        this.$el.append(this.button());
      }
    }    
  });
});