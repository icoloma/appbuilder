define(['globals'], function() {
  var Poi = persistence.define('Poi', {
    name_es: 'TEXT', //i18n
    name_en: 'TEXT', //i18n
    name_it: 'TEXT', //i18n
    name_fr: 'TEXT', //i18n
    name_de: 'TEXT', //i18n
    desc_es: 'TEXT', //i18n
    desc_en: 'TEXT', //i18n
    desc_it: 'TEXT', //i18n
    desc_fr: 'TEXT', //i18n
    desc_de: 'TEXT', //i18n
    address: 'TEXT',
    flags: 'JSON',
    data: 'JSON',
    timetables: 'JSON',
    languages: 'JSON',
    type: 'TEXT',
    contact: 'JSON',
    thumb: 'TEXT',
    imgs: 'JSON',
    created: 'DATE',
    lastModified: 'DATE',
    lat: 'REAL',
    lon: 'REAL',
    normLon: 'REAL',
    starred: 'BOOL',
  })
  , oldJSON = Poi.prototype.toJSON
  , jsonFields = _.filter(Poi.meta.fields, function(value) {
    return value === 'JSON';
  })
  ;

  /*
    persistence.js no permite exportar los campos 'JSON' al hacer toJSON (who knows why)
  */
  Poi.prototype.toJSON = function() {
    var json = oldJSON.apply(this);
    for (var field in jsonFields) {
      json[field] = this[field];
    }
    return json;
  };

  return Poi;
});