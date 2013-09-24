/*
  Utilidades para la internacionalización.
  AVISO: estos métodos requieren que res.locale *esté definido* para llamarlos
*/
define(['globals'], function() {

  // Las regexp para filtrar campos.
  var i18nPropRegExp = /(.+)_([a-z]{2})/

  // Encuentra los campos localizables en un @json en el idioma de la app (tipo 'name_fr')
  // y los copia en la propiedad sin indentificador de idioma (p.ej. 'name')
  , localizeJSON = function(json) {
    _.each(json, function(value, key) {
      var match = key.match(new RegExp('(.+)_' + res.locale));
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
        if (match[2] === res.locale) json[match[1]] = value;
        delete json[key];
      }
    });
    return json;
  }
  ;

  return {
    config: function(metadata) {

      window.res.i18n = metadata.i18n[res.locale];
      delete metadata.i18n;

      _.each([
        metadata.types, metadata.flags, metadata.flagGroups, metadata.menus,
        metadata.menuEntries, metadata.data, metadata.searchCategories
      ], function(collection) {
        _.each(collection, filterJSON);
      });

      _.extend(window.res, metadata);
    },
    filterJSON: filterJSON,
    localizeJSON: localizeJSON
  };
});