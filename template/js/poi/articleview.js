define(
  ['globals', 'tpl!poi/articleview.tpl'],
  function(Globals, Tmpl, Metadata) {
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

      render: function() {
        var json = this.model.toJSON();
        _.extend(json, {
          isStarred: json.starred ? 'star' : 'star-empty',
          geoLink: this.model.geoLink(),
          flags: json.flags.map(function(flagID) {
            return res._metadata.flags[flagID];
          })
        });
        this.$el.html(Tmpl(json));
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