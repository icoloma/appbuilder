/*
  Genera un JSON con @poiNumber pois aleatorios
*/
var random = require('./random-data.js')
, i18n = require('./i18n-generator.js')
, poiMetadata = require('./catalogconfig-mocker.js')
, flags = poiMetadata.flags, types = poiMetadata.types, data = poiMetadata.data
// Funciones para escoger type y flags aleatoriamente
, randomType = random.fixedAmountChooser(_.pluck(types, 'id'), 1)
, randomFlags = random.fixedAmountChooser(_.pluck(flags, 'id'), 1, 5)
, randomDataId = random.fixedAmountChooser(_.keys(data), 1)
, randomData = function(min, max) {
  var result = {}
  , count = random.randomInt(min, max)
  ;
  for (var i = 0; i < count; i++) {
    result[randomDataId()] = random.randomInt(1, 100);
  }
  return result;
}
;

module.exports = function(poiNumber) {
  var pois = []
  , poi, lat, lon, imgs
  ;
  for (var i = 0; i < poiNumber; i++) {
    lat = 40 + random.randomInt(0, 200)/100;
    lon = -1 + random.randomInt(0, 200)/100;
    imgs = [ 
    '0.jpg', '1.jpg', '2.jpg', '3.jpg', '4.jpg',
    '5.jpg', '6.jpg', '7.jpg', '8.jpg',
    ];
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
      data: randomData(2, 4),
      /**/
      type: randomType(),
      address: 'Calle ' + random.variableLorem(1, 4),
      flags: randomFlags().split(' '),
      thumb: imgs.splice(random.randomInt(0, imgs.length -1 ), 1)[0],
      imgs: imgs,
      starred: -1
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