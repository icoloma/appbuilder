<form class="search-form">
  <div class="search-input-section app-container">
    <div class="search-input-wrapper">
      <!-- <span class="icon-search"></span> -->
        <input type="text" name="text" class="search-input" placeholder={{res.i18n.Search}} value={{tpl.searchText}}>
    </div>
  </div>
  <div class="app-container collectionview search-categories">
    <% for (var id in res.searchCategories) { 
        var entry = res.searchCategories[id];
    %>
      <div class="col-xs-12 item-row search-category checkable">
        <div class="category-line">
          <div class="checkbox-wrapper">
            <input type="checkbox" {{tpl.categories[id]}} value="{{entry.id}}" class="category-checkbox" name="category">
            <label class="fake-checkbox"></label>
          </div> {{entry.label}}
        </div>
      </div>
    <% } %>
  </div>
  <div class="app-container collectionview">
    <div class="col-xs-12 item-row checkable">
      <div class="near-line">
        <div class="checkbox-wrapper">
          <input type="{{tpl.nearPoi ? 'radio' : 'checkbox'}}" {{tpl.near['_near_me_']}} name="near" value="_near_me_">
          <label class="fake-{{tpl.nearPoi ? 'radio' : 'checkbox'}}"></label>
        </div> {{res.i18n.nearMe}}
      </div>
    </div>
    <% if (tpl.nearPoi) { %>
      <div class="col-xs-12 item-row checkable">
        <div class="near-line near-poi-line hideable">
          <div class="checkbox-wrapper">
            <input type="radio" {{tpl.near[tpl.nearPoi.id]}} name="near" value="{{tpl.nearPoi.id}}">
            <label class="fake-radio"></label>
          </div> {{res.i18n.nearPoi + tpl.nearPoi.name}}
        </div>
      </div>
    <% } %>
  </div>
</form>