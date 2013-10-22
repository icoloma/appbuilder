<div class="navbar">
  <span data-action="historyback" class="icon-back bar-button {{tpl.hideBackButton}}"></span>
  <h1 class="pagetitle hideable">{{tpl.title}}</h1>
</div>
<div class="actionbar {{tpl.isBlocked}}">
  <span class="bar-button icon-menu menu-button {{tpl.hideMenuButton}}"></span>
  <div class="action-menu {{tpl.wrapActions}}">
    <% for (var i in tpl.actions) { 
        var action = tpl.actions[i];
    %>
      <span data-action="{{action}}" class="titlesize icon-{{action}} bar-button"></span>
    <% } %>
  </div>
</div>