define(
  [
    'globals',
    'schemas/schemas', 'db/loaddb'
  ],
  function(Globals, Db, LoadDb) {

    // BDD y schemas
    persistence.store.websql
      .config(persistence, 'guide', 'Our own very DB', 5 * 1024 * 1024);

    console.log('Sincronizando esquemas'); // DEBUG
    persistence.schemaSync();


    return function(cb) {

      // Carga inicial del nombre de la zona
      var loadZone = function() {
        window.appConfig.zone = localStorage.appZone;
        cb();
      }
      ;

      // Carga inicial de la base de datos
      Db.Category.all().limit(1).list(null, function(results) {
        if (!results.length) {
          console.log('Cargando la base de datos'); //DEBUG
          LoadDb(window.appConfig.locale, loadZone);
        } else {
          return loadZone();
        }
      });
    };
  }
);