define(['ui/topbarview', 'tpl!travelplanner/traveldetails.tpl'],
function(TopbarView, TravelDetailsTpl) {

  return B.View.extend({

    events: {
      'submit .traveldetails-form': function(e) {
        e.preventDefault();
        var uriObj = {
          startTime: this.$('[name="start-time"]').val(),
          endTime: this.$('[name="end-time"]').val(),
          transportation: this.$('[name="transportation-type"]:checked').val(),
        };
        this.trigger('updatequery', uriObj);

        this.trigger('navigate', '/travelplanner/travel?' +
          encodeURIComponent(JSON.stringify(uriObj)));
      }
    },

    className: 'pageview',

    initialize: function() {
      this.topbarView = new TopbarView({
        title: this.options.title
      });

      this.pass(this.topbarView, 'navigate');

      this.options = _.extend(_.clone(this.defaults), this.options);
    },

    defaults: {
      startTime: '09:00',
      endTime: '19:00',
      transportation: 'DRIVING'
    },

    render: function() {
      this.$el
        .html(this.topbarView.render().$el)
        .append(TravelDetailsTpl(this.options));

      this
        .$('[value="' + this.options.transportation + '"]')
        .prop('checked', true)
        .closest('.btn')
        .addClass('active');
      return this;
    }

  });
});