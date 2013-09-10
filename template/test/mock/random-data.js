/*
  Generadores pseudo-aleatorios para datos de prueba.
  Utilizan un randomizador con seed, de modo que mientras no se cambién los parámetros,
  el resultado es consistente. 
*/
var seedRandom = require('seed-random')
, randomUUIDGenerator = seedRandom('uuid')
, rand = seedRandom('cthulhu ftangh')
, loremIpsum =
  ['nullam','egestas','velit','et','euismod','placerat','nulla','consequat','risus','lectus',
  'eget','feugiat','dui','dictum','a','duis','nec','arcu','ut','magna','ornare','lobortis',
  'in','neque','justo','curabitur','tortor','commodo','vestibulum','ante','ipsum','primis',
  'faucibus','orci','luctus','ultrices','posuere','cubilia','curae','suspendisse','lacinia',
  'proin','elementum','vel','quam','pulvinar','aliquet','quisque','at','tempor','sem','ligula',
  'facilisis','tincidunt','sodales','consectetur','erat','donec','non','leo','eros','tempus',
  'ac','aliquam','vehicula','lorem','sit','amet','lacus','mauris','rhoncus','cursus','venenatis'
  ,'vitae','pellentesque','odio','sagittis','morbi','nibh','bibendum','nisl','nam','ultricies',
  'integer','convallis','fermentum','mi','eu','vulputate','gravida','mattis','phasellus','sed',
  'pharetra','interdum','accumsan','imperdiet','est','diam','quis','scelerisque','nunc','nisi',
  'viverra','iaculis','vivamus','felis','fusce','condimentum','urna','ullamcorper','dignissim',
  'sollicitudin','id','pretium','porttitor','tellus','sapien','turpis','dolor','metus',
  'tristique','malesuada','varius','mollis','praesent','rutrum']
// Extraido de github.com/zefhemel/persistencejs/blob/7b34341a6027284b635bb97ba7059848adfe685a/lib/persistence.js#L1259 
// Math.random sustituido por una función determinista
, createUUID = function() {
  var s = []
  , hexDigits = "0123456789ABCDEF"
  , random = randomUUIDGenerator
  ;
  for ( var i = 0; i < 32; i++) {
    s[i] = hexDigits.substr(Math.floor(random() * 0x10), 1);
  }
  s[12] = "4";
  s[16] = hexDigits.substr((s[16] & 0x3) | 0x8, 1);

  var uuid = s.join("");
  return uuid;
}

// @returns: un entero entre @min y @max, ambos inclusive
, randomInt = function(min, max) {
  var amount = min;
  if (max) {
    amount += Math.floor(rand() * (max - min + 1));
  }
  return amount;
}

// @returns: una string formada por @amount elementos elegidos aleatoriamente de @array
, _randomArrayElementChooser = function(array, amount) {
  var result = [];
  for (var i = 0; i < amount; i++) {
    result.push(
      array[Math.floor(rand()*array.length)]
    );
  }
  return result.join(' ');
}

// @returns: una función que devuelve elementos aleatorios de @array (unidos en una string),
// tantos como según se le especifique en sus parámetros (min, max)
, variableAmountChooser = function(array) {
  return function(min, max) {
    var amount = randomInt(min, max);
    return _randomArrayElementChooser(array, amount);
  };
}

// @returns: una función que devuelve entre @min y @max elementos aleatorios de @array
, fixedAmountChooser = function(array, min, max) {
  return function() {
    var amount = randomInt(min, max);
    return _randomArrayElementChooser(array, amount);
  };
}
// @returns: una string aleatoria de lorem-ipsum de entre @min y @max palabras
, variableLorem = function(min, max) {
  return (variableAmountChooser(loremIpsum))(min, max);
}

// @returns: una función de 0 parámetros que devuelve una string aleatoria de lorem-ipsum
// de entre @min y @max palabras
, fixedLorem = function(min, max) {
  return fixedAmountChooser(loremIpsum, min, max);
}
;

module.exports = {
  fixedAmountChooser: fixedAmountChooser,
  variableAmountChooser: variableAmountChooser,
  fixedLorem: fixedLorem,
  variableLorem: variableLorem,
  createUUID: createUUID,
  randomInt: randomInt
};