define(
  ['db/category', 'db/subcategory', 'db/poi'],
  function(Category, SubCategory, Poi) {
    return {
      Category: Category,
      SubCategory: SubCategory,
      Poi: Poi
    };
  }
);