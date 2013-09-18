define([], function() {

  var TouchEventMock = function(type) {
    var realtype = 'touch' + (type || 'start')
    , e = new CustomEvent(realtype, {bubbles: true});
    e.touches = [];
    return e;
  };

  return TouchEventMock;

});