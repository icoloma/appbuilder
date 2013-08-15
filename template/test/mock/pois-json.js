/*
  Genera un JSON con @poiNumber pois aleatorios
*/
var random = require('./random-data.js')
, i18n = require('./i18n-generator.js')
, flagsAndTypes = require('./flags-and-types.js')
, flags = flagsAndTypes.flags, types = flagsAndTypes.types
// Funciones para escoger type y flags aleatoriamente
, randomType = random.fixedAmountChooser(_.pluck(types, 'id'), 1)
, randomFlags = random.fixedAmountChooser(_.pluck(flags, 'id'), 1, 5)
;

module.exports = function(poiNumber) {
  var pois = []
  , poi, lat, lon
  ;
  for (var i = 0; i < poiNumber; i++) {
    lat = 40 + random.randomInt(0, 200)/100;
    lon = -1 + random.randomInt(0, 200)/100;
    poi = {
      id: random.createUUID(),
      created: 1370000000000 + random.randomInt(0, 10000000000),
      lastModified: 1370000000000 + random.randomInt(0, 10000000000),
      lat: lat,
      lon: lon,
      normLon: Math.sin(lat/180*Math.PI) * lon,
      /**/
      prices: {
        foo: 'bar'
      },
      contact: {
        bar: 'baz'
      },
      timetables: {
        baz: 'foo'
      },
      languages: ['es', 'en'],
      data: {
        'Staff gender ratio': 1,
        'Chairs/tables ratio': 0.1 
      },
      /**/
      type: randomType(),
      address: 'Calle ' + random.variableLorem(1, 4),
      flags: randomFlags().split(' '),
      thumb: 'thumb.png',
      imgs: [ 'img1.png', 'img2.png', 'img3.png' ],
      starred: false
    };
    _.extend(poi,
      i18n.object({
        name: random.fixedLorem(1, 3),
        desc: random.fixedLorem(10, 30)
      })
    );
    pois[i] = poi;
  }
  return pois;
};