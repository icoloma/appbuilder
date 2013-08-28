ASSEMBLER
---------
## Build de Android con datos de prueba
Requisitos:
* Sistema operativo *nix.
* Grunt y Ant.
* [Phonegap](http://phonegap.com/) **<= 2.9**.
* El SDK de Android, con `tools` y `platform-tools` en el `PATH`.
* Las dependencias instaladas en `appbuilder/template`.

```shell
# Dentro de appbuilder/assembler
npm install

# Sustituir '/opt/phonegap' por la carpeta donde se encuentre PhoneGap instalado.
grunt test:/opt/phonegap
(cd apps/segittur; ant debug; adb install -rd bin/Segittur-debug.apk)

# El código se genera en 'apps/segittur'.

```