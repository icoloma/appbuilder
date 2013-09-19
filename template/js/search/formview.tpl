<form class="search-form">
  <div class="search-input-section app-container">
    <div class="search-input-wrapper">
      <!-- <span class="icon-search"></span> -->
        <input type="text" class="search-input" placeholder={{res.i18n.Search}} value={{tpl.searchText}}>
    </div>
  </div>
  <div class="app-container collectionview search-categories">
    <% for (var id in res.searchCategories) { 
        var entry = res.searchCategories[id];
    %>
      <div class="col-xs-12 item-row search-category">
        <div class="category-line">
          <div class="checkbox-wrapper">
            <input type="checkbox" {{tpl.checked[id]}} value="{{entry.id}}" class="category-checkbox">
            <label class="fake-checkbox"></label>
          </div> {{entry.label}}
        </div>
      </div>
    <% } %>
  </div>
</form>