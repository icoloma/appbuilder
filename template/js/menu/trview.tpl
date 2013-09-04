<div class="row item-row activable" 
  data-url="{{url}}" >
  <div class="menu-tr-name col-xs-{{poiCount ? 10 : 12}}">{{label}}</div>
  <% if(poiCount) { %>
    <div class="menu-tr-poicount col-xs-2">{{poiCount}}</div>
  <% } %>
</div>