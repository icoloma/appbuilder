define(
  function() {
    return persistence.define('Tag', {
      name_es: 'TEXT', //i18n
      name_en: 'TEXT', //i18n
      name_it: 'TEXT', //i18n
      name_fr: 'TEXT', //i18n
      name_de: 'TEXT', //i18n
      tag: 'TEXT'
    });
});