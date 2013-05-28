define(
  [
    'schemas/schemas', 'db/loaddb'
  ],
  function(Db, LoadDb) {

    // BDD y schemas
    persistence.store.websql
      .config(persistence, 'guide', 'Our own very DB', 5 * 1024 * 1024);

    persistence.schemaSync();


    return function(cb) {
      // Carga inicial de la base de datos
      var findOne = Db.Category.all().limit(1).list(null, function(results) {
        if (!results.length) {
          return LoadDb(window.appConfig.locale, cb);
        } else {
          return cb();
        }
      });
    };

    // TODO: sería bueno que este módulo no retorne hasta que 
    // la carga de datos haya terminado

  }
);