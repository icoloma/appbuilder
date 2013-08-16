/*
  @rvidal
  
  Pequeña librería para dar soporte a eventos táctiles
  Soporta: evento 'tap'
  AVISO: el evento 'click' se elimina, pero la acción por defecto en los <a> se
  simula al tiempo que se dispara el 'tap', para poder usar enlaces como siempre

*/

define(['jquery'], function() {
  var $doc = $(document)

  // Estado del evento 'tap' 
  , tapping = false

  // Cancela el tap al mover el punto de contacto
  // AVISO: Necesita testeo, por si la pantalla es muy sensible
  , cancelTap = function() {
    tapping = false;
    $doc.off('touchmove.cancelTap');
  }
  ;

  $doc.on('touchstart', function(e) {
    // Cancela el tap al entrar con más de un punto de contacto
    // AVISO: no siempre soportado (e.g. Android 2.3.6)
    if (e.originalEvent.touches.length > 1) {
      tapping = false;
      return false;
    } else {
      tapping = true;
      $doc.on('touchmove.cancelTap', cancelTap);
    }
  });

  // Emite el evento 'tap'
  $doc.on('touchend', function(e) {
    if (tapping) {
      tapping = false;
      var $target = $(e.target);
      $target.trigger('tap');
      // Simulamos un click instantáneo en caso de que
      // sea un enlace <a>
      // AVISO: no funcionará para elementos *dentro* de un <a>
      if (e.target.tagName === 'A') {
        window.location = e.target.href;
      }
    }
  });

  // Elimina los 'click' en la fase de captura
  document.addEventListener('click', function(e) {
    e.preventDefault();
  }, true);


  /*
    Animación para el cambio de la vista principal
  */
  $.fn.loadAnimation = function($oldView, $newView, dir) {
    var $this = $(this)
    , width = $oldView.css('width').match(/([0-9]+)px/)[1]
    , widthPx = width + 'px'
    , minusWidthPx = (-width) + 'px'
    , toLeft = dir > 0
    ;

    $this.addClass('animating-views');
    // Fijar tamaño y posición
    $this.append(
      $newView.css({
        width: width,
        position: 'absolute',
        left: toLeft ? widthPx : minusWidthPx,
        top: window.pageYOffset
      })
    );
    $oldView.css({
      width: width,
      position: 'relative',
      left: 0,
      top: 0
    });

    // Dispara la transición. Se aplica un delay para encolarlo y dar tiempo a que los cambios
    // anteriores terminen de dibujarse antes de comenzar la transición.
    // AVISO: necesita testeo.
    _.delay(function() {
      $oldView.css('left', toLeft ? minusWidthPx : widthPx);
      $newView.css({left: '0px'});
    }, 100);

    $this.on('webkitTransitionEnd.changeView', function (e) {
      // Elimina el handler
      $this.off('webkitTransitionEnd.changeView');

      // Deshace el estilado
      $this.removeClass('animating-views');
      $oldView.remove();
      $newView.css({
        width: '',
        position: '',
        left: '',
        top: ''
      });

      if (dir > 0) window.scrollTo(0, 0);

      // Fix para la topbar
      // En Android 2.3.6, después de una animación, deja de ser 'touchable', y se necesita
      // disparar un redraw para volver a la normalidad.
      var $topbar = $this.find('.topbar');
      $topbar.css('width', width + 1);
      _.defer(function() {
        $topbar.css('width', '');
      });
    });
  };


  return {
    delegateScroll: function(el) {
      $doc.on('touchmove.delegatedScroll', function(e) {
        e.preventDefault();
        // TO-DO: pasar la capacidad de scrollar a @el
      });
    },
    undelegateScroll: function() {
      $doc.off('touchmove.delegatedScroll');
    }
  };

});