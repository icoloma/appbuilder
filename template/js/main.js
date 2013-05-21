define(
  ['modules/router', 'db/initdb'],
  function(Router, InitDb) {
    return {
      router: Router,
      initDb: InitDb
    };
});