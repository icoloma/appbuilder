define(['tpl!search/formview.tpl', 'ui/topbarview', 'search/search'],
  function(FormTpl, TopbarView, Search) {

  return B.View.extend({

    className: 'pageview searchview',

    events: {
      'submit .search-form': function(e) {
        e.preventDefault();
        this.doSearch($(e.currentTarget).find('.search-input').val());
      },
      'tap .checkable': function(e) {
        var $input = $(e.currentTarget).find('input');
        $input.prop('checked', !$input.prop('checked'));
      }
    },

    initialize: function() {
      var options = this.options
      ;


      this.topbarView = new TopbarView({
        // TO-DO: añadir appName
        title: res.appName,
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

      options.flags = _.reduce(res.flags, function(memo, value, id) {
        memo[id] = _.contains(options.flags, id) ?
                  'checked' : '';
        return memo;
      }, {});

    },

    render: function() {
      this.$el.html(this.topbarView.render().$el);

      this.$el.append(FormTpl(this.options));
      return this;
    },

    doSearch: function(searchText) {
      var checkedCategories = _.map(this.$('[name="category"]:checked'), function(input) {
        return $(input).val();
      })
      , checkedGeo = _.map(this.$('[name="geo"]:checked'), function(input) {
        return $(input).val();
      })
      ;

      this.trigger('updatequery', {
        searchText: searchText,
        categories: checkedCategories,
        geo: checkedGeo[0]
      });

      this.topbarView.block();

      var self = this;

      Search.searchConditions({
        text: searchText,
        categories: checkedCategories,
        geo: checkedGeo.length && checkedGeo[0] !== '__NEAR_ME' ? checkedGeo[0] : Search.NEAR_ME
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