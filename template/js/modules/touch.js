/*
  @rvidal
  
  Pequeña librería para dar soporte a eventos táctiles
  Soporta: evento 'tap'
  AVISO: el evento 'click' se elimina, pero la acción por defecto en los <a> se
  simula al tiempo que se dispara el 'tap', para poder usar enlaces como siempre

*/

define(['jquery', 'underscore'], function() {
  var $doc = $(document)

  // Namespace para la gestión del evento 'tap'
  , Tap = {
    state: false,
  }

  // Namespace para la gestión del elemento activo.
  // Se espera que el proceso de activación use una trancisión de CSS
  , Active = {

    // Elemento activo
    $el: null,

    transitioning: false,

    DELAY: 200,

    // Inicia la transición
    launchTransition: function() {
      if (this.transitioning || !this.$el) return;

      var self = this;
      this.transitioning = true;
      this.$el.addClass('active').on('webkitTransitionEnd.activeState', function() {
        this.transitioning = false;
        if (!self.$el) return;
        self.$el.off('webkitTransitionEnd.activeState')
                .data('active', true)
                .trigger('activatedByTouch');
      });
    },

    // Activa un elemento '.activable' a partir de un evento 'touchstart' sobre @$source
    init: function($source) {
      var $activable = $source.closest('.activable');

      if ($activable.length) {
        this.$el = $activable;
        var self = this;
        _.delay(function() {
          self.launchTransition();
        }, this.DELAY);
      }
    },

    // Llama a @callback tras terminar el proceso de activación. Si es necesario, se salta
    // el DELAY para que empieze la transición
    finish: function(callback) {
      if (!this.$el) return callback();

      var listener = function() {
        Active.cancel();
        _.nextFrame(callback);
      };

      if (this.$el.data('active')) {
        listener();
      } else {
        this.$el.on('activatedByTouch', listener);
        if (!this.$el.hasClass('active')) {
          this.launchTransition();
        }
      }
    },

    // Cancela el proceso de activación abruptamente
    cancel: function() {
      if (this.$el) {
        this.$el
          .removeClass('active')
          .data('active', undefined)
          .off('webkitTransitionEnd.activeState')
          .off('activatedByTouch');
        this.$el = null;
      }
      this.transitioning = false;
    }
  }

  // Helper para las llamadas en serie en loadAnimation
  , draw = function(action) {
    return function(cb) {
      action();
      // requestAnimationFrame pasa un timestamp que fastida el async.parallel
      _.nextFrame(function() {
        cb();
      });
    };
  }
  ;

  _.nextFrame = (typeof window.requestAnimationFrame === 'function') ? _.bind(requestAnimationFrame, window) : _.defer;

  /*
    Listeners para eventos táctiles.
    Se encargan de gestionar el evento 'tap' al que responde la aplicación y
    el estado '.active' de los elementos que respondan a 'tap' marcados con '.activable'
  */

  $doc.on('touchstart', function(e) {

    // Cancela el tap al entrar con más de un punto de contacto
    // AVISO: no siempre soportado (e.g. Android 2.3.6)
    if (e.originalEvent.touches.length > 1) {
      Tap.state = false;
      Active.cancel();
      return false;
    } else {
      Tap.state = true;
      Active.init($(e.target));

      // Cancela el tap al mover el punto de contacto
      // AVISO: Necesita testeo, por si la pantalla es muy sensible
      $doc.on('touchmove.cancelTap', function() {
        // Solo se dispara una vez
        $doc.off('touchmove.cancelTap');
        Tap.state = false;
        Active.cancel();
      });
    }
  });

  $doc.on('touchend', function(e) {
    Active.finish(function() {
      if (Tap.state) {
        Tap.state = false;
        $(e.target).trigger('tap');

        // Simulamos un click instantáneo en caso de que
        // sea un enlace <a>
        // AVISO: no funcionará para elementos *dentro* de un <a>
        // AVISO: no debería usarse con el router.cache, salvo para enlaces externos quizá?
        if (e.target.tagName === 'A') {
          window.location = e.target.href;
        }
      }
    });
  });

  $doc.on('touchcancel', function() {
    $doc.off('touchmove.cancelTap');
    Tap.state = false;
    Active.cancel();
  });

  // Elimina los 'click' en la fase de captura
  document.addEventListener('click', function(e) {
    e.preventDefault();
  }, true);

  /*
    Animación para el cambio de la vista principal
  */
  $.fn.loadAnimation = function(options, callback) {
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

    // Prepara y dispara la transición entre vistas. Se usa async.parallel y draw para encolar
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


  // return {
  //   delegateScroll: function(el) {
  //     $doc.on('touchmove.delegatedScroll', function(e) {
  //       e.preventDefault();
  //       // TO-DO: pasar la capacidad de scrollar a @el
  //     });
  //   },
  //   undelegateScroll: function() {
  //     $doc.off('touchmove.delegatedScroll');
  //   }
  // };


});