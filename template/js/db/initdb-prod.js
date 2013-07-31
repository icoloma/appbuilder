define(
  [
    'globals',
    'schemas/schemas'
  ],
  function(Globals, Db) {


    return function(cb) {
      openDatabase({ name: 'data' });
      // BDD y schemas
      persistence.store.websql
        .config(persistence, 'data', 'Our own very DB', 5 * 1024 * 1024);

      persistence.schemaSync();
      cb();
    };

    // return function(cb) {

    //   // Carga inicial del nombre de la zona
    //   var loadZone = function() {
    //     window.appConfig.zone = localStorage.appZone;
    //     cb();
    //   }
    //   ;

    //   // Carga inicial de la base de datos
    //   Db.Tag.all().limit(1).list(null, function(results) {
    //     if (!results.length) {
    //       LoadDb(window.appConfig.locale, loadZone);
    //     } else {
    //       return loadZone();
    //     }
    //   });
    // };
  }
);