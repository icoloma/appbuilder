$(function(){
	
	// Disable form submit on Enter
	
	$("form :input").on("keypress", function(e) {
		if (this.type != "textarea" && !this.hasClass("search-query"))
			return (e.keyCode != 13);
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
	
	// delete button
	$("button.deleteButton").click(function(){
		var form = $($(this).closest('form'));
		$('input[name="_method"]', form).val("DELETE");
		
		// disble required for submit
		$('[required]', form).each(function(){
			$(this).removeAttr("required");
		});
	});
	
});

