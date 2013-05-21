define(function() {
  return persistence.define('Poi', {
    name: 'TEXT', //i18n
    description: 'TEXT', //i18n
    thumb: 'TEXT',
    imgs: 'JSON',
    created: 'DATE',
    updated: 'DATE',
    lat: 'REAL',
    lon: 'REAL'
  });
});