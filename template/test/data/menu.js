/*
  TODO: i18n
  
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
  {
    "label": <string, i18n>,
    "desc": <string, i18n>,
    "poiCount": <number>,
    "menu": <menuID>*,
    "query": <string>*
  }

*/
define(
{
  root: {
    menu: "a0",
    pois: [
      {
        id: "51c9a32644ae0be7c55268d2",
        thumb: "img1.png"
      },
      {
        id: "51c9a32744ae0be7c55269dd",
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
          "poiCount": 100
        },
        {
          "label": "Sleep",
          "desc": "Take a nap",
          "poiCount": 300,
          "menu": "0c"
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
          "query": ""
        },
        {
          "label": "Aquatic parks",
          "desc": "Get wet!",
          "poiCount": 30,
          "query": ""
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
          "query": ""
        },
        {
          "label": "Camping",
          "desc": "Squirrels!",
          "poiCount": 90,
          "query": ""
        }
      ]
    }
  }
}
);