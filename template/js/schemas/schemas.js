define(
  ['schemas/category', 'schemas/subcategory', 'schemas/poi'],
  function(Category, SubCategory, Poi) {
    return {
      Category: Category,
      SubCategory: SubCategory,
      Poi: Poi
    };
  }
);