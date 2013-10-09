/*
  Utilidades para manipular tiempos
*/
define(['globals'], function() {

  var TIME_REGEX = /([0-9]+):([0-9]{2})/

  , getHours = function(time) {
    return Number(time.match(TIME_REGEX)[1]);
  }

  , getMinutes = function(time) {
    return Number(time.match(TIME_REGEX)[2]);
  }

  /*
    Compara dos timestamps en formato "24h", donde las horas pueden ser > 23
  */
  , compareTime = function(time1, time2) {
    var hours1 = getHours(time1)
    , minutes1 = getMinutes(time1)
    , hours2 = getHours(time2)
    , minutes2 = getMinutes(time2)
    , total1 = hours1*60 + minutes1
    , total2 = hours2*60 + minutes2
    , diff = total1 - total2
    ;

    return diff > 0 ? 1 : (diff < 0 ? -1 : 0);
  }

  /*
    Añade una cierta cantidad de tiempo a una hora inicial.
    *NO* efectúa acarreos: 23:55 + 3600 segundos == 24:55

    @time es la hora inicial, en formato "24h" (las horas pueden ser > 23 y > 100): HHH*:MM
    @duration es la duración en segundos. Puede pasarse opcionalmente más de un parámetro duration.

    El formato final es igual al de @time
  */
  , addTime = function(time, duration) {
    if (arguments.length > 2) {
      var args = Array.prototype.slice.call(arguments);
      args.splice(1, 1);
      time = addTime.apply(this, args);
    }

    var minutes = Math.floor(duration/60)%60
    , hours = Math.floor(duration/3600)
    , initialHour = getHours(time)
    , initialMinute = getMinutes(time)
    , endHour = initialHour + hours
    , endMinute = minutes + initialMinute
    ;

    if (endMinute >= 60) {
      endMinute -= 60;
      endHour++;
    }
    if (endMinute < 10) {
      endMinute = '0' + endMinute;
    }

    return endHour + ':' + endMinute;
  }
  ;

  return {
    add: addTime,
    compare: compareTime
  };
});