/*
  Vista del plan de viaje
*/
define(['ui/topbarview', 'travelplanner/planner', 'list/sortablelistview',
        'tpl!poi/plannertrview.tpl', 'travelplanner/travelstepmodel', 'poi/collection', 'db/db'],
  function(TopbarView, Planner, ListView, TrView, StepModel, PoiCollection, Db) {
  return B.View.extend({

    className: 'pageview',

    initialize: function() {
      var self = this;

      /*
        Top Bar
      */
      this.topbarView = new TopbarView({
        title: this.options.title,
        actions: ['sort'],
        isBlocked: true
      });

      this.pass(this.topbarView, 'navigate');
      this.listenTo(this.topbarView, 'sort', this.startSorting);
      this.listenTo(this.topbarView, 'ok', this.endSorting);

      /*
        Sortable list
      */

      // this.collection no está disponible hasta ejecutar this.initPlan()
      this.collectionView = new ListView({
        className: 'poisplanview collectionview sortablecollectionview',
        trView: TrView,
        collection: new B.Collection([])
      });

      this.listenTo(this.collectionView, 'sorted', function() {
        self.updatingRoute = true;
        self.updateRouteQueue.push({});
      });


      /*
        Cálcula y muestra el plan de viaje inicial
      */
      this.initPlan();


      /*
        Una cola para la actualización de la ruta.

        Cuando la lista de POIs cambie de orden, se encola una llamada a BDD para reflejarlo
      */
      this.updateRouteQueue = async.queue(function(task, cb) {
        self.updateRoute(cb);
      });

      // Cuando se vacía la cola, se recalcula el plan de viaje.
      this.updateRouteQueue.drain = function() {
        self.updatingRoute = false;
        self.updatePlan();
      };
    },

    render: function() {
      this.$el.html(this.topbarView.render().$el);

      // Arregla la pérdida de listeners en el re-render
      this.topbarView.delegateEvents();

      this.$el.append(this.collectionView.render().$el);

      return this;
    },

    // Activar/desactivar el modo "sorting" de la lista ordenable

    startSorting: function() {
      this.topbarView.unblock().updateActions(['ok']);
      this.collectionView.startSorting();
    },

    endSorting: function() {
      this.collectionView.endSorting();
      this.topbarView.unblock().updateActions(['sort']);
    },

    // Actualiza el orden de los POIs favoritos en la BDD
    updateRoute: function(callback) {
      var starredCounter = Number(localStorage.getItem('starredCounter'))
      , poisToUpdate = []
      ;

      // Adapta el valor de 'starred' para que el orden de de la collection sea consistente
      _.each(this.collection.models.slice(0, -1), function(model, i, models) {
        var starred1 = model.get('poi').get('starred')
        , starred2 = this.collection.models[i+1].get('poi').get('starred')
        ;
        if (starred1 >= starred2) {
          this.collection.models[i+1].get('poi').set('starred', starredCounter++);
          localStorage.setItem('starredCounter', starredCounter);
          poisToUpdate.push(this.collection.models[i+1].get('poi'));
        }
      }, this);

      // Se actualizan todos los POIs en una sola transacción
      if (!poisToUpdate.length) return callback(null);

      Db.transaction(function(tx) {
        _.each(poisToUpdate, function(poi) {
          tx.executeSql('UPDATE Poi SET starred=? WHERE id=?', [poi.get('starred'), poi.get('id')]);
        });
      }, callback, callback);
    },

    // Recalcula el plan de viaje.
    // Utiliza los POIs de this.collection, por lo que se le llama y se usan sus resultados
    // solo cuando el fla this.updatingRoute sea false 
    updatePlan: function() {
      var self = this 
      , pois = new PoiCollection(this.collection.map(function(step) {
        return step.get('poi');
      }))
      ;

      Planner.getPoisTravelPlan(pois, this.options, function(err, plan) {
        // TO-DO: error handling
        if (err) return;

        if (self.updatingRoute) return;

      });
    },

    // Calcula y muestra el plan de viaje inicial
    initPlan: function() {
      var self = this;

      Planner.getTravelPlan(this.options, function(err, plan) {
        // TO-DO: error handling
        if (err) console.log(err);
        self.topbarView.unblock();

        // Todos los días del plan juntos
        var planArray = Array.prototype.concat.apply(plan[0], plan.slice(1))
        // Hay que pasar una Collection para el ListView.render
        , collection = new B.Collection(_.map(planArray, function(step) {
          return new StepModel(step);
        }))
        ;

        self.collection = self.collectionView.collection = collection;

        self.collectionView.render();
      });
    }

  });
});