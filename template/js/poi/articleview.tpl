<div class="titlebar app-container">
  <div class="details">
    <div class="poi-name hideable">{{tpl.name}}</div>
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
    <div class="swiper-slide" style="background-image: url({{res.resources+tpl.thumb}})"></div>
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
        {{tpl.data[key].label}} 
      </div>
      <div class="col-xs-4 data-item-value">
        {{tpl.data[key].value}}
      </div>
    </div>
  <% }%>
</div>
<div class="row app-container poiview-section">
  <div class="col-xs-12 poi-flags">
  </div>
</div>
