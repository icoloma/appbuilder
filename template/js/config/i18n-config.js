/*
  Configura los metadatos y recursos i18n una vez fijado el locale en window.appConfig.locale
*/
define(['db/db'], function(Db) {
  return function(i18nStrings, metadata) {
    window.res = i18nStrings[appConfig.locale];

    _.each([
      metadata.types, metadata.flags,
      metadata.menuConfig.menus, metadata.menuConfig.entries
    ], function(collection) {
      _.each(collection, Db.utils.filterJSON);
    });

    window.res._metadata = metadata;

    for (var i = 2; arguments[i] !== undefined; i++) {
      _.extend(window.res._metadata, arguments[i]);
    }
  };
});