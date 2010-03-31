function setupData() {
	if (data.length <= 0) {
		return;
	}

	/*
	 * Find the start parameter which matches the visitdate
	 */
	if (start.length <= 0) {
		start = data[0].date;
	}
	var selVisitDates = $("#selVisitDates");
	$.each(data, function() {
		selVisitDates.append('<option value="' + this.date + '" ' + ((start == this.date) ? "selected='true'" : "")
				+ '">' + this.value + '</option>');
	});

	/*
	 * Handle the click on the view button. Get the selected date and reload the data
	 */
	$("#btnView").click(function() {
		var selVisitDates = $("#selVisitDates")[0];
		var selDate = selVisitDates.options[selVisitDates.selectedIndex].value;
		var datepage = $("$datepage");
		datepage.val(selDate);
		datepage.blur();
		var btnArr = [ "$tbtnSignin", "$tbtnDiagnosis", "$tbtnTreatment", "$tbtnPrescription" ];
		$.each(btnArr, function(indx, val) {
			var wgt = zk.Widget.$($(val).attr("id"));
			wgt._href = $.param.querystring(wgt._href, "visitDate=" + selDate);
		});
	});

	/* setup annotations */
	var annonimage = jq("$imgbody").annotateImage( {
		editable : false,
		useAjax : false
	});

	/* if there are annotations setup the notes */
	var txtAnnotations = zk.Widget.$($("$txtAnnotations"));
	if (txtAnnotations.getValue().length > 0) {
		var notes = parseJSON(txtAnnotations.getValue());
		notes = $.map(notes, function(data, indx) {
			if (data.editable) {
				data.editable = false;
			}
			return data;
		});
		$("$imgbody").annotateImage("load", notes);
	}
}

/**
 * This will be called when ever the selection date changed. This will clear all the image notes and recreate the new
 * ones
 * 
 */
function redrawAnnotations() {
	$("$imgbody").annotateImage("clear");
	var txtAnnotations = zk.Widget.$($("$txtAnnotations").attr("id"));
	/* if there are annotations setup the notes */
	if (txtAnnotations.getValue().length > 0) {
		var notes = parseJSON(txtAnnotations.getValue());
		notes = $.map(notes, function(data, indx) {
			if (data.editable) {
				data.editable = false;
			}
			return data;
		});
		$("$imgbody").annotateImage("load", notes);
	}
}

/**
 * Navigates to the diagnosis page
 * 
 * @return
 */
function navigateNext() {
	var wgt = zk.Widget.$($("$tbtnDiagnosis").attr("id"));
	zUtl.go(wgt._href);
}