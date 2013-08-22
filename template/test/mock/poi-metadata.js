/*
  Genera los metadatos de flags y types
*/
var random = require('./random-data.js') 
, i18n = require('./i18n-generator.js')
, empty_flags = []
, raw_flags = empty_flags.concat.apply(empty_flags, [
  'COMMON:PARKING BIKE_RENT', 'ACCESSIBILITY:GUIDE HANDICAPPED_ACCESS',
  'FAMILY:FAMILY_KINDER FAMILY_BABYSITTING', 'LODGING:CASINO LIFT',
  'NATURE:NATIONAL_PARK NATURAL_RESERVE', 'BUSINESS_GOLF: GOLF_CLUB',
  'BUSINESS_SPORT:SPORTS_TENNIS SPORTS_FOOTBALL', 'CULTURE_ARTISTIC:CULTURE_PERIOD_CELTIC'
].map(function(groupStr) {
  var parts = groupStr.split(':')
  ;
  return parts[1].split(' ').map(function(flag) {
    return { keyword: flag, group: parts[0] };
  });
}))
, raw_types = [
  'BEACH', 'NATURAL_SPACE', 'HOTEL', 'CAMPING', 'APARTMENT', 'MUSEUM', 'MONUMENT',
  'PARK_GARDEN', 'ECO_TOURISM', 'GOLF', 'NAUTICAL_STATION',
]
, dataStringsCount = 50
, flags = {}
, types = {}
, dataStrings = _.chain(locales).map(function(lang) {
  return [lang, {}];
}).object().value()
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
  type._keyword = raw_type;
  types[type.id] = type;
});

// Genera las flags
_.each(raw_flags, function(raw_flag, i) {
  var flag = i18n.object({
    name: function() {
      return raw_flag.keyword.toLowerCase() + ' ' + random.variableLorem(0, 1);
    },
    description: random.fixedLorem(3, 10)
  });
  _.extend(flag, {
    id: random.createUUID(),
    group: raw_flag.group
  });
  flags[flag.id] = flag; 
});

_.times(dataStringsCount, function() {
  var dataId = random.createUUID()
  , translations = i18n.object({
    t: function() {
      return random.variableLorem(1, 3);
    }
  })
  , value = random.variableLorem(1)
  ;
  dataIds.push(dataId);
  _.each(translations, function(translation, t_lang) {
    dataStrings[t_lang.slice(2)][dataId] = translation;
  });
});

module.exports = {
  flags: flags,
  types: types,
  dataIds: dataIds,
  dataStrings: dataStrings 
};