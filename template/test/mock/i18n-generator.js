
/*
  Herramientas para localizar los datos aleatorios de test.
  Espera un global @locales.
*/

// Campos i18n para la generación de la tabla
var i18nField = function(field) {
  return locales.map(function(lang) {
    return field + '_' + lang;
  });
}
/*
  @generator: una función que genere datos aleatorios.
  @returns: un array de valores localizados del tipo:
  [ 'ES: foo bar...', 'EN: lorem ipsum...', ... ]
*/
, i18nValue = function(generator) {
  return locales.map(function(lang) {
    return lang.toUpperCase() + ': ' + generator();
  });
}

/*
  Genera un objeto 18n a partir de un hash de generadores:
  @generatorHash:
   {
    foo: generate_foos,
    bar: generate_bars
   }
   @returns
   {
    foo_en: 'lore iml lkjf',
    foo_es: '-0wig jk2  crnj',
    ...
    bar_en: '-0df jgf wfi',
    bar_es: '09f oi jj wq',
    ...
   }
*/
, i18nObject = function(generatorHash) {
  var result = {};
  _.each(generatorHash, function(generator, field) {
    _.each(locales, function(lang) {
      _.extend(result, _.object(i18nField(field), i18nValue(generator)));
    });
  });
  return result;
}
;

module.exports = {
  field: i18nField,
  value: i18nValue,
  object: i18nObject
}