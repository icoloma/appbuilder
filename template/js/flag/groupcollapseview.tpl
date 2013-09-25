<div class="flag-group">
  <span class="flag-group-line {{tpl.open ? '' : 'collapsed'}}" data-toggle="collapse" data-target="#flags-container-{{tpl.id}}">
    <span class="flag-group-icon icon-flag-{{tpl.icon}}"></span>
    <span class="flag-group-name">
      {{tpl.name}} 
    </span>
      <span class="icon-custom open-close"></span>
  </span>
  <ul class="flags-container collapse {{tpl.open ? 'in' : ''}}" id="flags-container-{{tpl.id}}">
  </ul>
</div>