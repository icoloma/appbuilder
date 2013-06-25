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
    description: 'TEXT', //i18n
    thumb: 'TEXT',
    imgs: 'JSON',
    created: 'DATE',
    updated: 'DATE',
    lat: 'REAL',
    lon: 'REAL',
    normLon: 'REAL',
    starred: 'BOOL',
    idPoi: 'TEXT'
  })
  , oldJSON = Poi.prototype.toJSON
  ;

  /*
    persistence.js no permite exportar los campos 'JSON' al hacer toJSON (who knows)
  */
  Poi.prototype.toJSON = function() {
    var json = oldJSON.apply(this);
    json.imgs = this.imgs;
    return json;
  };
  return Poi;
});