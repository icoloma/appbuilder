define(function() {
  return persistence.define('Poi', {
    name: 'TEXT',
    description: 'TEXT',
    thumb: 'TEXT',
    imgs: 'JSON',
    created: 'DATE',
    updated: 'DATE',
    lat: 'REAL',
    lon: 'REAL'
  });
});