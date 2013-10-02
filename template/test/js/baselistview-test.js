define(['list/baselistview', 'test/mocktouch'], function(ListView, MockTouch) {

  var trView = function(json) {
    return '<div class="item-row" data-url="' + json.url + '">' +
             json.name +
            '</div>';
  }
  , collection = new B.Collection([
    new B.Model({name: 'foo', url: 'foo.com'}),
    new B.Model({name: 'bar', url: 'bar.com'}),
    new B.Model({name: 'baz', url: 'baz.com'}),
  ])
  ;

  var listView = new ListView({
    collection: collection, 
    trView: trView
  });

  test('Render', 1, function() {
    try {
      listView.render();
      ok(true, 'Render OK');
    } catch(e) {}
  });


  // El sorting no se usa
  // test('Sorting', 1, function() {

  //   collection.comparator = function(model) {
  //     return model.get('name');
  //   };

  //   collection.sort();
  //   var rows = listView.$('.item-row');

  //   ok(collection.every(function(model, i) {
  //     return model.get('name') === $(rows[i]).text();
  //   }), 'Sorting OK');

  // });

  // asyncTest('Navigation', 1, function() {
  //   $('body').append(listView.render().$el);

  //   var navigate = false;
  //   listView.on('navigate', function(url, dir) {
  //     navigate = url === collection.at(0).get('url') &&
  //                 dir === 1;
  //   });

  //   var $row = listView.$('.item-row').first();
  //   $row[0].dispatchEvent(MockTouch('start'));
  //   $row[0].dispatchEvent(MockTouch('end'));

  //   _.delay(function() {
  //     ok(navigate, 'Navigation OK');
  //     start();
  //   }, 50);
  // });


});