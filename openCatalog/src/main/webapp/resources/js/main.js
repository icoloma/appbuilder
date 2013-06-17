$(function(){
	
	// Disable form submit on Enter
	
	$("form :input").on("keypress", function(e) {
		if (this.type != "textarea" && !$(this).hasClass("search-query"))
			return (e.keyCode != 13);
	});
	
	// add links to data-links items
	$(".dataLink").each( function(){
		$(this).click( function(){
			window.location= $(this).data("link");
		} );

	});
	
	// pager
	$("ul.pager").each( function(){
		var page = $(this).data("page");
		var pages = $(this).data("pages");
		if (page<1){
			// disble prev button and link
			$("li.previous", this).addClass("disabled");
			$("li.previous a",this).attr("href","#").click(function(){return false;});
		}
		if (page >= pages -1){
			// disble last button and link
			$("li.next", this).addClass("disabled");
			$("li.next a",this).attr("href","#").click(function(){return false;});
		}
	});
	
	// delete button
	$("button.deleteButton").click(function(){
		var form = $($(this).closest('form'));
		$('input[name="_method"]', form).val("DELETE");
		
		// disble required for submit
		$('[required]', form).each(function(){
			$(this).removeAttr("required");
		});
	});
	
	// checkAll 
	$('#checkAll').click(function(){
		if ($("#checkAll").is(':checked')) {
            $("input[type=checkbox]").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
            });
        }	
			
	});
	
	
	addAddress = function (latLng){
		$('#map_canvas').gmap('search', {'location': latLng}, function(results, status) {
			if ( status === 'OK' ) {
				processAddressComponents(results[0].address_components);
			}	
		});
	};
	
	processAddressComponents = function( address_components ){
		
		var street_number = '';
		var route = '';
		
		$.each(address_components, function(i,v) {
			if ( v.types[0] == "administrative_area_level_1" ) {
				$('#adminArea1').val(v.long_name);
			} else if ( v.types[0] == "administrative_area_level_2" ) {
				$('#adminArea2').val(v.long_name);
			} else if ( v.types[0] == "route") {
				route = v.long_name;
			} else if ( v.types[0] == "street_number") {
				street_number = ', ' + v.long_name;
			} else if ( v.types[0] == "postal_code") {
				$('#zipCode').val(v.long_name);
			}
		});
		$('#route').val(route + street_number);
		
	};
	
});

