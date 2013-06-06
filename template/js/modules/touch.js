/*
  @rvidal
  
  Pequeña librería para dar soporte a eventos táctiles
  Soporta: evento 'tap'
  Ojo: el evento 'click' se elimina, pero la acción por defecto en los <a> se
  simula al tiempo que se dispara el 'tap', para poder usar enlaces como siempre

*/

define(['lib/jquery'], function() {
  var $doc = $(document)

  // Estado del evento 'tap' 
  , tapping = false

  // Cancela el tap al mover el punto de contacto
  // Ojo: Necesita testeo, por si la pantalla es muy sensible
  , cancelTap = function() {
    tapping = false;
    $doc.off('touchmove.cancelTap');
  }
  ;

  $doc.on('touchstart', function(e) {
    // Cancela el tap al entrar con más de un punto de contacto
    // OJO: no siempre soportado (e.g. Android 2.3.6)
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
      if (e.target.tagName === 'A') {
        window.location = e.target.href;
      }
    }
  });

  // Elimina los 'click' en la fase de captura
  document.addEventListener('click', function(e) {
    e.preventDefault();
  }, true);

  return {
    delegateScroll: function(el) {
      $doc.on('touchmove.delegatedScroll', function(e) {
        e.preventDefault();
        console.log("No scroll");
        // Do something
      });
    },
    undelegateScroll: function() {
      console.log("Scroll is back!");
      $doc.off('touchmove.delegatedScroll');
    }
  };

});