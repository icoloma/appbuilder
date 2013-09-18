define(['db/db', 'mocksql'], function(Db, MockSql) {
  var mockDb = new MockSql.Database();
  Db.initDb(mockDb);

  asyncTest('Proxy BDD', 4, function() {
    var args = {
      sqlStr: 'FOO BAR',
      params: ['foo', 'bar', 'baz', 5],
      callback: function() {},
      errback: function() {},
    };

    mockDb.options = {
      pre: function(sqlStr, params, callback, errback) {
        equal(sqlStr, args.sqlStr);
        equal(params, args.params);
        equal(callback, args.callback);
        equal(errback, args.errback);
      }
    };

    Db.transaction(function(tx) {
      tx.executeSql(args.sqlStr, args.params, args.callback, args.errback);
    });

    start();
  });

  asyncTest('Db.sql', 4, function() {
    mockDb.options = {
      results: [
        { 
          name_es: 'foobar en',
          name_en: 'foobar es',
          lat: 45
        }
      ]
    };

    Db.sql('FOO BAR', [], function(err, results) {
      ok(_.isArray(results), 'Resultados como array');
      ok(_.every(results[0], function(value, key) {
        return Object.getOwnPropertyDescriptor(this, key).writable;
      }, results[0]), 'Plain Old JS Objects');
      ok(_.every(results, function(result, i) {
        return result.name === result['name_' + appConfig.locale];
      }), 'localización OK');
    });

    mockDb.options.error = new Error();

    Db.sql('FOO BAR', [], function(err, results) {
      equal(err, mockDb.options.error, 'Error handling OK');
    });
    start();
  });

  asyncTest('Db.sqlAsCollection', 2, function() {
    var Collection = function(models) {
      this.models = models;
    };
    Collection.model = function(attrs) {
      this.attributes = attrs;
    };

    mockDb.options = {
      results: [
        {
          name_es: 'foobar es',
          name_en: 'foobar en',
          lat: 45,
          starred: 0
        }
      ]
    };

    Db.sqlAsCollection(Collection, 'FOO BAR', [], function(err, collection) {
      ok(collection instanceof Collection, 'Colección');
      ok(_.every(collection.models, function(model) {
        return model instanceof Collection.model;
      }), 'Colección');
    });
    start();

  });
});