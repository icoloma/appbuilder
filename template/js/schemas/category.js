define(
  ['schemas/subcategory'],
  function(SubCategory) {
  var Category = persistence.define('Category', {
    name: 'TEXT', //i18n
  });
  Category.hasMany('subcategories', SubCategory, 'category');
  return Category;
});