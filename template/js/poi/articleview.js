define(
  ['globals', 'tpl!poi/articleview.tpl', 'tpl!flag/groupcollapseview.tpl'],
  function(Globals, Tmpl, FlagGroupTmpl) {
    return B.View.extend({

      className: 'articleview',

      events: {
        'tap .star': function() {
          this.trigger('star');
        },
        'tap .name': function() {
          this.$('.poi-name').toggleClass('uncovered');
        },
      },

      FlagTmpl: _.template('<li class="flag-name">{{tpl.name}}</li>'),

      render: function() {
        var json = this.model.toJSON()
        , data = {}
        ;


        /*
          Pasamos los data como un hash dataId/label-value
          AVISO: Se ignoran los data que no estén presentes en los metadatos
        */
        _.each(json.data, function(value, dataId) {
          if (res.data[dataId]) {
            data[dataId] = _.clone(res.data[dataId]);
            data[dataId].value = value;
          }
        });

        _.extend(json, {
          isStarred: json.starred ? 'star' : 'star-empty',
          geoLink: this.model.geoLink(),
          data: data
        });

        this.$el.html(Tmpl(json));

        /*
          Añade la vista de flags
        */
        var $flagsGroupsEl = this.$('.poi-flags');

        _(json.flags).chain()
          // Construye un hash de grupos de flags, incluyendo un campo con
          // las flags de este POI en cada grupo
          .reduce(function(memo, flagId) {
            var flag = res.flags[flagId];

            // Se ignoran las flags desconocidas 
            if (!flag) return;

            if (!memo[flag.group]) {
              // AVISO: asume que el grupo flag.group existe
              memo[flag.group] = _.clone(res.flagGroups[flag.group]);
              memo[flag.group].flags = [];
            }
            memo[flag.group].flags.push(flag);
            return memo;
          }, {}, this)
          // Añade las vistas de flag groups
          .each(function(group) {
            var flags = $();

            _.each(group.flags, function(flag) {
              flags = flags.add($(this.FlagTmpl(flag)));
            }, this);

            var $groupEl = $(FlagGroupTmpl(_.extend({
              open: false
            }, group)));
            $groupEl.find('.flags-container').append(flags);
            $flagsGroupsEl.append($groupEl);
          }, this);

        return this;
      },

      imgTmpl: function(img) {
        return '<div class="swiper-slide" style="background-image: url(' +
                res.resources+img + ')"></div>';
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