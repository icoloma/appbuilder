$(function(){
	
	var showMessage = function( level, message){
		var alertDiv = '<div class="alert alert-' + level + '"><button type="button" class="close" data-dismiss="alert">x</button><span>'+message+'</span></div>';
		$(alertDiv).appendTo('div#alerts');
	};
	
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
	
	// submitButton
	$('input.saveButton').click( function(e){
		var form = $(this).parents("form:first")[0];
		var isValid = form.checkValidity();
		if (!isValid) {
			// muestra el tab que contiene el error
			var tabId = $($(':invalid')[0]).closest('.tab-pane').attr('id');
			var tab = $( 'a[href$="' + tabId + '"]');
			tab && tab.trigger('click');
		}
	});
	
	
	
});

