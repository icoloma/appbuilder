/*
  Plugin de requirejs para plantillas de Underscore.
  El build de r.js compila las plantillas.
*/
define(['underscore'],
  function() {
  //>>excludeStart("tplExclude", pragmas.tplExclude);

  var buildMap = {}
  , get = function(path, onsuccess) {
    if (typeof window !== 'undefined' && window.navigator && window.document) {
      var xhr = new XMLHttpRequest();
      xhr.onload = function() {
        onsuccess(this.responseText);
      };
      xhr.open('get', path, true);
      xhr.send();      
    } else if (typeof process !== 'undefined') {
      var fs = require.nodeRequire('fs');
      fs.readFile(path, 'utf-8', function(err, data) {
        onsuccess(data);
      });
    }
  }
  , trim = function(str) {
    return str.replace(/\n\s*/g, '');
  }
  ;

  _.templateSettings = {
    interpolate : /\{\{([\s\S]+?)\}\}/g,
    escape      : /\{\{-([\s\S]+?)\}\}/g,
    evaluate      : /<%([\s\S]+?)%>/g
  };

  //>>excludeEnd("tplExclude");

  return {
    //>>excludeStart("tplExclude", pragmas.tplExclude);
    load: function(name, req, onload, config) {
      get(req.toUrl(name), function(value) {
        if (config.isBuild) {
          buildMap[name] = _.template(trim(value)).source;
          onload();
        } else {
          onload(_.template(value));
        }
      });
    },

    write: function (pluginName, moduleName, write) {
      write('define("' + pluginName + '!' + moduleName + '", function() { return ' +
        buildMap[moduleName] + '});');
    }
    //>>excludeEnd("tplExclude");
  };
});