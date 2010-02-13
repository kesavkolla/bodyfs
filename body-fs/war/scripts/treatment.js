/**
 * This function initializes all the scripts for this page. This is called after
 * zk's mount is finished
 * 
 * @return
 */
function setupPage() {
	if (!readonly) {
		initMarkers();
	}
	setupPagination();
	reloadMarkers(true);
}

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
 * Reload the markers from the data from the server
 * 
 * @return
 */
function reloadMarkers(nogrowl) {
	$(".marker").remove();
	var wgt = zk.Widget.$($("$txtMarkers").attr("id"));
	var markerData = [];
	if (!isEmptyObject(wgt) && wgt.getValue() != "") {
		markerData = parseJSON(wgt.getValue());
	}
	var imgdiv = $("#imgdiv");
	/* Loop through each point and add marker point */
	$.each(markerData, function() {
		var marker = null;
		if (readonly) {
			marker = $("<img src='/img/push-pin.gif' class='marker'>").css(this);
		} else {
			marker = $("<img src='/img/push-pin.gif' class='marker' onClick='removeMarker(this)'>").css(this);
		}
		imgdiv.append(marker);
	});
	if (!nogrowl) {
		$.jGrowl("Loaded data for the selected visitdate", {
			life : 2000
		});
	}
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

/**
 * This function setup the pagination
 * 
 * @return
 */
function setupPagination() {
	if (!$.isArray(data)) {
		return;
	}
	if (data.length < 1) {
		return;
	}
	/* Find the start parameter which matches the visitdate */
	if (start.length <= 0) {
		start = 1;
	} else {
		for ( var i = 0, len = data.length; i < len; i++) {
			if (start == data[i].date) {
				start = i + 1;
				break;
			}
		}
	}
	$("$Pagination").paginate( {
		count : data.length,
		start : start,
		display : 8,
		border : true,
		border_color : '#000',
		text_color : '#000',
		background_color : '#fff',
		border_hover_color : '#ccc',
		text_hover_color : '#fff',
		background_hover_color : '#000',
		images : false,
		mouse : 'press',
		data : data,
		onChange : function(textValue, selectedObj) {
			jq("$txtVisitDates").val(selectedObj.date);
			jq("$txtVisitDates").blur();
			var wgt = zk.Widget.$(jq("$tbtnDiagnosis").attr("id"));
			wgt._href = $.param.querystring(wgt._href, "visitDate=" + selectedObj.date);
			var wgt = zk.Widget(jq("$tbtnTreatment").attr("id"));
			wgt._href = $.param.querystring(wgt._href, "visitDate=" + selectedObj.date);
			var wgt = zk.Widget(jq("$tbtnPrescription").attr("id"));
			wgt._href = $.param.querystring(wgt._href, "visitDate=" + selectedObj.date);
		}
	});
	$("$Pagination").find("ul").parent().css("display", "inline");
}