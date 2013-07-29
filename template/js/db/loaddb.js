/* 

  Introduce los datos en la BDD WebSQL en el idoma @locale
  Se llama al inicio de la app, depurando en un navegador

*/
define(
  ['schemas/schemas'],
  function(Db) {

    var loadData = function(callback) {
      return function() {
        var json = JSON.parse(this.responseText)
        , i, tagObj, poi, obj
        // Un hash para llevar la cuenta de las entities 'Tag'
        , tagEntitiesHash = {}
        // Un hash para encontrar el objeto json de cada tag
        , tagJsonHash = _.object(_.pluck(json.tags, 'tag'), json.tags)
        // Añade los tags correspondientes a un POI
        , addTags = function(tags, poiEntity) {

          var processTag = function(tag) {
            if (tagEntitiesHash[tag]) {
              poiEntity.tags.add(tagEntitiesHash[tag]);
            } else {
              tagObj = new Db.Tag(tagJsonHash[tag]);
              poiEntity.tags.add(tagObj);
              tagEntitiesHash[tag] = tagObj;
            }
          };

          tags.forEach(processTag);
        }
        ;

        // for (i = 0; i < json.tags.length; i++) {
        //   cat = json.tags[i];
        //   obj = new Db.Tag(_.omit(cat, ['tags']));
        //   tagEntitiesHash[tag.tag] = tag;
        //   persistence.add(obj);
        // }

        for (i = 0; i < json.pois.length; i++) {
          poi = json.pois[i];
          obj = new Db.Poi(_.omit(poi, [ 'tags']));
          addTags(poi.tags, obj);
          persistence.add(obj);
        }

        // localStorage.appZone = json.zone.name[locale];
        persistence.flush(callback);
      };
    };

    return function(locale, callback) {
      var req = new XMLHttpRequest();
      req.onload = loadData(locale, callback);
      req.open('get', window.appConfig.data + 'openCatalog.json', true);
      req.send();
    };
  }
);