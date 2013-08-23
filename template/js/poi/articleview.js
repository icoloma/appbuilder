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
        },
        'tap .flag-group-line': function(e) {
          $(e.currentTarget).parent().find('.flags-container').toggle();
        }
      },

      render: function() {
        var json = this.model.toJSON()
        , flagGroups = {}
        ;

        _.each(json.flags, function(flag) {
          var flagObj = res._metadata.flags[flag];
          if (!flagGroups[flagObj.group]) {
            var newGroupId = flagObj.group;
            flagGroups[newGroupId] = _.clone(res._metadata.flagGroups[newGroupId]);
            flagGroups[newGroupId].flags = [];
            flagGroups[newGroupId].icon = res._metadata.flag_icons[newGroupId];
          }
          flagGroups[flagObj.group].flags.push(flagObj);
        });

        _.extend(json, {
          isStarred: json.starred ? 'star' : 'star-empty',
          geoLink: this.model.geoLink(),
          flagGroups: flagGroups
        });

        this.$el.html(Tmpl(json));
        return this;
      },

      imgTmpl: function(img) {
        return '<div class="swiper-slide" style="background-image: url(' +
                appConfig.assets+img + ')"></div>';
      },

      swipe: function() {
        var imgs = this.model.get('imgs');
        this.$('.swiper-wrapper').append(
          $(imgs.map(this.imgTmpl).join(''))
        );
        this.$('.swiper-container').swiper({
          mode: 'horizontal',
          loop: true
        });
      }
    });
  }
);