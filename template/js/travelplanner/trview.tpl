<% 
  var visitDuration = moment.duration(tpl.duration, 's')
  , startTime = tpl.startTime || '??'
  ;

  visitDuration = visitDuration.hours() ? visitDuration.hours() + ' h ' + visitDuration.minutes() + ' m' : visitDuration.minutes() + ' m';

%>

<div class="row item-row" data-poi={{tpl.poi.id}}>
  <div class="step-details col-xs-9">
    <div class="step-poi hideable">{{tpl.poi.name}}</div>
    <!-- <div class="poi-tr-description small hideable">{{tpl.address}}</div> -->
    <div class="step-time small">
      {{res.i18n.Arrival}}: {{startTime}}, {{res.i18n.duration}}: {{visitDuration}}
    </div>
  </div>
  <div class="col-xs-3 poi-tr-thumb" style="background-image: url({{res.resources+tpl.poi.thumb}})"></div>
  <div class="sort-controls col-xs-3">
    <span class="sort-control sort-up icon-up-dir large"></span>
    <span class="sort-control sort-down icon-down-dir large"></span>
  </div>
  <% if (tpl.travel) { 

    var travelDuration = tpl.travel.duration ? moment.duration(tpl.travel.duration, 's') : null
    , distance = tpl.travel.distance ? 
        (tpl.travel.distance > 1000 ? Math.round(tpl.travel.distance/1000) + ' km' : tpl.travel.distance + ' m') :
        '??'
    ;

    travelDuration = travelDuration ?
      (travelDuration.hours() ? travelDuration.hours() + ' h ' + travelDuration.minutes() + ' m' : travelDuration.minutes() + ' m') :
      '??';
  %>
    <div class="step-travel small {{tpl.travel.approx ? 'approx' : ''}}">
      <span class="step-travel-distance">
        {{res.i18n.Distance}}: {{distance}}
      </span> 
      <span class="step-travel-duration">
        ({{travelDuration}})
      </span>
    </div>
  <% } %>
</div>