define(['globals'], function() {
  return persistence.define('Poi', {
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
    starred: 'BOOL'
  });
});