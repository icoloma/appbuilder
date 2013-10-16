define(['db/db', 'test/mocksql'], function(Db, MockSql) {

  var dbMock;

  module('Database', {
    setup: function() {
      dbMock = new MockSql.Database();
      MockSql.mockProxy(Db, dbMock);
    },
    teardown: function() {
      delete Db.transaction;
    }
  });

  asyncTest('Proxy BDD', 4, function() {
    var args = {
      sqlStr: 'FOO BAR',
      params: ['foo', 'bar', 'baz', 5],
      callback: function() {},
      errback: function() {},
    };

    dbMock.options = {
      pre: function(sqlStr, params, callback, errback) {
        strictEqual(sqlStr, args.sqlStr);
        strictEqual(params, args.params);
        strictEqual(callback, args.callback);
        strictEqual(errback, args.errback);
      }
    };

    Db.transaction(function(tx) {
      tx.executeSql(args.sqlStr, args.params, args.callback, args.errback);
    });

    start();
  });

  asyncTest('Db.sql', 4, function() {
    dbMock.options = {
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
        return result.name === result['name_' + res.locale];
      }), 'localización OK');
    });

    dbMock.options.error = new Error();

    Db.sql('FOO BAR', [], function(err, results) {
      strictEqual(err, dbMock.options.error, 'Error handling OK');
    });
    start();
  });

  // asyncTest('Db.sqlAsCollection', 2, function() {
  //   var Collection = function(models) {
  //     this.models = models;
  //   };
  //   Collection.model = function(attrs) {
  //     this.attributes = attrs;
  //   };

  //   dbMock.options = {
  //     results: [
  //       {
  //         name_es: 'foobar es',
  //         name_en: 'foobar en',
  //         lat: 45,
  //         starred: 0
  //       }
  //     ]
  //   };

  //   Db.sqlAsCollection(Collection, 'FOO BAR', [], function(err, collection) {
  //     ok(collection instanceof Collection, 'Colección');
  //     ok(_.every(collection.models, function(model) {
  //       return model instanceof Collection.model;
  //     }), 'Colección');
  //   });
  //   start();

  // });
});