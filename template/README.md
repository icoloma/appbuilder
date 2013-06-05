TEMPLATE
--------
Las dependencias de la aplicación requieren [node.js](http://nodejs.org) y [npm](http://npmjs.org) para instalarse.

    npm install

Para depurar en un navegador, hay que levantar un servidor en la máquina local:
* Node.js: [http-server](https://github.com/nodeapps/http-server), [simple-http-server](https://github.com/andrewpthorp/simple-http-server)
* [Python](http://docs.python.org/2/library/simplehttpserver.html)

(Varias cosas no funcionarán bien si se intenta depurar mediante urls `file:`, como Ajax o el evento `deviceready` de Phonegap).

En la configuración en desarrollo, la página de inicio `index.html` contiene un enlace a `192.168.X.XXX:8080/target/target-script-min.js` para acceder a las dev tools mientras se prueba en un dispositivo móvil, usando [Weinre](http://people.apache.org/~pmuellr/weinre/docs/latest/):
    
    # Requiere permisos de admin
    npm install -g weinre
    weinre --boundHost -all-
    # Dev tools en: http://localhost:8080/client/

### Maneras de depurar. Grunt.js. Build para producción.
La aplicación incluye un `Gruntfile` para automatización de tareas.

    # Instalar 
    npm install -g grunt-cli
    grunt {nombre de la tarea}
    # Sin instalar grunt-cli de forma global (requiere permisos) se puede hacer:
    node node_modules/.bin/grunt {nombre de la tarea}

La tarea `dev` simplemente compila la hoja LESS y comprueba con JSHint el código JavaScript. Esto deja lista la aplicación para depurar con un navegador desktop o móvil. El resto de tareas compila en una carpeta `build` y permite otras opciones:
* `device`: simplemente copia la estructura de ficheros. Útil para depurar en un móvil compilando una aplicación nativa.
* `optimize`: optimiza el javascript. Útil para depurar el build de Require.js, ya sea con un navegador o en nativo. 
* `prod`: compila JS y LESS para producción (necesitará "ensamblarse" con los datos del catálogo).

## Esquema de datos
Los schemas en `js/schemas` contienen el formato esperado para el volcado de datos desde el repositorio, así como el ejemplo en `test/data/data.json`.