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
          attrs[field] = JSON.parse(attrs[field] || null);
        }
      });
      // Los campos BOOLEAN llegan como ints
      _.each(this.constructor.schema.boolean, function(field) {
        if (_.isString(attrs[field])) {
          attrs[field] = attrs[field] ? true : false;
        }
      });

      // Android SDK <=11 guarda todo como strings
      // TO-DO: dejar esto como un override solo para Android
      _.each(this.constructor.schema.real, function(field) {
        attrs[field] = attrs[field] && Number(attrs[field]);
      });

      _.each(this.constructor.schema.integer, function(field) {
        attrs[field] = attrs[field] && Number(attrs[field]);
      });

      return attrs;
    },

    propDistanceTo: function(lat, lon) {
      return Geo.propDistance(this.get('lat'), this.get('lon'), lat, lon);
    },
    geoLink: function() {
      if (window.res.platform.match(/ios/i)) {
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
        json[field] = json[field] ? 1 : 0;
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
      boolean: [],
      real: [],
      integer: []
    },

    // Campos a pedir a la BDD, omitiendo columnas en otros locales
    sqlFields: [],

    // Inicializa el schema de un POI, para dar un tratamiento especial algunos tipos de campos
    initSchema: function(schema) {
      _.each(schema, function(type, field) {
        if (type === 'i18n') {
          this.sqlFields.push(field + '_' + res.locale);
        } else {

          this.sqlFields.push(field);

          if (type === 'BOOLEAN') {
            this.schema.boolean.push(field);
          } else if (type == 'JSON') {
            this.schema.json.push(field);
          } else if (type === 'REAL') {
            this.schema.real.push(field);
          } else if (type === 'INTEGER') {
            this.schema.integer.push(field);
          }
        }
      }, this);
    }
  });
});