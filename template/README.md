TEMPLATE
--------
## Puesta en marcha
```shell
# Dependencias globales
npm install -g bower grunt-cli

# OJO: dentro de appbuilder/template !!!
npm install
bower install
grunt
# Aplicación corriendo en el puerto 7000 con LiveReload activado
```

Hay más opciones para **grunt** en el `Gruntfile.js`: *optimized*, *device*, *prod*... 
(Varias cosas no funcionarán bien si se intenta depurar sin un servidor mediante urls `file:`, como Ajax o el evento `deviceready` de Phonegap).

### Windows
El paquete `sqlite3` tiene unas [dependencias](https://github.com/TooTallNate/node-gyp#installation) complicadas en Windows. Sin él, fallará la generación de datos de prueba (solo la parte de generar un `appData.db`).

### Dev Tools en mobile
Para acceder a unas dev tools en un dispositivo móvil (requiere dispositivo y desktop conectados a una red local):
* *Antes de desplegar en el móvil*, cambiar `http://192.168.X.XXX` en `index.html` por la IP del desktop.

```shell
#Dependencias globales
npm install -g weinre

weinre --boundHost -all-
# Consola de debugging en http://localhost:8080/client/
```

Vale tanto para el navegador móvil como para la app nativa.

### Build en Android
Ver `appbuilder/assembler/README.md`.

### Esquema de datos
Véase `test/README.md`.