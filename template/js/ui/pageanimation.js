/*
  Animación entre pageviews
*/
define(['jquery', 'underscore', 'async'], function() {

  // Helpers

  _.nextFrame = (typeof window.requestAnimationFrame === 'function') ?
    _.bind(requestAnimationFrame, window) : _.defer;

  var draw = function(action) {
    return function(cb) {
      action();
      // requestAnimationFrame pasa un timestamp que fastida el async.parallel
      _.nextFrame(function() {
        cb();
      });
    };
  }
  ;


  $.fn.loadAnimation = function(options, callback) {
    // El contenedor de las vistas
    var $this = $(this)
    // options
    , $oldView = options.$oldView
    , $newView = options.$newView
    , direction = options.direction
    , newScroll = direction > 0 ? 0 : options.newScroll

    , width = $oldView.css('width').match(/([0-9]+)px/)[1]
    , widthPx = width + 'px'
    , minusWidthPx = (-width) + 'px'
    , toLeft = direction > 0
    , newViewOffset
    ;

    // Prepara y dispara la transición entre vistas. Se usa async.series y draw para encolar
    // las tareas y que el navegador tenga tiempo hacer los reflows secuencialmente
    async.series([
      draw(function() {
        // Fijar tamaño y posición
        $this.append(
          $newView.css({
            width: width,
            position: 'absolute',
            left: toLeft ? widthPx : minusWidthPx,
            top: 0
          })
        );
      }),
      draw(function() {
        $this.css('height', $newView.outerHeight());
      }),
      draw(function() {
        $oldView.remove();
      }),
      draw(function() {
        $this.addClass('animating-views');
        window.scrollTo(0, newScroll);
        $newView.css({left: '0px'});
      })
    ]);

    $this.on('webkitTransitionEnd.changeView', function (e) {
      // Elimina el handler
      $this.off('webkitTransitionEnd.changeView');

      // Deshace el estilado
      $this.removeClass('animating-views');
      $newView.css({
        width: '',
        position: '',
        left: '',
        top: ''
      });

      $this.css('height', '');

      // Fix para la topbar
      // En Android 2.3.6, después de una animación, deja de ser 'touchable', y se necesita
      // disparar un redraw para volver a la normalidad.
      var $topbar = $this.find('.topbar');
      $topbar.css('width', width + 1);
      _.nextFrame(function() {
        $topbar.css('width', '');
        callback();
      });
    });
  };


});