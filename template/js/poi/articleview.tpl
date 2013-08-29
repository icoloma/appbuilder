<div class="titlebar app-container">
  <div class="details">
    <div class="name hideable">{{name}}</div>
    <address class="small">
      <a href="{{geoLink}}" class="geo-link">
        {{address}}&nbsp;<span class="icon-map"></span>
      </a>
    </address>
  </div>
  <span class="star icon-{{isStarred}}"></span>
</div>
<div class="poi-imgs swiper-container">
  <div class="swiper-wrapper">
    <div class="swiper-slide" style="background-image: url({{appConfig.assets+thumb}})"></div>
  </div>
</div>
<div class="row app-container poiview-section">
  <p class="small description col-xs-12">{{desc}}</p>
</div>
<div class="row app-container poiview-section small">
  <% 
    for (var key in data) {
  %>
    <div class="row data-item">
      <div class="col-xs-8 data-item-name">
        {{res._metadata.data[key]}} 
      </div>
      <div class="col-xs-4 data-item-value">
        {{data[key]}}
      </div>
    </div>
  <% }%>
</div>
<div class="row app-container poiview-section">
  <% 
    var group, icon, flag;
    for (var groupId in flagGroups) {
      group = flagGroups[groupId];
  %>
    <div class="col-xs-12 small flag-group">
      <span class="flag-group-line" data-toggle="collapse" data-target="#flags-container-{{group.id}}">
        <span class="flag-group-icon icon-flag-{{group.icon}}"></span>
        <span class="flag-group-name">{{group.name}}</span>
      </span>
      <div class="flags-container collapse" id="flags-container-{{group.id}}">
        <% for (var flagId in group.flags) {
            flag = group.flags[flagId];
        %>
          <div>{{flag.name}}</div>
        <%  } %>
      </div>
    </div>
  <% } %>
</div>
