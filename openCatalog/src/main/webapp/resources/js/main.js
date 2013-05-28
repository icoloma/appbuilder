$(function(){
	
	// Disable form submit on Enter
	$("form :input").on("keypress", function(e) {
	    return e.keyCode != 13;
	});
	
	// table.poi : add links to Pois
	$("table.poi tbody tr").each( function(){
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
	
});

