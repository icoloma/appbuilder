define(
  ['globals', 'tpl!poi/articleview.tpl'],
  function(Globals, Tmpl) {
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

      render: function() {
        var json = this.model.toJSON()
        , flagGroups = {}
        , data = {}
        ;

        /*
          Para mostrar las flags, pasamos un hash flagGroups que contiene los flag groups relevantes
          para este POI sacados de res.flagGroup, y los extendemos:
            flagGroupID: {
              flags: (las flags del POI dentro de este group, copiadas de res.flags)
              (resto de campos de un flag group)
            }
          AVISO: se ignoran los flags desconocidos.
        */
        _.each(json.flags, function(flag) {
          var flagObj = res.flags[flag];
          if (!flagObj) return;

          if (!flagGroups[flagObj.group]) {
            var newGroupId = flagObj.group;

            // AVISO: asume que el flagGroup correspondiente existe
            flagGroups[newGroupId] = _.clone(res.flagGroups[newGroupId]);
            flagGroups[newGroupId].flags = [];
          }

          flagGroups[flagObj.group].flags.push(flagObj);
        });

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
          flagGroups: flagGroups,
          data: data
        });

        this.$el.html(Tmpl(json));
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