define(function() {
  
  // Longitud 'normalizada'.
  // Supone que @lat viene en grados. Devuelve en las mismas unidades que @lon. 
  var normalizeLon = function(lat, lon) {
    return Math.sin( lat / 180 * Math.PI) * lon;
  }
  , earthRadius = 6370
  ;


  /*
    Métodos utiles para geoposicionamiento
    NO son aptos cerca de los polos o del antimeridiano 180º.
    El error en las distancias es despreciable para su uso dentro de la Península o de un
    archipiélago
  */
  return {
    // Calcula un valor proporcional al cuadrado de la distancia. 
    // Apto para comparar, ahorra operaciones 
    propDistance: function(lat1, lon1, lat2, lon2) {
      var normLon1 = normalizeLon(lat1, lon1)
      , normLon2 = normalizeLon(lat2, lon2)
      ;
      return Math.pow(lat1-lat2, 2) + Math.pow(normLon1 - normLon2, 2);
    },
    // Distancia entre dos puntos en kilómetros.
    // Todos los argumentos deben ir en grados.
    distance: function(lat1, lon1, lat2, lon2) {
      var normLon1 = normalizeLon(lat1, lon1) * Math.PI / 180
      , normLon2 = normalizeLon(lat2, lon2) * Math.PI / 180
      ;
      return earthRadius * Math.sqrt(
        Math.pow((lat1-lat2)/180*Math.PI, 2) + Math.pow(normLon1 - normLon2, 2)
      ); 
    },
    // Filtro para buscar por distancia. 
    // @lat y @lon deben ir en grados, @distance en kilometros
    distanceFilter: function(lat, lon, distance) {
      var normLon = normalizeLon(lat, lon)
      , angle = 180 * distance / (2 * Math.PI * earthRadius)
      , minLat = lat - angle
      , maxLat = lat + angle
      , minNormLon = normLon - angle
      , maxNormLon = normLon + angle
      ;

      return function(latitude, normalizedLon) {
        return latitude > minLat &&
                latitude < maxLat &&
                normalizedLon > minNormLon &&
                normalizedLon < maxNormLon;
      };
    }
    // // Cotas para el filtro por distancia. Las coordenadas deben ir en grados.
    // bounds: function(lat, lon, distance) {
    //   var normLon = normalizeLon(lat, lon)
    //   , angle = 180 * distance / (2 * Math.PI * earthRadius) 
    //   ;

    //   return {
    //     'min-lat': lat - angle,
    //     'max-lat': lat + angle,
    //     'min-normLon': normLon - angle,
    //     'max-normLon': normLon + angle,
    //   };
    // }

  };
});