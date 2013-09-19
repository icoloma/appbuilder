## Estructura de datos

La app en producción espera la siguiente estructura dentro de `assets`:

    assets/
    ├── appData.db
    └── www
        ├── appMetadata.json
        ├── assets/
        ├── (aplicación web)
        └── ...

* `appData.db` es la base de datos de POIs.
* `assets/` es la carpeta con las imágenes y demás.
* `appMetadata.json` son los metadatos.

## Metadatos
Los metadatos `appMetadata.json` son una combinación de configuración estática del `template` (cadenas i18n, etc.) con configuración dinámica proveniente de `openCatalog`: información sobre *types*, *flags* y los menús.

Para realizar dicha combinación, `assembler` espera el siguiente formato en la carpeta `assembler/tmp-app-data`:

    tmp-app-data/
    ├── appData.db
    ├── catalog-metadata.json
    └── www
        └── assets/

### Formato
El formato esperado para `catalog-metadata.json` es:

    {
      // Metadatos de los FlagGroups
      "flagGroups": {
        <ID única>: {
          "id": <ID única>,
          "name": <string, i18n>,
          "description": <string, i18n>
        },
        ...
      },
      // Una traducción de cada string 'data' en los POIs del volcado
      "data": {
        <ID única>: {
          "label": <string, i18n>
        },
        ...
      },
      // Metadatos de las Flags
      "flags": { 
        <ID única>: {
          "id": <ID única>,
          "name": <string, i18n>,
          "description": <string, i18n>,
          "group": <id de FlagGroup>
        },
        ...
      },
      // Metadatos de los Types
      "types": {
        <ID única>: {
          "id": <ID única>,
          "name": <string, i18n>,
          "description": <string, i18n>
        },
        ...
      },
      // Configuración de menús, ver abajo
      "rootMenu": <root menu>,
      "menus": <menus>,
      "menuEntries": <menu entries>,
      // Configuración de la búsqueda, ver abajo
      "searchCategories": <search categories>
      // Schema de los POIs, ver abajo
      "schema": <schema de un POI>,
    }

En `appbuilder/template` puede generarse un ejemplo con `grunt mock`.

#### Campos i18n en metadatos
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

#### Menús
La configuración de los menús en los metadatos viene dada por:

* Configuración global:

```
"rootMenu": {
  "menu": <id de un menú>,
  "pois": [ <poiData> ]
},
// Un hash de menús por ID
"menus": {
  <ID única>: <menu>  
},
// Un hash de entradas de menús por ID
"menuEntries": {
  <id de una entry>: <entry>
}
```

* `<poi data>`: datos de POIs en portada

```
{
  "id": <id de un poi>,
  "thumb": <imagen del poi (campo 'thumb')>
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
  "queryConditions": <queryStr>
}
```
El campo `menu` tiene valor cuando se salta a otro submenú. El campo `query` tiene valor cuando se muestra una lista de POIs según algún criterio. **Ambos deben estar presentes**, el que no aplique con valor `null`.

* `<queryStr>`: las condiciones para una query SQL.

```
// Ejemplo
"queryConditions": "(type=\"<id de un type>\")"
```

#### Búsqueda
La configuración de la búsqueda en los metadatos viene dada por:

* Configuración global:

```
// Categorías de búsqueda
"searchCategories": {
  <ID única>: {
    "id": <ID única>,
    "label": <string, i18n>,
    "desc": <string, i18n>,
    "categoryConditions": <categoryQueryStr>
  },
  ...
}
```

* `<categoryQueryStr>`: las condiciones a aplicar en una query SQL para esta "categoría"

```
// Ejemplo
"categoryConditions": "(type=\"<id de un type>\") OR (type=\"<id de un type>\") OR (type=\"<id de un type>\")"
```

#### Schema
El campo `schema` especifica las columnas que trae la BDD `appData.db`, con notación especial para los **campos i18n, BOOLEAN y JSON**. Por ejemplo:

    {
      "id": "VARCHAR(32) PRIMARY KEY",
      "address": "TEXT",
      "thumb": "TEXT",
      "lastModified": "REAL",
      "lat": "REAL",
      "lon": "REAL",

      // Una sola entrada para las columnas 'name_en', 'name_es', etc.
      "name": "i18n",
      "desc": "i18n",
  
      // Campos que vengan como strings JSON
      "imgs": "JSON",
      "flags": "JSON",
      
      ...
    }

Los campos *JSON* son campos de texto con el JSON "stringificado". Los campos *i18n* corresponen a una columna de `TEXT` por idioma. Un *BOOLEAN* es un `INTEGER`, 0 o 1.

Véase `appbuilder/template/test/mock/schema.js`.