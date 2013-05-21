define(
  [
    'db/entities', 'modules/loaddb'
  ],
  function(Db, LoadDb) {
    // TODO: get correct locale
    // navigator.globalization.
    window.appLocale = 'es';

    // BDD y schemas
    persistence.store.websql
      .config(persistence, 'guide', 'Our own very DB', 5 * 1024 * 1024);

    persistence.schemaSync();


    return function(cb) {
      // Carga inicial de la base de datos
      var findOne = Db.Category.all().limit(1).list(null, function(results) {
        if (!results.length) {
          return LoadDb(window.appLocale, cb);
        } else {
          return cb();
        }
      });
    };

    // TODO: sería bueno que este módulo no retorne hasta que 
    // la carga de datos haya terminado

  }
);