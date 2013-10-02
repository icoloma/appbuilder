/*
  Carga los plugins relacionados con eventos táctiles y configura 

*/
define(['hammer', 'ui/pageanimation', 'ui/activable'], function() {

  $(document)
    .hammer()
    .on('tap.checkable', '.checkable', function(e) {
      var $el = $(e.currentTarget).find('[type="checkbox"], [type="radio"]');
      $el.prop('checked', !$el.prop('checked'));
    })
  ;

});