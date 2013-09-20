/*
  Un mock del API de WebSQL
  TO-DO: 
  AVISO: 
         Solo funciona para transacciones simples.

*/
define(['globals'], function() {

  /*
    @options: un hash de opciones que se pasa a cada nueva trasacción. 
  */
  var DatabaseMock = function() {
    this.options = {};
  }

  /*
    @options: las opciones para ejecutar la transacción.

      @options.pre: un callback a ejecutar *antes* de hacer ninguna "llamada a BDD".
      
      @options.results: el resultado de la tx, por defecto un resultado vacío.

      @options.success: un callback opcional para llamar tras una tx exitosa.
   
      @options.error: un error opcional, que marca la tx como fallida.

      @options.failure: un callback opcional para después de una tx fallida.
  
  */
  , TransactionMock = function(options) {
    this.options = options || {};
  }


  /*
    @results: un array de resultados
  */
  , ResultsMock = function(results) {
    this.results = results;
    this.rows = {
      length: results.length,
      item: _.bind(item, this)
    };
  }

  // La función item del API WebSQL
  , item = function(i) {
    if (i < 0 || i > this.length) {
      throw new Error("No existe el item " + i);
    }

    // Simula el objeto devuelto por WebSQL, que es "read-only"
    var result = {};
    _.each(this.results[i], function(value, key) {
      Object.defineProperty(result, key, {
        value: value,
        writable: false,
        configurable: false
      });
    });
    
    return result;
  }
  ;

  TransactionMock.prototype = {
    executeSql: function(sqlStr, params, callback, errback) {
      if (this.options.pre) this.options.pre(sqlStr, params, callback, errback);

      if (this.options.error) {
        errback && errback(this, this.options.error);
        this.options.failure && this.options.failure();
      } else {
        callback && callback(this, new ResultsMock(this.options.results || []));
        this.options.success && this.options.success();
      }
    }
  };

  DatabaseMock.prototype = {
    transaction: function(callback) {
      callback(new TransactionMock(this.options));
    }
  };

  return {
    Database: DatabaseMock
  };
});