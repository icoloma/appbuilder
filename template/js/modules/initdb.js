define(
  [
    'db/poi', 'db/category', 'modules/loaddb'
  ],
  function(Poi, Category, LoadDb) {
    // TODO: get correct locale
    // navigator.globalization.
    window.appLocale = 'es';

    // BDD y schemas
    persistence.store.websql
      .config(persistence, 'guide', 'Our own very DB', 5 * 1024 * 1024);

    persistence.schemaSync();

    // Carga inicial de la base de datos
    var findOne = Category.all().limit(1).list(null, function(results) {
      if (!results.length) {
        LoadDb(window.appLocale);
      }
    });

    // TODO: sería bueno que este módulo no retorne hasta que 
    // la carga de datos haya terminado

  }
);