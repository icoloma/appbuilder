define(function() {
  var icon = function(name) {
    return 'icon-flag-' + name;
  };

  return {
    COMMON: icon('common'), 
    FAMILY: icon('family'), 
    ACCESSIBILITY: icon('accessibility'),
    // QUALITY: icon(), 
    NATURE: icon('nature'), 
    BEACH: icon('beach'),
    CULTURE: icon('cultural'),
    CULTURE_ARTISTIC: icon('cultural'),
    CULTURE_CONSTRUCTION: icon('cultural'),
    CULTURE_DESIGNATION: icon('cultural'),
    CULTURE_HISTORICAL: icon('cultural'),
    BUSINESS_ACTIVITY: icon('cultural'),
    BUSINESS_NAUTICAL: icon('cultural'),
    BUSINESS_SKI: icon('ski'),
    BUSINESS_SPORT: icon('sport'),
    GOLF_TYPE: icon('golf'),
    BUSINESS_GOLF: icon('golf'),
    LODGING: icon('lodging'), 
    LODGING_SERVICES: icon('lodging'), 
    ROOM: icon('lodging'),
  };
});