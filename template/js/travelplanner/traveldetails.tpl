<form class="traveldetails-form">
  <div class="app-container transportation-types">
    <div class="transportation-types-group btn-group" data-toggle="buttons">
      <label class="btn btn-default">
        <input type="radio" name="transportation-type" value="DRIVING"> Car
      </label>
      <label class="btn btn-default">
        <input type="radio" name="transportation-type" value="TRANSIT"> bus
      </label>
      <label class="btn btn-default">
        <input type="radio" name="transportation-type" value="BICYCLING"> bike
      </label>
      <label class="btn btn-default">
        <input type="radio" name="transportation-type" value="WALKING"> walk
      </label>
    </div>
  </div>
  <div class="app-container time-limits">
    <div class="time-limits-container">
      <label>{{window.res.i18n.StartDay}}</label>
      <input type="date" name="start-date" value="{{tpl.startDate}}">
      <label>{{window.res.i18n.StartTime}}</label>
      <input type="time" name="start-time" value="{{tpl.startTime}}">
      <label>{{window.res.i18n.EndTime}}</label>
      <input type="time" name="end-time" value="{{tpl.endTime}}">
    </div>
  </div>
  <div class="plan-button">
    <input type="submit" class="btn btn-default" value="{{window.res.i18n.PlanMyTrip}}">
  </div>
</form>