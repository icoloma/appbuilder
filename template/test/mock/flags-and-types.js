/*
  Genera los metadatos de flags y types
*/
var random = require('./random-data.js') 
, i18n = require('./i18n-generator.js')
, raw_flags = [
  'AIR_CONDITIONING', 'BAR', 'BBQ', 'BIKE_RENT', 'CAR_RENT',
  'CREDIT_CARD', 'EDUCATIONAL_ACTIVITIES', 'EXCHANGE', 'GUIDED_TOUR', 'HANDICAPPED',
  'LEISURE', 'LOCKER', 'MEDICAL_SERVICE', 'PARKING', 'REPAIR',
  'RESTAURANT', 'NO_CHILDS', 'PETS_ALLOWED', 'PETS_ALLOWED_SIZE', 'SHOP',
  'SOS_SERVICE', 'WC'
]
, raw_types = [
  'BEACH', 'NATURAL_SPACE', 'HOTEL', 'CAMPING', 'APARTMENT', 'MUSEUM', 'MONUMENT',
  'PARK_GARDEN', 'ECO_TOURISM', 'GOLF', 'NAUTICAL_STATION',
]
, flags = []
, types = []
;

console.log('Generando flags y types...');

// Genera los types
_.each(raw_types, function(type, i) {
  types[i] = i18n.object({
    name: function() {
      return type + ' ' + random.variableLorem(0, 1);
    },
    description: random.fixedLorem(3, 10)
  });
  types[i].id = random.createUUID();
  // Un campo solo para testing
  types[i]['_keyword'] = type;
});

// Genera las flags
_.each(raw_flags, function(flag, i) {
  flags[i] = i18n.object({
    name: function() {
      return flag + ' ' + random.variableLorem(0, 1);
    },
    description: random.fixedLorem(3, 10)
  });
  flags[i].id = random.createUUID();
});

module.exports = {
  flags: flags,
  types: types,
};