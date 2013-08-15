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
, flags = {}
, types = {}
;

console.log('Generando flags y types...');

// Genera los types
_.each(raw_types, function(raw_type, i) {
  var type = i18n.object({
    name: function() {
      return raw_type + ' ' + random.variableLorem(0, 1);
    },
    description: random.fixedLorem(3, 10)
  });
  type.id = random.createUUID();
  // Un campo solo para testing
  type._keyword = raw_type;
  types[type.id] = type;
});

// Genera las flags
_.each(raw_flags, function(raw_flag, i) {
  var flag = i18n.object({
    name: function() {
      return raw_flag + ' ' + random.variableLorem(0, 1);
    },
    description: random.fixedLorem(3, 10)
  });
  flag.id = random.createUUID();
  flags[flag.id] = flag; 
});

module.exports = {
  flags: flags,
  types: types,
};