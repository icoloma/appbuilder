/*
  TO-DO: i18n
  
  Ejemplo de configuración de menús.
  La estructura básica es:

  ** Configuración
  {
    root: <rootMenu>,
    menus: { 
      <menuId>: <menu>
      ...
    }
  }

  ** Configuración de portada <rootMenu>
  {
    menu: <menuID>,
    pois: [ <poiData> ]
  }

  ** Datos de POI para la portada <poiData> (para ahorrar llamadas a BDD)
  {
    thumb: <thumbID>,
    id: <poiID>
  }

  ** Un <menu>
  {
    id: <menuId, string, unique>, //Sería conveniente usar un prefix para las ids
    title: <string, i18n>,
    entries: [ entry ]
  }

  ** Una entry.
    En los menús de nivel superior, las entries llevan a otros menús.
    En los del nivel inferior, ejecutan una query contra la BDD.
    Se necesitan ambos campos, el que no procede con null
  {
    "label": <string, i18n>,
    "desc": <string, i18n>,
    "poiCount": <number>,
    "menu": <menuID>,
    "query": <string>
  }

*/
define(
{
  root: {
    menu: "a0",
    pois: [
      {
        id: "C630D367521A405B959DD454FD1987B7",
        thumb: "img1.png"
      },
      {
        id: "7068487BF5594A9CA9EAC30B6885AEF4",
        thumb: "img2.png"
      } 
    ]
  },
  menus: {
    "a0": {
      "id": "a0",
      "title": "Huesca",
      "entries": [
        {
          "label": "See",
          "desc": "See stuff in Huesca",
          "menu": "f8",
          "query": null,
          "poiCount": null
        },
        {
          "label": "Sleep",
          "desc": "Take a nap",
          "poiCount": null,
          "menu": "0c",
          "query": null,
        },
        {
          "label": "My favorites",
          "desc": "sdfasdf",
          "poiCount": null,
          "menu": null,
          "query": "starred=true"
        },
      ]
    },
    "f8": {
      "id": "f8",
      "title": "See",
      "entries": [
        {
          "label": "Monuments",
          "desc": "Old rocks",
          "poiCount": 70,
          "menu": null,
          "query": {
            type: "MONUMENT"
          }
        },
        {
          "label": "Aquatic parks",
          "desc": "Get wet!",
          "poiCount": 30,
          "menu": null,
          "query": {
            type: "PARK_GARDEN"
          }
        }
      ]
    },
    "0c": {
      "id": "0c",
      "title": "Sleep",
      "entries": [
        {
          "label": "Hotels",
          "desc": "So fancy...",
          "poiCount": 210,
          "menu": null,
          "query": {
            type: "HOTEL"
          }
        },
        {
          "label": "Camping",
          "desc": "Squirrels!",
          "poiCount": 90,
          "menu": null,
          "query": {
            type: "CAMPING"
          }
        }
      ]
    }
  }
}
);