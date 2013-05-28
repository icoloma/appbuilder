$(function(){
	
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


// extending jQuery
$.fn.disableForm = function() {
	return this.each(function() {
		if ($(this).is("form ")) {
			$("input,checkbox,textarea,radio,button", this).each( function(){ this.disabled = true;});
			$(".saveButton",this).hide();
			$(".editButton",this).show().attr("disabled",false);
		}
	});
};

$.fn.enableForm = function() {
	return this.each(function() {
		if ($(this).is("form ")) {
			$("input,checkbox,textarea,radio,button", this).each( function(){ this.disabled = false;});
			$(".saveButton", this).show();
			$(".editButton", this).hide();
		}
	});
};

$.fn.prepareForm = function() {
	return this.each(function() {
		var form = $(this);
		if (form.is("form ")) {
			if (form.data("id")){
				form.disableForm();
				$(".editButton", form ).click( function() {
					
					form.enableForm();
					return false;
				});
			} else {
				$(".editButton", form ).hide();
			}
			
			
		}
	});
};
