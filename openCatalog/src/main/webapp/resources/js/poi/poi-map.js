var map = $('#map_canvas');

$(function(){

	var mapInitialized = false;

	$('a[data-toggle="tab"]').on('shown', function (e) {
		// activated tab
		if (e.target.hash == "#geo" && ! mapInitialized) {
			showMap();
			mapInitialized = true;
		}
	})
	
	
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
	
function showMap() {
	map.gmap({ 
 		'center': map.data("coord"),
		'zoom': 17,
 		'callback':function() {
 			var self = this;
 	        self.addMarker({ 
 	    		'position':  map.data("coord"), 
 	        }); 
 		}
 	}).bind('init', function (event, map) { 
		$(map).click( function(event) {
			addMarker(event.latLng );	
			addAddress(event.latLng);
		});
		
	});

	map.gmap('autocomplete', 'gsearcher', function(ui) {
		addMarker(ui.item.position );	
		addAddress(ui.item.position);
	});
}
	
function addMarker(latLng){
	map.gmap('clear','markers');
	map.gmap('addMarker', { 'position' : latLng }, function(map, marker){
		map.setCenter(latLng);
	});
	
	$('input[name="location.lat"]').val(latLng.lat());
	$('input[name="location.lng"]').val(latLng.lng());
	
}
		
	