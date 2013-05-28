/*
  @rvidal
  
  Pequeña librería para dar soporte a eventos táctiles
  Soporta: evento 'tap'
  Ojo: el evento 'click' se elimina, pero la acción por defecto en los <a> se
  simula al tiempo que se dispara el 'tap', para poder usar enlaces como siempre

*/

define(['lib/jquery'], function() {
  // Lleva la cuenta los eventos 'touchstart'
  var touched = false
  ;

  $(document).on('touchstart', function(e) {
    // Cancela el tap al entrar con más de un punto de contacto
    // OJO: no siempre soportado (e.g. Android 2.3.6)
    if (e.originalEvent.touches.length > 1) {
      touched = false;
      return false;
    } else {
      touched = true;
    }
  });

  // Cancelar tap al mover el punto de contacto
  // Ojo: Necesita testeo, por si la pantalla es muy sensible
  $(document).on('touchmove', function(e) {
    touched = false;
  });

  // Emite el evento 'tap'
  $(document).on('touchend', function(e) {
    if (touched) {
      touched = false;
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

});