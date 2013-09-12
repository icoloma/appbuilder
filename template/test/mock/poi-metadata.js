/*
  Genera los metadatos de flags y types
*/
var random = require('./random-data.js') 
, i18n = require('./i18n-generator.js')
, empty_flags = []
, raw_flags = { 
  COMMON: ['AIR_CONDITIONING', 'BBQ', 'EDUCATIONAL_ACTIVITIES', 'GUIDED_TOUR', 'WC', ],
  ACCESSIBILITY: [ 'GUIDE', 'HANDICAPPED_ACCESS' ],
  BEACH: [ 'BEACH_BATH_CONDITION_CALM', 'BEACH_COMPOSITION_SAND', 'BEACH_SAND_TYPE_GOLDEN' ],
  FAMILY: [ 'FAMILY_KINDER', 'FAMILY_BABYSITTING' ],
  LODGING: ['BUSSINESS_CENTER', 'CASINO', 'FREE_FACILITIES', 'FREE_FACILITIES_REQ', 'LIBRARY', ],
  NATURE: [ 'NATIONAL_PARK', 'NATURAL_RESERVE' , 'REGIONAL_PARK' ],
  BUSINESS_GOLF: ['GOLF_BUNKER', 'GOLF_CADDY', 'GOLF_CALL_PLAY_CARD', 'GOLF_CLUB', ],
  BUSINESS_SPORT: ['SPORTS_FOOTBALL', 'SPORTS_GOLF', 'SPORTS_GYM', 'SPORTS_JACUZZI', 'SPORTS_TENIS', ],
  CULTURE_CONSTRUCTION: ['CULTURE_TYPE_ROADWAY', 'CULTURE_TYPE_CHAPEL', 'CULTURE_TYPE_CHARTERHOUSE', 'CULTURE_TYPE_MANOR_HOUSE', 'CULTURE_TYPE_CASTLE', 'CULTURE_TYPE_CASTRO', 'CULTURE_TYPE_CATACOMBS', 'CULTURE_TYPE_CATHEDRAL'],
  BUSINESS_SKI: ['SKI_RENTALS']
}
, raw_types = [
  'BEACH', 'NATURAL_SPACE', 'HOTEL', 'CAMPING', 'APARTMENT', 'MUSEUM', 'MONUMENT',
  'PARK_GARDEN', 'ECO_TOURISM', 'GOLF', 'NAUTICAL_STATION',
]
, dataItemsCount = 50
, flags = {} , types = {}, flagGroups = {}
, data = {}
, dataIds = []
;

console.log('Generando metadatos de POIs...');

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
  type._keyword_dev = raw_type;
  types[type.id] = type;
});

// Genera los flags y grupos de flags 
_.each(raw_flags, function(flag_group, groupName) {
  // Grupo
  var groupID = groupName
  , group = i18n.object({
    name: function() {
      return groupName.toLowerCase() + ' ' + random.variableLorem(1);
    },
    desc: random.fixedLorem(3, 10)
  })
  ;

  group.id = groupID;
  flagGroups[groupID] = group;

  // Flags en el grupo
  _.each(flag_group, function(flagName) {
    var flag = i18n.object({
      name: function() {
        return flagName.toLowerCase() + ' ' + random.variableLorem(0, 1);
      },
      description: random.fixedLorem(3, 10)
    });

    _.extend(flag, {
      id: random.createUUID(),
      group: group.id
    });

    flags[flag.id] = flag;
  });
});

// Genera las cadenas i18n para 'data'
_.times(dataItemsCount, function() {
  var dataEntry = i18n.object({
    label: function() {
      return random.variableLorem(1, 4);
    }
  });
  data[random.createUUID()] = dataEntry;
});

module.exports = {
  flags: flags,
  types: types,
  flagGroups: flagGroups,
  data: data 
};