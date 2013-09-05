		$(function(){

			var addToDiv = function( elem, divId ){
				var div = getDiv(divId);
				if (elem.is("div")){
					$(elem).appendTo(div.find(".group-base")[0]);
				} else {
					$(elem).appendTo(div.find(".group-sub")[0]);
				}
			}

			var getDiv = function (divId){
				console.log("looking for " + divId);
				var div = $("#" + divId);
				if (div.length == 0){
					div = createDiv(divId);
				} else {
					console.log("found " + divId)
				}
				return div;
			}

			var createDiv = function (divId){
				console.log("creating " + divId);
				var parent = findParent(divId);
				var sub = $(parent).find(".group-sub")[0]; 
				var div = $("<div/>").attr("id", divId).appendTo(sub);
				var header = getHeader(divId).appendTo(div);

				// add base div
				$("<div/>").addClass("group-base").appendTo(div);

				// add sub div
				$("<div/>").addClass("group-sub").appendTo(div);
				return div;
			}

			var findParent = function( divId ) {

				if (divId.indexOf("_") == -1 ){
					return $("div#poiData");
				}

				var parentId = divId.substr(0, divId.lastIndexOf("_"));

				var div = $("#"+parentId);

				if (div.length > 0 ){
					return div;
				}

				// No existe parent
				return createDiv(parentId);
			}

			var getHeader = function(divId){
				var deep = divId.split("_").length + 2;
				return $("<h" + deep + "/>").text(getHeaderText(divId));
			}

			var getHeaderText = function(divId){
				var path = divId.substr(6);
				var div = $("div.poiData[data-path='" + path +"']");
				if (div.length > 0){
					return div.data("tpath")
				}
				return "";
			}

			var poiDataDiv = $("div#poiData");
			var poiData = $('div.poiData');

			poiData.each( function(){
				var that = $(this);
				var path = that.data("path");
				console.log("processing " + path);
				addToDiv(that, path.length == 0 ? "poiData": "group-" + path);
			});


		})