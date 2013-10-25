/*
  Plugin de requirejs para plantillas de Underscore.

  El build de r.js compila las plantillas.
  Además, las plantillas utilizan una variable "global" 'tpl', para mejor rendimiento.
  Ej:
    Código de una plantilla foo.tpl:

      foo is {{tpl.bar}}

    La función compilada se invoca normalmente:

      FooTpl({bar: 'something'});

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
      onsuccess(fs.readFileSync(path, 'utf-8'));
    }
  }

  , trim = function(str) {
    return str.replace(/\n\s*/g, '');
  }

  ;

  var templateSettings = {
    interpolate : /\{\{([\s\S]+?)\}\}/g,
    escape      : /\{\{-([\s\S]+?)\}\}/g,
    evaluate      : /<%([\s\S]+?)%>/g,

    // Pasar un nombre de variable a _.template mejora el rendimiento
    variable: 'tpl'
  };

  //>>excludeEnd("tplExclude");

  return {
    //>>excludeStart("tplExclude", pragmas.tplExclude);

    load: function(name, req, onload, config) {
      get(req.toUrl(name), function(value) {
        if (config.isBuild) {
          buildMap[name] = _.template(trim(value), null, templateSettings).source;
          onload();
        } else {
          onload(_.template(value, null, templateSettings));
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