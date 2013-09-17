/*
  Modelo de un POI.
    * El JSON de la BDD viene con los campos filtrados según el locale
*/
define(['globals', 'modules/geo', 'db/db'], function(Globals, Geo, Db) {

  return B.Model.extend({

    // Parsea los campos con tipos "especiales"
    parse: function(attrs, opt) {
      attrs = _.clone(attrs);

      // Los campos JSON llegan como strings
      _.each(this.constructor.schema.json, function(field) {
        if (_.isString(attrs[field])) {
          attrs[field] = JSON.parse(attrs[field]);
        }
      });
      // Los campos BOOLEAN llegan como ints
      _.each(this.constructor.schema.bool, function(field) {
        if (_.isString(attrs[field])) {
          attrs[field] = attrs[field] ? true : false;
        }
      });

      return attrs;
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
        return 'geo:' + this.get('lat') + ',' + this.get('lon'); 
                // El nuevo engine de Google Maps no acepta labels 
                // '?q=' + this.get('lat') + ',' + this.get('lon') + 
                // '(' + this.get('name') + ')';
      }
    },

    // Exporta el modelo para la BDD
    toRow: function() {
      var json = this.toJSON();
      _.each(this.constructor.schema.json, function(field) {
        json[field] = JSON.stringify(json[field]);
      });
      _.each(this.constructor.schema.boolean, function(field) {
        json[field] = json[field] ? 0 : 1;
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

    // Algunos tipos de campos necesitan parsearse de manera especial debido a cómo funciona SQLIT
    schema: {
      json: [],
      bool: [],
    },

    // Campos a pedir a la BDD, omitiendo columnas en otros locales
    sqlFields: [],

    // Inicializa el schema de un POI, para dar un tratamiento especial algunos tipos de campos
    initSchema: function(schema) {
      _.each(schema, function(type, field) {
        if (type === 'i18n') {
          this.sqlFields.push(field + '_' + appConfig.locale);
        } else {

          this.sqlFields.push(field);

          if (type === 'BOOLEAN') {
            this.schema.bool.push(field);
          } else if (type == 'JSON') {
            this.schema.json.push(field);
          }
        }
      }, this);
    }
  });
});