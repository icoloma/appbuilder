<div class="titlebar app-container">
  <div class="details">
    <div class="name hideable">{{tpl.name}}</div>
    <address class="small">
      <a href="{{tpl.geoLink}}" class="geo-link">
        {{tpl.address}}&nbsp;<span class="icon-map"></span>
      </a>
    </address>
  </div>
  <span class="star icon-{{tpl.isStarred}}"></span>
</div>
<div class="poi-imgs swiper-container">
  <div class="swiper-wrapper">
    <div class="swiper-slide" style="background-image: url({{appConfig.assets+tpl.thumb}})"></div>
  </div>
</div>
<div class="row app-container poiview-section">
  <p class="small description col-xs-12">{{tpl.desc}}</p>
</div>
<div class="row app-container poiview-section">
  <% 
    for (var key in tpl.data) {
  %>
    <div class="row data-item">
      <div class="col-xs-8 data-item-name">
        {{res._metadata.data[key].label}} 
      </div>
      <div class="col-xs-4 data-item-value">
        {{tpl.data[key]}}
      </div>
    </div>
  <% }%>
</div>
<div class="row app-container poiview-section">
  <% 
    var group, icon, flag;
    for (var groupId in tpl.flagGroups) {
      group = tpl.flagGroups[groupId];
  %>
    <div class="col-xs-12 flag-group">
      <span class="flag-group-line collapsed" data-toggle="collapse" data-target="#flags-container-{{group.id}}">
        <span class="flag-group-icon icon-flag-{{group.icon}}"></span>
        <span class="flag-group-name">
          {{group.name}} 
        </span>
          <span class="icon-custom open-close"></span>
      </span>
      <ul class="flags-container collapse" id="flags-container-{{group.id}}">
        <% for (var flagId in group.flags) {
            flag = group.flags[flagId];
        %>
          <li class="flag-name">{{flag.name}}</li>
        <%  } %>
      </ul>
    </div>
  <% } %>
</div>
