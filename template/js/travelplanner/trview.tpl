<div class="row item-row" data-poi={{tpl.poi.id}}>
  <div class="step-details col-xs-9">
    <div class="step-poi hideable">{{tpl.poi.name}}</div>
    <!-- <div class="poi-tr-description small hideable">{{tpl.address}}</div> -->
    <div class="step-time small">
      <% var duration = moment.duration(tpl.duration, 's');%>
      Arrival: {{tpl.startTime}}, duration: {{duration.hours() ? duration.hours() + ' h ' + duration.minutes() + ' m' : duration.minutes() + ' m'}}
    </div>
    <div class="sort-controls">
      <span class="sort-control sort-up icon-up-dir large"></span>
      <span class="sort-control sort-down icon-down-dir large"></span>
    </div>
  </div>
  <div class="col-xs-3 poi-tr-thumb" style="background-image: url({{res.resources+tpl.poi.thumb}})">
  </div>
  <% if (tpl.travel) { %>
    <% var duration = moment.duration(tpl.travel.duration, 's');%>
    <div class="step-travel small {{tpl.travel.approx ? 'approx' : ''}}">
      <span class="step-travel-distance">
        Distance: {{tpl.travel.distance > 1000 ? Math.round(tpl.travel.distance/1000) + ' km' : tpl.travel.distance + ' m'}}
      </span> 
      <span class="step-travel-duration">
        ({{duration.hours() ? duration.hours() + ' h ' + duration.minutes() + ' m' : duration.minutes() + ' m'}})
      </span>
    </div>
  <% } %>
</div>