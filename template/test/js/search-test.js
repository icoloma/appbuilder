define(['search/search', 'test/mocksql', 'test/mockgeo'], function(Search, MockSql, MockGeo) {


  asyncTest('Recortar texto', 1, function() {
    var text = ' \u000B \u000C foo \u00A0   bar  \u2028 \n baz \t \r  \u2029 ';

    Search.searchConditions({
      text: text,
    }, function(err, queryConditions) {
      ok(/foo bar baz/.exec(queryConditions).length, 'Espacios en blanco eliminados.');
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
        /lat >/.exec(queryConditions).length && 
        /lat </.exec(queryConditions).length && 
        /normLon >/.exec(queryConditions).length && 
        /normLon </.exec(queryConditions).length
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
        /lat >/.exec(queryConditions).length && 
        /lat </.exec(queryConditions).length && 
        /normLon >/.exec(queryConditions).length && 
        /normLon </.exec(queryConditions).length
      );
      start();     
    });
  });

  //TO-DO: testar category conditions

});