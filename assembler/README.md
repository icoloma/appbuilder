ASSEMBLER
---------
## Antes de un build
* Instalar las dependencias de `appbuilder/template`.

```
# Dentro de appbuilder/template
npm install
bower install
```

## Puesta en marcha
Build de Android con datos de prueba:

```
# Dependencias
npm install

# Datos de prueba
grunt mock

# Generar e instalar un apk de testeo
grunt build

# El código se genera en 'apps/segittur'.
```

**Comandos disponibles:** `grunt --help`.

Con `grunt compile` se repite el build SIN crear de cero un proyecto Android.

## Opciones de build
Se puede especificar como compilar el código web de `template`:

```
# Ejemplos
grunt build:prod
grunt compile:device
```

Las opciones de compilación son:
 * `prod`: minificación y optimización.
 * `optimized` (default): JavaScript "empaquetado" sin minificar.
 * `device`: JavaScript sin "empaquetar", scripts separados.


## Datos para el build
El `assembler` espera una serie de datos provenientes de `openCatalog`. Para generar unos datos de prueba:

```
grunt mock
```

Para el formato, véase `assembler/FORMAT.md`.