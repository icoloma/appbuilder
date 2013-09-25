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
      <div class="geo-line">
        <div class="checkbox-wrapper">
          <input type="{{tpl.nearPoi ? 'radio' : 'checkbox'}}" {{tpl.geo['__NEAR_ME']}} name="geo" value="__NEAR_ME">
          <label class="fake-checkbox"></label>
        </div> {{res.i18n.nearMe}}
      </div>
    </div>
    <% if (tpl.nearPoi) { %>
      <div class="col-xs-12 item-row checkable">
        <div class="geo-line near-poi-line hideable">
          <div class="checkbox-wrapper">
            <input type="radio" {{tpl.geo[tpl.nearPoi.id]}} name="geo" value="{{tpl.nearPoi.id}}">
            <label class="fake-checkbox"></label>
          </div> {{res.i18n.nearPoi + tpl.nearPoi.name}}
        </div>
      </div>
    <% } %>
  </div>
  <div class="app-container collectionview">
    <div class="col-xs-12 item-row">
      <span data-toggle="collapse" data-target="#search-flag-groups" class="{{tpl.collapsedFlags}} search-by-flags">
        {{res.i18n.SearchByFlags}}
        <span class="icon-custom open-close"></span>
      </span>
      <span class="clear-all">
        {{res.i18n.ClearFlags}}
      </span>
    </div>
  </div>
  <div class="col-xs-12 flag-groups collapse {{tpl.openFlags}}" id="search-flag-groups">
  </div>
</form>