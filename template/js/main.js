define(['modules/config', 'modules/router', 'db/initdb', 'modules/i18n'],
  function(Config, Router, InitDb, i18n) {
    return {
      Router: Router,
      Boot: function(callback) {
        async.series([
          Config,
          function(cb) {
            // i18n: se busca el idioma del dispositivo en los locales del app, tomando inglés como
            // fallback. OJO: se asume que los locales del app y de los datos del catálogo son los mismos
            window.appConfig.locale = 
              window.appConfig.locale in i18n ? window.appConfig.locale : 'en'; 
            window.res = i18n[window.appConfig.locale];
            cb();
          },
          InitDb
        ], function(cb) {
          callback();
        });
      }
    };
  }
);