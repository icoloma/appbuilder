define(['router/baserouter'], function(BaseRouter) {

  var $el = $('<div class="baserouter-test"></div>')
  , router = new BaseRouter({
    $el: $el
  })
  ;

  $('body').append($el);
  B.history.start();

  test('Navegación básica', 1, function() {
    try {
      var test = true;
      router.navigateTo('one', 1);
      test = test && window.location.hash === '#one';
      router.navigateTo('two', 1);
      test = test && window.location.hash === '#two';
      ok(test, 'Navegación básica OK');
    } catch(e) {};
  });

  test('Historial', 2, function() {
    router.navigateTo('one', 1);

    $('.baserouter-test').css('height', 5000);
    window.scrollTo(0, 1000);

    router.navigateTo('two', 1);
    router.navigateTo('foobar', -1);

    strictEqual(window.location.hash, '#one');
    strictEqual(window.pageYOffset, 1000);
  });

  test('Actualizar URI', 1, function() {
    var params = {foo: 'bar'}
    , newParams = {baz: 'foo'}
    , finalParams = _.extend(_.clone(params), newParams)
    ;

    router.navigateTo('one?' + encodeURIComponent(JSON.stringify(params)), 1);
    router.updateUri(newParams);

    router.navigateTo('two', 1);
    router.navigateTo('foobar', -1);

    var queryStr = /\?(.*)/.exec(window.location.hash)[1];
    deepEqual(JSON.parse(decodeURIComponent(queryStr)), finalParams);
  });

  test('Cambio de vista', 5, function() {
    var params = {foo: 'bar'}
    , oldView
    ;

    router.currentView = null;
    router.setView(B.View);
    oldView = router.currentView;

    $.fn.loadAnimation = function() {
      ok(true, 'loadAnimation invocado');
    };

    router.setView(B.View);

    router.currentView.trigger('navigate', 'one?', 1);
    strictEqual(window.location.hash, '#one?', 'Listener de navegación OK');

    router.currentView.trigger('updatequery', params);
    router.navigateTo('two', 1);
    router.navigateTo('foobar', -1);

    var queryStr = /\?(.*)/.exec(window.location.hash)[1];
    deepEqual(JSON.parse(decodeURIComponent(queryStr)), params, 'Listener de URI OK');

    router.navigateTo('three?', 1);

    oldView.trigger('navigate', 'four?');
    notStrictEqual(window.location.hash, '#four?', 'La vista vieja no afecta a la navegación.');

    oldView.trigger('updatequery', encodeURIComponent(JSON.stringify(params)));
    queryStr = /\?(.*)/.exec(window.location.hash)[1];
    ok(!queryStr.length, 'La vista vieja no afecta a la URI.');
  });

});