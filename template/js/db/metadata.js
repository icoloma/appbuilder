/*
  Contenedor para los metadatos de la aplicación: flags, types y menús.
  Permite que los metadatos se mantengan como un .json separado del código, a la vez que 
  la aplicación los utiliza como un módulo AMD.
*/
define(['globals', 'db/db'],
function(Globals, Db) {
  var initMetadata = function(json) {
    _.each(
      [ json.types, json.flags, json.menuConfig.menus, json.menuConfig.entries ],
      function(collection) {
        _.each(collection, Db.utils.filterJSON);
      }
    );
    _.extend(this, json);
  };

  return {
    initMetadata: _.once(initMetadata)
  };
});