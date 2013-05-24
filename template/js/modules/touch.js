/*
  @rvidal
  
  Pequeña librería para dar soporte a eventos táctiles
  Soporta: evento 'tap'
  Ojo: elimina el evento click por defecto (e.g. enlaces <a>)

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
      $(e.target).trigger('tap');
    }
  });

  // Elimina los 'click' en la fase de captura
  document.addEventListener('click', function(e) {
    e.preventDefault();
  }, true);
});