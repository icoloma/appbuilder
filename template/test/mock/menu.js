/*
  Configuración de menús
*/
var poiMetadata = require('./poi-metadata.js')
, types = poiMetadata.types
, findByKeyword = function(keyword) {
  return _.find(types, function(type) {
    return type._keyword === keyword;
  }).id;
}
;

module.exports = function(pois) {
  return {
    root: {
      menu: "7E8A283A491B413289EBC6010E96B78D",
      pois: [
        {
          id: pois[0].id,
          thumb: pois[0].thumb
        },
        {
          id: pois[1].id,
          thumb: pois[1].thumb
        } 
      ]
    },
    menus: {
      "7E8A283A491B413289EBC6010E96B78D": {
        "id": "7E8A283A491B413289EBC6010E96B78D",
        "title_es": "Huesca",
        "title_en": "Huesca",
        "entries": [
          '914AB9BBE10440A889F6DEBE02AEE584',
          '5B0893B1D2DC4767B45BE3A5DF0FA728',
          'F64F7FC14E674675A67000E4BFCB627A',
          'A9784B92F0184E488A9500BB53B8D0F3',
        ]
      },
      "AE9D636326734A7F8A921E902F1515B8": {
        "id": "AE9D636326734A7F8A921E902F1515B8",
        "title_es": "Ver",
        "title_en": "See",
        "entries": [
          '3F8B5F76B9F94D368D07B951EC9167B6',
          'B7227578EED94855904A3F60BFF7F390',
          '0F9781FAAF6D4E218F767297030C685A'
        ]
      },
      "FC8A6A5A1CDC46DB854FAD0EE57BA706": {
        "id": "FC8A6A5A1CDC46DB854FAD0EE57BA706",
        "title_es": "Dormir",
        "title_en": "Sleep",
        "entries": [
          '4635D82B3AEA44EA922C9E9A2DB99DD8',
          'EAF934E4CE944011A0751BBDF23F1A01',
          '88A223C80158475DAF2AFA4598F84BE2'
        ]
      },
      "92551F8B3C3F4D3BBF12C53D1A59798C": {
        "id": "92551F8B3C3F4D3BBF12C53D1A59798C",
        "title_es": "Hacer",
        "title_en": "Do",
        "entries": [
          '11F4667E110446D78666A2FDD5B62624',
          '17BA689093354DA48E76AFB83CA86F68',
          'F32958AE046F4F19873DBFA52644AE54'
        ]
      }
    },
    entries: {
      '914AB9BBE10440A889F6DEBE02AEE584': {
        id: '914AB9BBE10440A889F6DEBE02AEE584',
        "label_en": "See",
        "label_es": "Ver",
        "desc_en": "See stuff in Huesca",
        "desc_es": "Vea cosas en Huesca",
        "menu": "AE9D636326734A7F8A921E902F1515B8",
        "queryConditions": null,
        "poiCount": null
      },
      '5B0893B1D2DC4767B45BE3A5DF0FA728': {
        id: '5B0893B1D2DC4767B45BE3A5DF0FA728',
        "label_en": "Sleep",
        "label_es": "Dormir",
        "desc_en": "Take a nap",
        "desc_es": "Eche la siesta",
        "poiCount": null,
        "menu": "FC8A6A5A1CDC46DB854FAD0EE57BA706",
        "queryConditions": null,
      },
      'A9784B92F0184E488A9500BB53B8D0F3': {
        id: 'A9784B92F0184E488A9500BB53B8D0F3',
        "label_en": "My favorites",
        "label_es": "Mis favoritos",
        "desc_en": "sdfasdf",
        "desc_es": "sdfasdf",
        "poiCount": null,
        "menu": null,
        "queryConditions": '(starred="true")'
      },
      'F64F7FC14E674675A67000E4BFCB627A': {
        id: 'F64F7FC14E674675A67000E4BFCB627A',
        "label_en": "Do",
        "label_es": "Hacer",
        "desc_en": "Do stuff in Huesca",
        "desc_es": "Haga cosas en Huesca",
        "poiCount": null,
        "menu": '92551F8B3C3F4D3BBF12C53D1A59798C',
        "queryConditions": null
      },
      '3F8B5F76B9F94D368D07B951EC9167B6': {
        "id": '3F8B5F76B9F94D368D07B951EC9167B6',
        "label_en": "Monuments",
        "label_es": "Monumentos",
        "desc_en": "Old rocks",
        "desc_es": "Piedras antiguas",
        "poiCount": 70,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("MONUMENT") + '")'
      },
      'B7227578EED94855904A3F60BFF7F390': {
        "id": 'B7227578EED94855904A3F60BFF7F390',
        "label_en": "Aquatic parks",
        "label_es": "Parques acuáticos",
        "desc_en": "Get wet!",
        "desc_es": "Mójese!",
        "poiCount": 30,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("PARK_GARDEN") + '")'
      },
      '0F9781FAAF6D4E218F767297030C685A': {
        "id": '0F9781FAAF6D4E218F767297030C685A',
        "label_es": "Museos",
        "label_en": "Museums",
        "desc_es": "Rollazo",
        "desc_en": "Booring",
        "poiCount": 15,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("MUSEUM") + '")'
      },
      '4635D82B3AEA44EA922C9E9A2DB99DD8': {
        "id": '4635D82B3AEA44EA922C9E9A2DB99DD8',
        "label_en": "Hotels",
        "label_es": "Hoteles",
        "desc_en": "So fancy...",
        "desc_es": "Qué fancy...",
        "poiCount": 210,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("HOTEL") + '")'
      },
      'EAF934E4CE944011A0751BBDF23F1A01': {
        "id": 'EAF934E4CE944011A0751BBDF23F1A01',
        "label_en": "Camping",
        "label_es": "Acampada",
        "desc_en": "Squirrels!",
        "desc_es": "Ardillas!",
        "poiCount": 90,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("CAMPING") + '")'
      },
      '88A223C80158475DAF2AFA4598F84BE2': {
        "id": '88A223C80158475DAF2AFA4598F84BE2',
        "label_es": "Apartamentos",
        "label_en": "Apartments",
        "desc_es": "Con vecinos!",
        "desc_en": "With neighbours!",
        "poiCount": 45,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("APARTMENT") + '")'
      },
      '11F4667E110446D78666A2FDD5B62624': {
        "id": '11F4667E110446D78666A2FDD5B62624',
        "label_en": "Golf",
        "label_es": "Golf",
        "desc_es": "Ese swing!",
        "desc_en": "Watch your swing",
        "poiCount": 5,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("GOLF") + '")'
      },
      '17BA689093354DA48E76AFB83CA86F68': {
        "id": '17BA689093354DA48E76AFB83CA86F68',
        "label_es": "Playas",
        "label_en": "Beaches",
        "desc_es": "Arena gratis",
        "desc_en": "Free sand",
        "poiCount": 20,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("BEACH") + '")'
      },
      'F32958AE046F4F19873DBFA52644AE54': {
        "id": 'F32958AE046F4F19873DBFA52644AE54',
        "label_es": "Ecoturismo",
        "label_en": "Eco-tourism",
        "desc_es": "P'al campo",
        "desc_en": "Country-side",
        "poiCount": 25,
        "menu": null,
        "queryConditions": '(type="' + findByKeyword("ECO_TOURISM") + '")'
      },
    }
  };
};