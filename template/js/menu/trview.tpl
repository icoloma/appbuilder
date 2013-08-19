<div class="row" 
  data-url="{{url}}" >
  <div class="menu-tr-name col-{{poiCount ? 10 : 12}}">{{label}}</div>
  <% if(poiCount) { %>
    <div class="poiCount col-2">{{poiCount}}</div>
  <% } %>
</div>