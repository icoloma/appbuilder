$(function(){
	
	var deleteUserZoneRow = function( btn ){
		$(btn).closest('tr').remove();
		setZoneInputNames();
	}
	
	
	$('a.removeZone').on('click', function(e){
		e && e.preventDefault();
		deleteUserZoneRow(this);
	});
	
	$('.addUserZone').bind('click', function(e){
		e && e.preventDefault();
		var id = $('#addZone').val();
		var name = $('#addZone option:selected').text();
		var link = $('table.zones').data('link') + id;
		var tr = $('<tr/>').appendTo("table.zones > tbody");
		var td1 = $('<td/>').appendTo(tr);
		var span = $('<span/>')
					.addClass('dataLink')
					.click( function(){window.location= link;})
					.attr('data-link',link)
					.html(name).appendTo(td1);
		var input = $('<input/>').attr("type","hidden").attr("value", id).appendTo(td1).attr("class","idZone");
		
		var td2 = $('<td/>').appendTo(tr);
		td2.append('<a href="#" class="btn btn-danger removeZone"><i class="icon-trash icon-white"></i></a></td>')
		
		var deleteButton = $('a.removeZone').on('click', function(e){
			e.preventDefault();
			deleteUserZoneRow(this);
		})
		
		setZoneInputNames();

	});
	
	
	function setZoneInputNames(){
		$('table.zones tbody tr').each( function(it){
			var tr = $(this);
			$('input.idZone',  tr).attr('name', 'idZones[' + it + ']'); 
		});
	}
});