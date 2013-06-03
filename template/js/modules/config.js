define(function() {
  window.appConfig = {
    assets: 'assets/',
    data: 'data/'
  };

  return function(callback) {
    document.addEventListener('deviceready', function () {
      navigator.globalization.getLocaleName(function(locale) {
        window.appConfig.locale = locale.value.match(/^([a-z]{2})/)[1];
        callback();
      }, function(err) {
        // TODO: error handling
      });
    });
  };
});