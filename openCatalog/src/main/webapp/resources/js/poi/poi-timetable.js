$(function(){
	
	var deleteTimeTableRow = function( btn ){
		$(btn).closest('tr').remove();
	}
	
	
	$('a.removeTimeTable').on('click', function(e){
		e.preventDefault();
		deleteTimeTableRow(this);
	});
	
	$('.addTimeTableEntry').bind('click', function(e){
		e && e.preventDefault();
		var newTr = $('table.timetable tr.rowTemplate').clone()
			.removeClass("rowTemplate")
			.appendTo("table.timetable > tbody");
		
		$('input', newTr).removeAttr('disabled');
		$('a', newTr).on('click', function(e){
			e.preventDefault();
			deleteTimeTableRow(this);
		})
		
		
	});
});