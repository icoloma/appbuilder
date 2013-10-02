/*
  Gestiona el funcionamiento de los elementos 'activables', marcados como data-activable,
  y del evento 'activableTap'. Este funciona como un 'tap' pero que espera a que cierta
  transición se lleve a cabo.
  
  Se supone un elemento activable *siempre* dispara algún tipo de transición mediante
  la clase 'activating'.
*/

define(['jquery', 'underscore'], function() {

  var $document = $(document) 

  , Active = {

    // El delay mínimo entre un evento touch y el comienzo de la activación
    DELAY: 200,

    $el: null,

    transitioning: false,

    // Espera un tiempo DELAY y dispara la transición
    init: function($el) {
      this.$el = $el;
      _.delay(_.bind(this.fireTransition, this), this.DELAY);
    },

    // Dispara la transición añadiendo la clase .activating. Al final de la misma, actualiza
    // el estado del elemento.
    fireTransition: function() {
      if (this.transitioning || !this.$el) return;

      var self = this;
      this.transitioning = true;
      this.$el.addClass('activating').on('webkitTransitionEnd.pg.activable', function(e) {
        // Los eventos transitionEnd pueden propagarse hasta otras vistas
        e.stopPropagation();

        var $el = $(this);
        self.transitioning = false;
        $el
          .off('webkitTransitionEnd.pg.activable')
          .data('active', true)
          .trigger('activableReady');
      });
    },

    // Cortocircuita la espera para iniciar la transición
    finish: function() {
      if (!this.$el) return;

      var self = this
      , endActivation = function() {
        if (self.$el) {
          self.$el.trigger('activableTap');
        }
        self.cancel();
      }
      ;

      if (this.$el.data('active')) {
        endActivation();
      } else {
        this.$el.on('activableReady', endActivation);

        if (!this.transitioning) {
          this.fireTransition();
        }
      }
    },

    // Resetea el estado de Active
    cancel: function() {
      if (this.$el) {
        this.$el
          .off('webkitTransitionEnd.pg.activable')
          .off('activableReady')
          .data('active', false)
          .removeClass('activating');
      }
      this.transitioning = false;
      this.$el = null;
    }
  };


  $document.on('touchstart.pg.activable', '[data-activable]', function(e) {
    // Cancela el proceso en presencia de múltiples eventos touch
    if (this.$el) {
      Active.cancel();
      return;
    }

    // Lanza el proceso de activación
    var $el = $(e.currentTarget);
    Active.init($el);

    // Cancela la activación al desplazar el touchpoint
    $document.on('touchmove.pg.activable', '[data-activable]', function() {
      $document.off('touchmove.pg.activable');
      Active.cancel();
    });
  });

  $document.on('touchcancel.pg.activable', '[data-activable]', function() {
    $document.off('touchmove.pg.activable');
    Active.cancel();
  });

  $document.on('touchend.pg.activable', '[data-activable]', function() {
    $document.off('touchmove.pg.activable');
    // Dispara el evento 'activableTap'
    Active.finish();
  });

});