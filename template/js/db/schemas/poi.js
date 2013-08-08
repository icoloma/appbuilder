define({
  /*
    Schema de un Poi.
    TO-DO: hay cierta duplicación con model/poi.jsonFields
  */


  id: 'VARCHAR(32) PRIMARY KEY',
  type: 'TEXT',
  name_es: 'TEXT', name_en: 'TEXT', name_de: 'TEXT', name_it: 'TEXT', name_fr: 'TEXT',
  desc_es: 'TEXT', desc_en: 'TEXT', desc_de: 'TEXT', desc_it: 'TEXT', desc_fr: 'TEXT',

  /*CAMPOS JSON*/
  prices: 'TEXT',
  contact: 'TEXT',
  timetables: 'TEXT',
  languages: 'TEXT',
  data: 'TEXT',
  flags: 'TEXT',
  /* */

  address: 'TEXT',
  created: 'DATE', lastModified: 'DATE',
  lat: 'REAL', lon: 'REAL', normLon: 'REAL',
  thumb: 'TEXT', imgs: 'TEXT',
  starred: 'BOOLEAN'
});