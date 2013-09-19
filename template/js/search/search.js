define(['globals', 'tpl!search/query.tpl'], function(Globals, QueryTpl) {
  return {
    doSearch: function(queryObj) {
      var trimmedText = $.trim(queryObj.text).split(/\s+/).join(' ')
      , categoryConditions = ''
      ;

      if (queryObj.categories.length && queryObj.categories.length !== _.size(res.searchCategories)) {
        categoryConditions = _.map(queryObj.categories, function(cat) {
          return res.searchCategories[cat].queryConditions;
        }).join(' OR ');
      }

      return  QueryTpl({
        locale: appConfig.locale,
        categoryConditions: categoryConditions,
        text: trimmedText
      });
    }
  };
});