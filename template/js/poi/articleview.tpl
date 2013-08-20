<div class="titlebar row app-container">
  <div class="details col-xs-10">
    <div class="name hideable">{{name}}</div>
    <address class="small">
      <a href="{{geoLink}}"> <span class="icon-map"></span> {{address}}</a>
    </address>
  </div>
  <div class="col-xs-2"><span class="star icon-{{isStarred}}"></span></div>
</div>
<div class="poi-imgs swiper-container">
  <div class="swiper-wrapper">
    <div class="swiper-slide" style="background-image: url({{appConfig.assets+thumb}})"></div>
    <% for (var i in imgs) { 
      var img = imgs[i];
    %>
      <div class="swiper-slide" style="background-image: url({{appConfig.assets+img}})"></div>
    <% } %>
  </div>
</div>
<div class="row app-container">
  <p class="small description col-xs-12">{{desc}}</p>
</div>