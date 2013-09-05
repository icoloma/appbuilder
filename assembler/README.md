ASSEMBLER
---------
## Antes de un build
* Instalar las dependencias de `appbuilder/template`. 

```
# Dentro de appbuilder/template
npm install
bower install
```

## Build de Android
Para preparar las dependencias:

    # Dentro de appbuilder/assembler
    npm install

**Comandos disponibles:** `grunt --help`.

### Con datos de prueba

```shell
# Sustituir '/opt/phonegap' por la carpeta donde se encuentre PhoneGap instalado.
grunt test:/opt/phonegap

# El código se genera en 'apps/segittur'.
```

### Producción
En producción, la carpeta `assembler/app-data` debe contener:
* `metadata.json`: los metadatos de la aplicación (que el assembler fusiona con i18n, etc.)
* `appData.db`: los datos de POIs.
* `assets/`: la carpeta con las imágenes y demás de POIs.

```shell
# Sustituir '/opt/phonegap' por la carpeta donde se encuentre PhoneGap instalado.
grunt prod:/opt/phonegap
```