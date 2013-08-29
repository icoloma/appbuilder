ASSEMBLER
---------
## Build de Android
Requisitos:
* Sistema operativo *nix.
* Grunt y Ant.
* [Phonegap](http://phonegap.com/) **<= 2.9**.
* El SDK de Android, con `tools` y `platform-tools` en el `PATH`.
* Las dependencias instaladas en `appbuilder/template`.

Para preparar las dependencias:

    # Dentro de appbuilder/assembler
    npm install

### Con datos de prueba

```shell
# Sustituir '/opt/phonegap' por la carpeta donde se encuentre PhoneGap instalado.
grunt test:/opt/phonegap
(cd apps/segittur; ant debug; adb install -rd bin/Segittur-debug.apk)

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
(cd apps/segittur; ant debug; adb install -rd bin/Segittur-debug.apk)
```