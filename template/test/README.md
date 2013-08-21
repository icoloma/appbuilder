## Estructura de datos

La aplicación en producción espera encontrar:
* Una base de datos de POIs `appData.db` en la carpeta raíz de los componentes web (junto a `index.html`, `js/`, `css/`, etc.)
* Un fichero de configuración con metadatos y cadenas i18n `appConfig.json` en la misma carpeta raíz.

Este último se genera combinando configuración estática del app-template (cadenas i18n, etc.) con configuración dinámica proveniente del catálogo, a saber: información sobre *types*, *flags* y los menús.

    // appConfig.json
    {
      // Parte "estática"
      i18n: <cadenas i18n de la aplicación>,
      ...
      // Parte "dinámica"
      flags: [ <flag> ],
      types: [ <type> ],
      menuConfig: <menu config>
    }

### Campos i18n en metadatos
Varios objetos, como los *flags*, las entradas de menú, etc. tienen que estar internacionalizados. Los campos «i18n» en realidad requieren una copia por idioma, algo como:

    {
      "name_es": "foo bar",
      "name_en": "fooing baring",
      ...
      "desc_es": "Muy bonito",
      "desc_en": "Very cute",
      ...
    }
La aplicación se encarga de cargar en memoria sólo los datos en el idioma apropiado.

### Menús
La estructura de la entrada `menuConfig` en los metadatos es como sigue:

* Configuración global:

```
menuConfig: {
  root: {
    "menu": <id de un menú>,
    "pois": [ <poiData> ]
  },
  // Un hash de menús por ID
  menus: {
    <id de un menú>: <menu>  
  },
  // Un hash de entradas de menús por ID
  entries: {
    <id de una entry>: <entry>
  }
}
```

* `<poi data>`: datos de POIs en portada

```
{
  "id": <id de un poi>,
  "thumb": <imagen del poi>
}
```

* `<menu>`: Un menú de la aplicación

```
{
  "id": <ID única>,
  "title": <string, i18n>,
  "entries": [ <id de una entry> ]
}
```

* `<entry>`: Una entrada en un menú:

```
{
  "id": <ID única>,
  "label": <string, i18n>,
  "desc": <string, i18n>,
  "poiCount": <number>,
  "menu": <id de un menú>,
  "query": <queryObj>
}
```
El campo `menu` tiene valor cuando se salta a otro submenú. El campo `query` tiene valor cuando se muestra una lista de POIs según algún criterio. **Ambos deben estar presentes**, el que no aplique con valor `null`.

* `<queryObj>`: una *query* para buscar POIs en la BDD.

```
{
  <field>: <value>
}
```