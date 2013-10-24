define(['ui/topbarview', 'tpl!travelplanner/traveldetails.tpl'],
function(TopbarView, TravelDetailsTpl) {

  return B.View.extend({

    events: {
      'submit .traveldetails-form': function(e) {
        e.preventDefault();

        // TO-DO: testear si iOS y el futurible plugin de Android devuelven
        // la fecha y hora en los formatos adecuados: YYYY-MM-DD, HH:mm
        var travelConfig = {
          startDate: this.$('[name="start-date"]').val(),
          startTime: this.$('[name="start-time"]').val(),
          endTime: this.$('[name="end-time"]').val(),
          transportation: this.$('[name="transportation-type"]:checked').val(),
        };

        if (
          moment.utc(travelConfig.startDate + 'T' + travelConfig.startTime) >
          moment.utc(travelConfig.startDate + 'T' + travelConfig.endTime)
        ) {
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
      startDate: moment().format('YYYY-MM-DD'),
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