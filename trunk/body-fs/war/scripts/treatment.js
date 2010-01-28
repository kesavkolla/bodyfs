/**
 * This function setup the markers and corresponding events
 * 
 * @return
 */
function initMarkers() {
	$("#imgdiv").click(function(e) {
		var offset = $(this).offset();
		var left = e.pageX - offset.left - 15;
		var top = e.pageY - offset.top - 15;
		var marker = $("<img src='/img/push-pin.gif' class='marker' onClick='removeMarker(this)'>").css( {
			'left' : left,
			'top' : top
		});
		$(this).append(marker);
		e.stopPropagation();
		e.preventDefault();
	});
}

/**
 * This function removes the selected marker
 * 
 * @return
 */
function removeMarker(marker) {
	event.stopPropagation();
	event.preventDefault();
	$(marker).remove();
}

/**
 * This function serializes all the marker positions into a json string
 * 
 * @return
 */
function SaveMarkers() {
	var arr = new Array();
	$(".marker").each(function() {
		arr.push( {
			'left' : $(this).css("left"),
			'top' : $(this).css("top")
		});
	});
	$("$txtMarkers").val(toJSON(arr)).blur();
}