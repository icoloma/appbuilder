define(
  [
  // 'schemas/category', 'schemas/subcategory', 
  'schemas/tag', 'schemas/poi'],
  function(Tag, Poi) {
    Poi.hasMany('tags', Tag, 'pois');
    Tag.hasMany('pois', Poi, 'tags');
    return {
      // Category: Category,
      // SubCategory: SubCategory,
      Tag: Tag,
      Poi: Poi
    };
  }
);