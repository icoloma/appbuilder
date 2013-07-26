/*
  Ejemplo de configuración de menús.
  La estructura básica es:

  TODO: i18n

  ** Configuración
  {
    root: <menuID>,
    menus: { 
      <menuId>: <menu>...
    }
  }

  ** Un menú
  {
    id: <string, unique>, //Sería conveniente usar un prefix para las ids
    title: <string>,
    entries: [ entry ]
  }

  ** Una entry. En los menús de nivel superior, las entries llevan a otros menús.
  En los del nivel inferior, ejecutan una query contra la BDD.
  {
    "label": <string>,
    "desc": <string>,
    "poiCount": <number>,
    "menu": <menuID>*,
    "query": <string>*
  }

*/
define(
{
  root: "a0",
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