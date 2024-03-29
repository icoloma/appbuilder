/*
  Vista del plan de viaje
*/
define(['ui/topbarview', 'travelplanner/planner', 'list/sortablelistview',
        'tpl!travelplanner/trview.tpl', 'travelplanner/travelstepmodel', 'poi/collection', 'db/db'],
  function(TopbarView, Planner, ListView, TrView, StepModel, PoiCollection, Db) {
  return B.View.extend({

    className: 'pageview',



    /*
      Flags:

        - this.updatingRoute: indica si se está actualizando el orden de los POIs
        - this.routeDetailsAvailable: indica si los datos de la ruta están disponibles para mostrar al usuario
    */

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

      // Respuesta a un cambio en el orden de los POIs
      this.listenTo(this.collectionView, 'sorted', function() {

        // Si aún se está mostrando los detalles de la ruta, se borran.
        if (self.routeDetailsAvailable) {
          self.clearRouteDetails();
        }

        // Encola una actualización de la BDD
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
      this.updateRouteQueue.drain = _.bind(this.updatePlan, this);
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

    clearRouteDetails: function() {
      this.routeDetailsAvailable = false;

      this.collection.forEach(function(step) {
        step.set({
          date: null,
          startTime: null,
          travel: {
            distance: null,
            duration: null,
            approx: false
          }
        });
      });

      this.routeDetailsAvailable = false;

      this.collectionView.render();
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
      this.updatingRoute = false;

      var self = this 
      , pois = new PoiCollection(this.collection.map(function(step) {
        return step.get('poi');
      }))
      ;

      Planner.getPoisTravelPlan(pois, this.options, function(err, plan) {
        // TO-DO: error handling
        if (err) return;

        if (self.updatingRoute) return;

        var collection = new B.Collection(_.map(plan, function(step) {
          return new StepModel(step);
        }));

        self.routeDetailsAvailable = true;

        self.collectionView.collection = self.collection = collection;
        self.collectionView.render();

      });
    },

    // Calcula y muestra el plan de viaje inicial
    initPlan: function() {
      var self = this;

      Planner.getTravelPlan(this.options, function(err, plan) {
        // TO-DO: error handling
        if (err) console.log(err);
        self.topbarView.unblock();


        // Hay que pasar una Collection para el ListView.render
        var collection = new B.Collection(_.map(plan, function(step) {
          return new StepModel(step);
        }));

        self.routeDetailsAvailable = true;

        self.collection = self.collectionView.collection = collection;

        self.collectionView.render();
      });
    }

  });
});