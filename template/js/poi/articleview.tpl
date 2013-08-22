<div class="titlebar row app-container">
  <div class="details col-xs-10">
    <div class="name hideable">{{name}}</div>
    <address class="small">
      <a href="{{geoLink}}" class="geo-link">
        {{address}}&nbsp;<span class="icon-map"></span>
      </a>
    </address>
  </div>
  <div class="col-xs-2"><span class="star icon-{{isStarred}}"></span></div>
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
    var flag_icons = window.res._metadata.flag_icons,
    flag, icon;
    for (var i in flags) {
      flag = flags[i];
      icon = flag_icons[flag.group] ? flag_icons[flag.group] : flag_icons['COMMON'];
  %>
    <div class="col-xs-12 small flag-item">
      <span>
        <span class="flag-icon icon-flag-{{icon}}"></span>
        <span class="flag-description">{{flag.name}}</span>
      </span>
    </div>
  <% }%>
</div>
