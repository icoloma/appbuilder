define(['globals', 'modules/geo', 'db/db'],
  function(Globals, Geo, Db) {

    // Campos JSON que vienen de la BDD como string
    var jsonFields = [
      'prices', 'contact', 'timetables', 'languages', 'data', 'flags', 'imgs'
    ]
    , booleanFields = [ 'starred' ]
    ;

  return B.Model.extend({

    // Parsea los campos JSON que lleguen como strings
    constructor: function(attrs, opt) {
      _.each(jsonFields, function(field) {
        if (_.isString(attrs[field])) {
          attrs[field] = JSON.parse(attrs[field]);
        }
      });
      _.each(booleanFields, function(field) {
        if (_.isString(attrs[field])) {
          attrs[field] = attrs[field] === 'true' ? true : false;
        }
      });
      B.Model.apply(this, [attrs, opt]);
    },

    propDistanceTo: function(lat, lon) {
      return Geo.propDistance(this.get('lat'), this.get('lon'), lat, lon);
    },
    geoLink: function() {
      if (window.appConfig.platform.match(/ios/i)) {
        // TO-DO: testear!!
        return 'http//:maps.apple.com/?ll=' + this.get('lat') + ',' + this.get('lon') + '&z=17';
      } else {
        // Android como valor por defecto
        return 'geo:' + this.get('lat') + ',' + this.get('lon') + 
               '?q=' + this.get('lat') + ',' + this.get('lon') + 
               '(' + this.get('name') + ')';
      }
    },

    // Exporta el modelo para la BDD
    toRow: function() {
      var json = this.toJSON();
      _.each(jsonFields, function(field) {
        json[field] = JSON.stringify(json[field]);
      });
      return json;
    },

    /* 
      Un pequeño shim para persistir cambios en un POI 
      https://github.com/icoloma/appbuilder/issues/29
    */
    persist: function(callback) {
      var changed = _.pick(this.toRow(), _.keys(this.changed))
      , sqlStr = _.map(changed, function(value, field) {
        return '`' + field + '`="' + value + '",';
      }).join('').slice(0, -1)
      ;
      Db.sql('UPDATE Poi SET ' + sqlStr + ' WHERE `id`="' + this.get('id') + '"', [], function(err, results) {
        // TO-DO: error handling
        callback();
      });
    }
  }, {
    jsonFields: jsonFields
  });
});