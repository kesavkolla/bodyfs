function setupData() {
	if (data.length <= 0) {
		return;
	}

	/*
	 * Find the start parameter which matches the visitdate
	 */
	if (start.length <= 0) {
		start = data[0];
	}
	var selVisitDates = $("#selVisitDates");
	$.each(data, function() {
		selVisitDates.append('<option value="' + this.date + '" '
				+ ((start.date == this.date) ? "selected='true'" : "") + '">' + this.value + '</option>');
	});

	/*
	 * Handle the click on the view button. Get the selected date and reload the
	 * data
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

	jq("$imgbody").annotateImage( {
		editable : true,
		useAjax : false
	});
}

/**
 * Navigates to the diagnosis page
 * @return
 */
function navigateNext() {
	var wgt = zk.Widget.$($("$tbtnDiagnosis").attr("id"));
	zUtl.go(wgt._href);
}