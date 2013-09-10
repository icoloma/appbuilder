define(['tpl!search/formview.tpl', 'ui/topbarview'], function(FormTpl, TopbarView) {
  return B.View.extend({

    className: 'pageview searchview',

    events: {
      'submit .search-form': function(e) {
        e.preventDefault();
        this.doSearch($(e.currentTarget).find('.search-input').val());
      }
    },

    initialize: function() {
      this.topbarView = new TopbarView({
        title: res._metadata.appName,
      });

      this.pass(this.topbarView, 'navigate');
    },
    render: function() {
      this.$el.html(this.topbarView.render().$el);
      this.$el.append(FormTpl({
        placeholder: res.Search
      }));
      return this;
    },

    doSearch: function(searchQuery) {
      var trimmedQuery = $.trim(searchQuery).split(/\s+/).join(' ')
      , nameQuery = 'name_' + appConfig.locale + ' GLOB "*' + trimmedQuery + '*"'
      , descQuery = 'desc_' + appConfig.locale + ' GLOB "*' + trimmedQuery + '*"'
      , queryConditions = '(' + nameQuery + ' OR ' + descQuery + ')'
      , uri = encodeURIComponent(JSON.stringify({queryConditions: queryConditions}))
      ;


      this.trigger('navigate', '/pois?' + uri, 1);
    }
  });
});