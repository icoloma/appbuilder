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
Requisitos:
* Sistema operativo *nix (de momento).
* [Phonegap](http://phonegap.com/) **<= 2.9**.
* El SDK de Android, con `tools` y `platform-tools` en el `PATH`.

```shell
build_android() {
  rm -r template-android-build
  "$1"/lib/android/bin/create template-android-build com.segittur segittur
  grunt optimized
  rm -r template-android-build/assets/www
  mv build/ template-android-build/assets/www
  cp "$1"/lib/android/cordova*js template-android-build/assets/www/phonegap.js
  cp test/data/appData.db template-android-build/assets/
  cd template-android-build
  wget https://github.com/jrvidal/PG-SQLitePlugin-Android/archive/master.zip
  unzip master.zip; rm master.zip
  cp -r PG-SQLitePlugin-Android-master/Android/src/com/phonegap src/com
  sed -i "s/<plugins>/<plugins>\n<plugin name=\"SQLitePlugin\" value=\"com.phonegap.plugin.sqlitePlugin.SQLitePlugin\"\/>/" res/xml/config.xml
  rm -r PG-SQLitePlugin-Android-master
  cd ..
}
# Dentro de appbuilder/template
build_android {carpeta de phonegap}
```

### Esquema de datos
Véase `test/README.md`.