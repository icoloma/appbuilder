define(['search/search', 'test/mocksql', 'test/mockgeo'], function(Search, MockSql, MockGeo) {


  asyncTest('Recortar texto', 1, function() {
    var text = ' \u000B \u000C foo \u00A0   bar  \u2028 \n baz \t \r  \u2029 ';

    Search.searchConditions({
      text: text,
    }, function(err, queryConditions) {
      ok(/foo bar baz/.test(queryConditions), 'Espacios en blanco eliminados.');
      start();
    });
  });

  asyncTest('Geolocalización', 1, function() {
    MockGeo.options = {
      coordinates: {
        lat: 45,
        lon: 32
      }
    };
    Search.searchConditions({
      text: 'foobar',
      geo: Search.NEAR_ME
    }, function(err, queryConditions) {
      // TO-DO: testar algo decente
      ok(
        /lat >/.test(queryConditions) &&
        /lat </.test(queryConditions) &&
        /normLon >/.test(queryConditions) &&
        /normLon </.test(queryConditions)
      );
      start();
    });
  });

  asyncTest('Cercanía a un POI', 1, function() {
    MockSql.options = {
      results: [
        {
          id: '123fooBAR',
          lat: '20',
          normLon: '5'
        }
      ]
    };
    Search.searchConditions({
      text: 'foobar',
      geo: '123fooBAR'
    }, function(err, queryConditions) {
      // TO-DO: testar algo decente
      ok(
        /lat >/.test(queryConditions) &&
        /lat </.test(queryConditions) &&
        /normLon >/.test(queryConditions) &&
        /normLon </.test(queryConditions)
      );
      start();
    });
  });

  // asyncTest('Búsqueda por categorías', 2, function() {
  //   window.res.searchCategories = {
  //     '1234foobar': {
  //       id: '1234foobar',
  //       queryConditions: 'foo=FOO AND bar=BAZ'
  //     },
  //     '345guau': {
  //       id: '345guau',
  //       queryConditions: 'some conditions'
  //     }
  //   };

  //   Search.searchConditions({
  //     text: 'foo',
  //     categories: ['1234foobar']
  //   }, function(err, queryConditions) {
  //     ok(/foo=FOO AND bar=BAZ/.test(queryConditions), 'Categoría incluida.');
  //   });

  //   Search.searchConditions({
  //     text: 'foo',
  //     categories: ['1234foobar', '345guau']
  //   }, function(err, queryConditions) {
  //     ok(!/AND/.test(queryConditions), 'Sin restricciones por categoría.');
  //     start();
  //   });

  // });

});