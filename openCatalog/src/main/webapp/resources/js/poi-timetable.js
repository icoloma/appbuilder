$(function(){
	
	var deleteRow = function( btn ){
		$(btn).closest('tr').remove();
	}
	
	
	$('a.remove').on('click', function(e){
		e.preventDefault();
		deleteRow(this);
	});
	
	
	
	$('.add').bind('click', function(e){
		e && e.preventDefault();
		
		var tr = $('<tr/>').appendTo("table.timetable > tbody");
		var td1 = $('<td/>').appendTo(tr);
		var td2 = $('<td/>').appendTo(tr);
		var a = $('<a href="#" class="btn btn-danger remove"><i class="icon-trash icon-white" ></i></a>').on('click', function(e){
			e.preventDefault();
			deleteRow(this);
		}).appendTo(td2);
		
		var input = $('<input/>', {
			type: 'text',
			name: 'timetable',
			pattern: $('table.timetable').data("pattern"),
			'required': 'required',
			class:'input-xxlarge'
		}).appendTo(td1);
		
		
		
	});
});