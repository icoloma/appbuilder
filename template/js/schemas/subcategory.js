define(
  ['schemas/poi'],
  function(Poi) {
  var SubCategory = persistence.define('SubCategory', {
    name: 'TEXT', //i18n
  });
  SubCategory.hasMany('pois', Poi, 'subcategory');
  return SubCategory;
});