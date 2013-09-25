define(['tpl!search/formview.tpl', 'ui/topbarview', 'search/search',
  'tpl!flag/groupcollapseview.tpl', 'tpl!flag/formview.tpl'],
  function(FormTpl, TopbarView, Search, FlagGroupTmpl, FlagTmpl) {

  return B.View.extend({

    className: 'pageview searchview',

    events: {
      'submit .search-form': function(e) {
        e.preventDefault();
        this.doSearch($(e.currentTarget).find('.search-input').val());
      },
      'tap .clear-all': function() {
        this.$('.flag-groups [type="checkbox"]:checked').each(function(i, checkbox) {
          $(checkbox).prop('checked', false);
        });
        this.$('.in').each(function(i, el) {
          $(el).collapse('hide');
        });
        this.$('[data-toggle="collapse"]').addClass('collapsed');
      }
    },

    initialize: function() {
      var options = this.options
      ;

      this.topbarView = new TopbarView({
        title: res.name
      });

      this.pass(this.topbarView, 'navigate');

      options.searchText = options.searchText || '';

      // Convierte los arrays de IDs en hashes ID/checked-status para reconstruir el estado del formulario

      options.categories = _.reduce(res.searchCategories, function(memo, value, id) {
        memo[id] = _.contains(options.categories, id) ?
                  'checked' : '';
        return memo;
      }, {});

      // En el caso de las opciones de geoloc., el número de opciones depende de si se llegó a
      // #/search a través de un POI. __NEAR_ME es la opción básica "buscar cerca de mí".
      var geoOptions = options.nearPoi ? [ '__NEAR_ME', options.nearPoi.id] : ['__NEAR_ME'];

      options.geo = _.reduce(geoOptions, function(memo, value, i) {
        memo[value] = options.geo == value ? 'checked' : '';
        return memo;
      }, {});

      options.openFlags = (options.flags && options.flags.length) ? 'in' : '';
      options.collapsedFlags = (options.flags && options.flags.length) ? '' : 'collapsed';

      options.flagsView = _.map(res.flagGroups, function(group) {
        var isOpen = false;

        var flags = _.map(res.flags, function(flag) {
          if (flag.group !== group.id) return '';

          var isChecked = _.contains(options.flags, flag.id);

          flag.checked = isChecked ? 'checked' : '';

          if (isChecked) isOpen = true;

          return FlagTmpl(flag);
        }).join('');
        group = _.clone(group);
        group.flags = flags;
        group.open = isOpen;

        return FlagGroupTmpl(group);

      }).join(''); 
    },

    render: function() {
      this.$el.html(this.topbarView.render().$el);

      this.$el.append(FormTpl(this.options));

      var $flagGroupsEl = this.$('.flag-groups');

      _.each(res.flagGroups, function(group) {
        var flags = $()
        , isOpen = false
        ;

        _(res.flags).chain().filter(function(flag) {
          return flag.group === group.id;
        })
        .each(function(flag) {
          var isChecked = _.contains(this.options.flags, flag.id)
          , $flag
          ;
          if (isChecked) isOpen = true;

          flag = _.extend({
            checked: isChecked ? 'checked' : ''
          }, flag);
          flags = flags.add($(FlagTmpl(flag)));
        }, this);

        var $groupEl = $(FlagGroupTmpl(_.extend({
          open: isOpen
        }, group)));
        $groupEl.find('.flags-container').append(flags);

        $flagGroupsEl.append($groupEl);
      }, this);

      return this;
    },

    doSearch: function(searchText) {
      var checkedCategories = _.map(this.$('[name="category"]:checked'), function(input) {
        return $(input).val();
      })
      , checkedFlags = _.map(this.$('[name="flag"]:checked'), function(input) {
        return $(input).val();
      })
      , checkedGeo = _.map(this.$('[name="geo"]:checked'), function(input) {
        return $(input).val();
      })
      ;

      this.trigger('updatequery', {
        searchText: searchText,
        categories: checkedCategories,
        flags: checkedFlags,
        geo: checkedGeo[0]
      });

      this.topbarView.block();

      var self = this;

      Search.searchConditions({
        text: searchText,
        categories: checkedCategories,
        flags: checkedFlags,
        geo: checkedGeo.length && (checkedGeo[0] !== '__NEAR_ME' ? checkedGeo[0] : Search.NEAR_ME)
      }, function(err, queryConditions) {
        if (err) {
          // TO-DO: error handling cuando no es un error de geolocalización
          self.topbarView.unblock();
          navigator.notification.alert(res.i18n.geoError);
          return;
        } 
        var uri = encodeURIComponent(JSON.stringify({
          queryConditions: queryConditions
        }));

        self.topbarView.unblock();

        self.trigger('navigate', '/pois?' + uri, 1);
      });
    }
  });
});