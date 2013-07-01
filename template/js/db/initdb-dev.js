define(
  [
    'globals',
    'schemas/schemas', 'db/loaddb'
  ],
  function(Globals, Db, LoadDb) {

    var initPersistence = function() {
      // BDD y schemas
      persistence.store.websql
        .config(persistence, 'openCatalog', 'Our own very DB', 5 * 1024 * 1024);

      console.log('Sincronizando esquemas'); // DEBUG
      persistence.schemaSync();
    };
    if (location.protocol === 'http:' || location.protocol === 'https:' ) {

      // Depuración en un navegador: fallback a WebSQL
      return function(cb) {

        initPersistence();
        // // Carga inicial del nombre de la zona
        // var loadZone = function() {
        //   window.appConfig.zone = localStorage.appZone;
        //   cb();
        // }
        // ;

        // Carga inicial de la base de datos desde el JSON
        Db.Tag.all().limit(1).list(null, function(results) {
          if (!results.length) {
            console.log('Cargando la base de datos'); //DEBUG
            LoadDb(cb);
          } else {
            // return loadZone();
            cb();
          }
        });
      };
    } else {
      return function(cb) {
        openDatabase({ name: 'openCatalog' });
        initPersistence();
        cb();
      };
    }
  }
);