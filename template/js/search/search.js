/*
  Búsquedas en BDD
*/
define(['modules/geo', 'db/db', 'poi/model'], function(Geo, Db, Poi) {

  var QueryTpl = _.template(
    '( ( (name_{{tpl.locale}} GLOB "*{{tpl.text}}*") OR ' +
    '(desc_{{tpl.locale}} GLOB "*{{tpl.text}}*") ) {{tpl.conditions}} )'
  )
  
  // Distancia para búsquedas geolocalizadas (km)
  , DISTANCE = 20

  , geoQueryTpl = _.template(
    'lat > {{tpl.min_lat}} AND lat < {{tpl.max_lat}} AND ' +
    'normLon > {{tpl.min_normLon}} AND normLon < {{tpl.max_normLon}}'
  )
  ;

  return {
    /*
      Una "keyword" para representar el punto actual a la hora de geolocalizar.
      La definimos como objeto para asegurar que no choca con ningún otro identificador.
    */
    NEAR_ME: {},

    /*
      Elabora las condiciones para la petición SQL de una búsqueda, de manera asíncrona, ya que puede
      requerir otras llamadas a BDD o geolocalización.
        * @queryObj: {
            geo: ID del POI para buscar por cercanía, o "__NEAR_ME" cuando se trata de buscar 
          }
    */
    searchConditions: function(queryObj, callback) {
      var trimmedText = $.trim(queryObj.text).split(/\s+/).join(' ')
      , conditions = []

      // El callback a llamar si todo ha ido bien
      , successCallback = function() {

        conditions = _.map(conditions, function(condition) {
          return '(' + condition + ')';
        }).join(' AND ');

        if (conditions.length) {
          conditions = ' AND (' + conditions + ')';
        }

        callback(null, QueryTpl({
          locale: appConfig.locale,
          text: trimmedText,
          conditions: conditions
        }));
      }
      ;

      if (queryObj.categories && queryObj.categories.length && queryObj.categories.length !== _.size(res.searchCategories)) {
        conditions.push(_.map(queryObj.categories, function(cat) {
          return res.searchCategories[cat].queryConditions;
        }).join(' OR '));
      }

      if (queryObj.geo === this.NEAR_ME) {
        Geo.getCurrentPosition(function(position) {
          var bounds = Geo.bounds({
            lat: position.coords.latitude,
            lon: position.coords.longitude
          }, DISTANCE);
          conditions.push(geoQueryTpl(bounds));
          successCallback();
        },
        function(err) {
          callback(err, null);
        });
      } else if (queryObj.geo) {
        Db.sql('SELECT lat,normLon FROM POI WHERE id=?', [queryObj.geo], function(err, pois) {
          if (err || !pois.length) {} //TO-DO: error handling
          var poi = new Poi(pois[0], {parse: true});
          var bounds = Geo.bounds({
            lat: poi.get('lat'),
            normLon: poi.get('normLon')
          }, DISTANCE);
          conditions.push(geoQueryTpl(bounds));
          successCallback();
        });
      } else {
        successCallback();
      }
    }
  };
});