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

      // Convierte los arrays de IDs en hashes ID/checked-status
      options.categories = _.reduce(res.searchCategories, function(memo, value, id) {
        memo[id] = _.contains(options.categories, id) ?
                  'checked' : '';
        return memo;
      }, {});

      var nearOptions = options.nearPoi ? [ '_near_me_', options.nearPoi.id] : ['_near_me_'];
      options.near = _.reduce(nearOptions, function(memo, value, i) {
        memo[value] = options.near == value ? 'checked' : '';
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
      , checkedNearness = _.map(this.$('[name="near"]:checked'), function(input) {
        return $(input).val();
      })
      , uriObj = {
        queryConditions: Search.doSearch({
          text: searchText,
          categories: checkedCategories,
          near: checkedNearness[0]
        })
      }
      , uri = encodeURIComponent(JSON.stringify(uriObj))
      ;

      this.trigger('updatequery', {
        searchText: searchText,
        categories: checkedCategories,
        near: checkedNearness[0]
      });

      this.trigger('navigate', '/pois?' + uri, 1);
    }
  });
});