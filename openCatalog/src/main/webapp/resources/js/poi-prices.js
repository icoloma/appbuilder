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
			
			$('input.price',  tr).attr('name', 'prices[' + it + '].price').removeAttr('disabled');  // price 
			$('input.period', tr).attr('name', 'prices[' + it + '].timeTable.period').removeAttr('disabled'); // period
			$('select.priceType', tr).attr('name', 'prices[' + it + '].priceType').removeAttr('disabled'); // priceType

			$($('ul.nav-pills a', tr)[0]).attr("href","#price-es-" + it);
			$($('.tab-pane', tr)[0]).attr("id","price-es-" + it);
			
			$($('ul.nav-pills a', tr)[1]).attr("href","#price-en-" + it);
			$($('.tab-pane', tr)[1]).attr("id","price-en-" + it);
			
			$('textarea.es', tr).attr('name', 'prices[' + it + '].observations.es').removeAttr('disabled');
			$('textarea.en', tr).attr('name', 'prices[' + it + '].observations.en').removeAttr('disabled');
			
			var deleteButton = $('a.removePrice').on('click', function(e){
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