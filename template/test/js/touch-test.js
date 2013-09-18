define(['modules/touch'], function() {
  $('body')
    .append($('<div class="touch-1">Touch me</div>'))
    .append($('<div class="touch-2">Touch me</div>'))
    .append($('<div class="touch-3">Touch me</div>'))
  ;

  asyncTest('Evento tap', 3, function() {

    var tap1 = false;
    $('.touch-1').on('tap', function() {
      tap1 = true;
    });
    $('.touch-1')[0].dispatchEvent(touchstart);
    $('.touch-1')[0].dispatchEvent(touchend);

    var tap2 = false;
    $('.touch-2').on('tap', function() {
      tap2 = true;
    });
    $('.touch-2')[0].dispatchEvent(touchstart);
    $('.touch-2')[0].dispatchEvent(touchmove);
    $('.touch-2')[0].dispatchEvent(touchend);

    var tap2 = false;
    $('.touch-2').on('tap', function() {
      tap2 = true;
    });
    $('.touch-2')[0].dispatchEvent(touchstart);
    $('.touch-2')[0].dispatchEvent(touchmove);
    $('.touch-2')[0].dispatchEvent(touchend);

    var tap3 = false;
    $('.touch-3').on('tap', function() {
      tap3 = true;
    });
    $('.touch-3')[0].dispatchEvent(touchstart);
    $('.touch-3')[0].dispatchEvent(touchstart);
    $('.touch-3')[0].dispatchEvent(touchend);


    _.delay(function() {
      equal(tap1, true, 'touchstart+touchend == tap');
      equal(tap2, false, 'touchstart+touchmove+touchend != tap');
      equal(tap3, false, 'touchstart+touchstart+touchend != tap');
      start();
    }, 100);
  });

});