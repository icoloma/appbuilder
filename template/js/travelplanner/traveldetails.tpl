<form class="traveldetails-form">
  <div class="app-container transportation-types">
    <div class="transportation-types-group btn-group" data-toggle="buttons">
      <label class="btn btn-default">
        <input type="radio" name="transportation-type" value="car"> Car
      </label>
      <label class="btn btn-default">
        <input type="radio" name="transportation-type" value="bus"> bus
      </label>
      <label class="btn btn-default">
        <input type="radio" name="transportation-type" value="bike"> bike
      </label>
      <label class="btn btn-default">
        <input type="radio" name="transportation-type" value="walk"> walk
      </label>
    </div>
  </div>
  <div class="app-container time-limits">
    <div class="time-limits-container">
      <label>Start time</label>
      <input type="time" name="start-time" value="{{tpl.startTime}}">
      <label>End time</label>
      <input type="time" name="end-time" value="{{tpl.endTime}}">
    </div>
  </div>
  <div class="plan-button">
    <input type="submit" class="btn btn-default" value="Plan my trip">
  </div>
</form>