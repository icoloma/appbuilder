define(['ui/topbarview', 'tpl!travelplanner/traveldetails.tpl', 'modules/time'],
function(TopbarView, TravelDetailsTpl, Time) {

  return B.View.extend({

    events: {
      'submit .traveldetails-form': function(e) {
        e.preventDefault();

        // TO-DO: testear si iOS devuelve siempre HH:MM en 24h.
        var travelConfig = {
          startTime: this.$('[name="start-time"]').val(),
          endTime: this.$('[name="end-time"]').val(),
          transportation: this.$('[name="transportation-type"]:checked').val(),
        };

        if (Time.compare(travelConfig.startTime, travelConfig.endTime) >= 0) {
          navigator.notification.alert(res.i18n.timeLimitsErrorDialog, null,
                                        res.i18n.TimeLimitsError);
          return;
        }

        this.trigger('updatequery', travelConfig);

        this.trigger('navigate', '/travelplanner/travel?' +
          encodeURIComponent(JSON.stringify(travelConfig)));
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