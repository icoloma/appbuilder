<div class="navbar">
  <span class="<% if (!root) { %>back-button icon-back<% } %> bar-button"></span>
  <h1 class="pagetitle hideable">{{title}}</h1>
</div>
<div class="actionbar">
  <% if (search) { %>
    <span data-action="search"data-async="true" class="titlesize search-button icon-search bar-button"></span>
  <% } else {%>
    <span class="bar-button icon-menu menu-button"></span>
    <div class="action-menu">
      <% if (filter) { %>
        <span data-action="filter"data-async="true" class="titlesize filter-button icon-filter bar-button"></span>
      <% } %>
      <% if (sort) { %>
        <span data-action="sort"data-async="true" class="titlesize sort-button icon-sort bar-button"></span>
      <% } %>
      <% if (notify) { %>
        <span data-action="notify"class="titlesize notify-button">Notify</span>
      <% } %>
      <% if (map) { %>
      <span data-action="map"class="titlesize map-button bar-button icon-map"></span><% } %>
    </div>
  <% } %>
</div>