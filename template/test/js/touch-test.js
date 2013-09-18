define(['modules/touch', 'mocktouch'], function(Touch, MockTouch) {
  $('body').append('<div class="touch-test"></div>');
  $('.touch-test')
    .append($('<div class="touch-1">Touch me</div>'))
    .append($('<div class="touch-2">Touch me</div>'))
    .append($('<div class="touch-3">Touch me</div>'))
  ;

  asyncTest('Evento tap', 3, function() {

    var tap1 = false;
    $('.touch-1').on('tap', function() {
      tap1 = true;
    });
    $('.touch-1')[0].dispatchEvent(MockTouch('start'));
    $('.touch-1')[0].dispatchEvent(MockTouch('end'));

    var tap2 = false;
    $('.touch-2').on('tap', function() {
      tap2 = true;
    });
    $('.touch-2')[0].dispatchEvent(MockTouch('start'));
    $('.touch-2')[0].dispatchEvent(MockTouch('move'));
    $('.touch-2')[0].dispatchEvent(MockTouch('end'));

    var tap3 = false;
    $('.touch-3').on('tap', function() {
      tap3 = true;
    });
    $('.touch-3')[0].dispatchEvent(MockTouch('start'));
    $('.touch-3')[0].dispatchEvent(MockTouch('start'));
    $('.touch-3')[0].dispatchEvent(MockTouch('end'));


    _.delay(function() {
      equal(tap1, true, 'touchstart+touchend == tap');
      equal(tap2, false, 'touchstart+touchmove+touchend != tap');
      equal(tap3, false, 'touchstart+touchstart+touchend != tap');
      start();
    }, 100);
  });

});