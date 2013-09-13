<form class="search-form">
  <div class="search-input-section app-container">
    <div class="search-input-wrapper">
      <!-- <span class="icon-search"></span> -->
        <input type="text" class="search-input" placeholder={{res.Search}} value={{searchText}}>
    </div>
  </div>
  <div class="app-container collectionview search-categories">
    <% for (var id in res._metadata.searchConfig.categories) { 
        var entry = res._metadata.searchConfig.categories[id];
    %>
      <div class="col-xs-12 item-row search-category">
        <div class="category-line">
          <div class="checkbox-wrapper">
            <input type="checkbox" {{checked[id]}} value="{{entry.id}}" class="category-checkbox">
            <label class="fake-checkbox"></label>
          </div> {{entry.label}}
        </div>
      </div>
    <% } %>
  </div>
</form>