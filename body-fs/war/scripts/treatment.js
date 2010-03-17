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
	initPage();
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
	if (!$.isEmptyObject(wgt) && wgt.getValue() != "") {
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
function initPage() {
	if (!$.isArray(data)) {
		return;
	}
	if (data.length < 1) {
		return;
	}

	/*
	 * Find the start parameter which matches the visitdate
	 */
	if (start.length <= 0) {
		start = data[0].date;
	}

	/* Add all the dates to the dropdown box */
	var selVisitDates = $("#selVisitDates");
	$.each(data, function() {
		selVisitDates.append('<option value="' + this.date + '" ' + ((start == this.date) ? "selected='true'" : "")
				+ '">' + this.value + '</option>');
	});

	/* Handle the click on View */
	$("#btnView").click(function() {
		var selVisitDates = $("#selVisitDates")[0];
		var selDate = selVisitDates.options[selVisitDates.selectedIndex].value;
		$("$txtVisitDates").val(selDate).blur();
		var btnArr = [ "$tbtnSignin", "$tbtnDiagnosis", "$tbtnTreatment", "$tbtnPrescription" ];
		$.each(btnArr, function(indx, val) {
			var wgt = zk.Widget.$($(val).attr("id"));
			wgt._href = $.param.querystring(wgt._href, "visitDate=" + selDate);
		});
	});

	/*
	 * Handle the click on prev/next
	 */
	$(".submitbtn").click(function() {
		SaveMarkers();
	});
}

/**
 * This function redirects to the next/prev page
 * 
 * @param direction
 * @return
 */
function navigate(direction) {
	if (direction == "Next") {
		var wgt = zk.Widget.$($("$tbtnPrescription").attr("id"));
		zUtl.go(wgt._href);
	} else {
		var wgt = zk.Widget.$($("$tbtnDiagnosis").attr("id"));
		zUtl.go(wgt._href);
	}
}