define(
  ['db/poi'],
  function(Poi) {
  var SubCategory = persistence.define('SubCategory', {
    name: 'TEXT',
    uuid: 'TEXT'
  });
  SubCategory.hasMany('pois', Poi, 'subcategory');
  return SubCategory;
});