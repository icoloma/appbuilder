define(['tpl!search/formview.tpl', 'ui/topbarview', 'tpl!search/query.tpl'],
  function(FormTpl, TopbarView, QueryTpl) {

  return B.View.extend({

    className: 'pageview searchview',

    events: {
      'submit .search-form': function(e) {
        e.preventDefault();
        this.doSearch($(e.currentTarget).find('.search-input').val());
      },
      'tap .search-category': function(e) {
        var $checkbox = $(e.currentTarget).find('[type="checkbox"]');
        $checkbox.prop('checked', !$checkbox.prop('checked'));
      }
    },

    initialize: function() {
      this.topbarView = new TopbarView({
        // TO-DO: añadir appName
        title: res.appName,
      });

      this.pass(this.topbarView, 'navigate');

      this.options.searchText = this.options.searchText || '';

      // Convierte un array de ids en un hash id/checked-status
      this.options.checked = _.chain(res.searchCategories)
                              .clone()
                              .reduce(function(memo, value, id) {
                                memo[id] = _.contains(this.options.checked, id) ?
                                          'checked' : '';
                                return memo;
                              }, {}, this)
                              .value();
    },

    render: function() {
      this.$el.html(this.topbarView.render().$el);

      this.$el.append(FormTpl({
        searchText: this.options.searchText,
        checked: this.options.checked
      }));
      return this;
    },

    doSearch: function(searchQuery) {
      var trimmedQuery = $.trim(searchQuery).split(/\s+/).join(' ')
      , checkedCategories = _.map(this.$('.category-checkbox:checked'), function(input) {
        return res.searchCategories[$(input).val()];
      })
      , categoryConditions = ''
      , uri
      ;

      if (checkedCategories.length && checkedCategories.length !== _.size(res.searchCategories)) {
        categoryConditions = _.map(checkedCategories, function(cat) {
          return cat.categoryConditions;
        }).join(' OR ');
      }

      uri = encodeURIComponent(JSON.stringify({
        queryConditions: QueryTpl({
          locale: appConfig.locale,
          text: trimmedQuery,
          categoryConditions: categoryConditions
        })
      }));

      this.trigger('updatequery', {
        searchText: trimmedQuery,
        checked: checkedCategories.map(function(cat) {
          return cat.id;
        })
      });
      this.trigger('navigate', '/pois?' + uri, 1);
    }
  });
});