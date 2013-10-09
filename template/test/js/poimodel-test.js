define(['poi/model'], function(Poi) {
  var schema = {
    someField: 'TEXT',
    name: 'i18n',
    funny: 'BOOLEAN',
    stuff: 'JSON',
    lat: 'REAL',
    lon: 'REAL',
  }
  , poiData = {
    someField: 'lorem ipsum',
    name_es: 'foobar es',
    name_en: 'foobar en',
    name_jp: 'foobar jp',
    funny: 0,
    stuff: '{"someStuff":"some value"}',
    lat: 40,
    lon: 0.5,
    normLon: 0.32
  }
  ;

  Poi.initSchema(schema);

  module('Poi model test', {
    setup: function() {
      Poi.initSchema(schema);
    },
    teardown: function() {
      Poi.sqlFields = [];
      _.each(Poi.schema, function(fields, type) {
        Poi.schema[type] = [];
      });
    }
  });

  test('Conversión desde SQL', 2, function() {
    var poi = new Poi(poiData, {parse: true});
    
    equal(poi.get('funny'), !!poiData.funny, 'INTEGER->BOOLEAN');
    deepEqual(poi.get('stuff'), JSON.parse(poiData.stuff), 'TEXT->JSON');
  });

  test('Conversión a SQL', 2, function() {
    var row = (new Poi(poiData, {parse: true})).toRow();

    equal(row.funny, poiData.funny, 'BOOLEAN->INTEGER');
    equal(row.stuff, poiData.stuff, 'JSON->TEXT');
  });

  // TO-DO: test de persistencia
});