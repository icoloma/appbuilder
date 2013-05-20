define(
  ['db/subcategory'],
  function(SubCategory) {
  var Category = persistence.define('Category', {
    name: 'TEXT',
    uuid: 'TEXT'
  });
  Category.hasMany('subcategories', SubCategory, 'category');
  return Category;
});