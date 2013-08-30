$(function(){
	
	var deleteRow = function( btn ){
		$(btn).closest('tr').remove();
	}
	
	
	$('a.removePrice').on('click', function(e){
		e.preventDefault();
		deletePriceRow(this);
	});
	
	$('select.data-option').each( function(){
		var select = $(this);
		var data = select.data("value");
		select.find("option[value='" + data + "']").attr("selected",true);
	});
	
	
	$('.addPrice').bind('click', function(e){
		e && e.preventDefault();
		$('table.prices tr.rowTemplate').clone()
			.removeClass("rowTemplate")
			.appendTo("table.prices > tbody");
		setPriceInputNames();
	});
	
	function setPriceInputNames(){
		$('table.prices tbody tr').each( function(it){
			var tr = $(this);
			var priceInput  = $('input',  tr.children()[0]).removeAttr('disabled').attr('name', 'prices[' + it + '].price');
			var selectInput = $('select', tr.children()[1]).removeAttr('disabled').attr('name', 'prices[' + it + '].priceType');
			var periodInput = $('input',  tr.children()[2]).removeAttr('disabled').attr('name', 'prices[' + it + '].timeTable.period');
			var deleteButton = $('a',  tr.children()[4]).on('click', function(e){
				e.preventDefault();
				deletePriceRow(this);
			})
			
		});
		
	}
	
	
	var deletePriceRow = function( btn ){
		$(btn).closest('tr').remove();
		setPriceInputNames();
	}
});