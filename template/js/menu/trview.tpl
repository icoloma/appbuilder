<div class="row item-row" data-activable 
  data-url="{{tpl.url}}" >
  <div class="menu-tr-name col-xs-{{tpl.poiCount ? 10 : 12}}">{{tpl.label}}</div>
  <% if(tpl.poiCount) { %>
    <div class="menu-tr-poicount col-xs-2">{{tpl.poiCount}}</div>
  <% } %>
</div>