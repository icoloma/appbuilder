TEMPLATE
--------
## Puesta en marcha
```shell
# En appbuilder/template
npm install
bower install
grunt
# Aplicación corriendo en el puerto 7000 con LiveReload activado
```

**Comandos disponibles**: `grunt --help`.

### Navegadores
El depurado con navegadores sólo está testado en Chrome (necesariamente tiene que ser un navegador WebKit).

Varias cosas no funcionarán bien si se intenta depurar sin un servidor mediante urls `file:`, como Ajax o el evento `deviceready` de Phonegap.

### LiveReload
Las tareas de desarrollo implementan *live reloading*, refrescando el navegador automáticamente cuando ciertos archivos cambian (ver `Gruntfile.js`, tarea `watch`).

### Windows
El paquete `sqlite3` tiene unas [dependencias](https://github.com/TooTallNate/node-gyp#installation) complicadas en Windows. Sin él, fallará la generación de datos de prueba (solo la parte de generar un `appData.db`).

### Dev Tools en mobile
Para acceder a unas dev tools cuando se depura en un dispositivo móvil (requiere dispositivo y desktop conectados a una red local):
* *Antes de desplegar en el móvil*, cambiar `http://192.168.X.XXX` en `index.html` por la IP del desktop.

```shell
weinre --boundHost -all-
# Consola de debugging en http://localhost:8080/client/
```

Funciona usando el navegador móvil o la aplicación compilada.

### Build como aplicación nativa
Ver `appbuilder/assembler/README.md`.