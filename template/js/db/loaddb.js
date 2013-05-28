/* 

  Introduce los datos en la BDD en el idoma @locale
  Se llama al inicio de la aplicación, y cada vez que se cambie el idioma.

*/
define(
  ['schemas/schemas'],
  function(Db) {

    var loadData = function(locale, callback) {
      return function() {
        var json = JSON.parse(this.responseText)
        , i, cat, subcat, poi, obj
        , catHash = {}, subcatHash = {}
        ;

        for (i = 0; i < json.categories.length; i++) {
          cat = json.categories[i];
          cat.name = cat.name[locale];
          obj = new Db.Category(cat);
          catHash[cat.uuid] = obj;
          persistence.add(obj);
        }

        for (i = 0; i < json.subcategories.length; i++) {
          subcat = json.subcategories[i];
          subcat.name = subcat.name[locale];
          obj = new Db.SubCategory(_.omit(subcat, 'category'));
          obj.category = catHash[subcat.category];
          subcatHash[subcat.uuid] = obj;
          persistence.add(obj);
        }

        for (i = 0; i < json.pois.length; i++) {
          poi = json.pois[i];
          poi.name = poi.name[locale];
          poi.description = poi.description[locale];
          obj = new Db.Poi(_.omit(poi, 'subcategory')); //TODO: qué hacer con el id o uuid de poi
          obj.subcategory = subcatHash[poi.subcategory];
          persistence.add(obj);
        }

        persistence.flush(callback);

        window.appConfig.zone = json.zone.name[locale];
      };
    };

    return function(locale, callback) {
      var req = new XMLHttpRequest();
      req.onload = loadData(locale, callback);
      req.open('get', window.appConfig.data + 'data.json', true);
      req.send();
    };
  }
);