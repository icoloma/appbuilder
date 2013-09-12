/*
  Utilidades para la internacionalización.
  AVISO: estos métodos requieren que appConfig.locale esté definido antes de llamarse
*/
define(['globals'], function() {

  // Las regexp para filtrar campos. localePropRegExp se inicia cuando el locale está disponible
  var i18nPropRegExp = /(.+)_([a-z]{2})/
  , localePropRegExp

  // Encuentra los campos localizables en un @json en el idioma de la app (tipo 'name_fr')
  // y los copia en la propiedad sin indentificador de idioma (p.ej. 'name')
  , localizeJSON = function(json) {
    _.each(json, function(value, key) {
      var match = key.match(localePropRegExp);
      if (match) {
        json[match[1]] = value;
      }
    });
    return json;
  }

  // Localiza el @json como localizeJSON, pero además eliminando el resto de campos i18n
  // para ahorrar memoria
  , filterJSON = function(json) {
    var regex = /(.+)_([a-z]{2})/;
    _.each(json, function(value, key) {
      var match = key.match(i18nPropRegExp);
      if (match) {
        if (match[2] === appConfig.locale) json[match[1]] = value;
        delete json[key];
      }
    });
    return json;
  }
  ;

  return {
    config: function(i18nStrings, metadata) {
      localePropRegExp = new RegExp('(.+)_' + appConfig.locale);

      window.res = i18nStrings[appConfig.locale];

      _.each([
        metadata.types, metadata.flags, metadata.flagGroups,
        metadata.menuConfig.menus, metadata.menuConfig.entries
      ], function(collection) {
        _.each(collection, filterJSON);
      });

      // TO-DO: simplificar la inclusión de los 'data'
      window.res._metadata = metadata;
      window.res._metadata.data = metadata.data[appConfig.locale];
    },
    filterJSON: filterJSON,
    localizeJSON: localizeJSON
  };
});