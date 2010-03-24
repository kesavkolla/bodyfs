/**
 * This method gets executed after the zk.mount
 * 
 * @return
 */
function setupData(reload) {
	if (reload) {
		clearFields();
	} else {
		initPage();
	}
	/* Get the data from the jsondata textfield and setup all the input elements */
	var wgt = zk.Widget.$($("$jsondata").attr("id"));
	var data = {};
	if ($.isEmptyObject(wgt)) {
		data = {};
	} else {
		data = parseJSON(wgt.getValue());
	}

	/* setup the tongue color */
	var objselTongueColor = $("#selTongueColor");
	objselTongueColor.dropdownchecklist( {
		width : objselTongueColor.width()
	});
	if (data.TongueColor) {
		if ($.isArray(data.TongueColor)) {
			$.each(data.TongueColor, function() {
				objselTongueColor.find("option[value='" + this + "']").attr("selected", "selected").change();
			});
		} else {
			objselTongueColor.find("option[value='" + data.TongueColor + "']").attr("selected", "selected").change();
		}
	}

	/* setup the tongue coating */
	var selTongueCoating = $("#selTongueCoating");
	selTongueCoating.dropdownchecklist( {
		width : selTongueCoating.width()
	});
	if (data.TongueCoating) {
		if ($.isArray(data.TongueCoating)) {
			$.each(data.TongueCoating, function() {
				selTongueCoating.find("option[value='" + this + "']").attr("selected", "selected").change();
			});
		} else {
			selTongueCoating.find("option[value='" + data.TongueCoating + "']").attr("selected", "selected").change();
		}
	}

	/* setup tongue wetness */
	var selTongueWetness = $("#selTongueWetness");
	selTongueWetness.dropdownchecklist( {
		width : selTongueWetness.width()
	});
	if (data.TongueWetness) {
		if ($.isArray(data.TongueWetness)) {
			$.each(data.TongueWetness, function() {
				selTongueWetness.find("option[value='" + this + "']").attr("selected", "selected").change();
			});
		} else {
			selTongueWetness.find("option[value='" + data.TongueWetness + "']").attr("selected", "selected").change();
		}
	}

	/* setup tongue shape */
	var selTongueShape = $("#selTongueShape");
	selTongueShape.dropdownchecklist( {
		width : selTongueShape.width()
	});
	if (data.TongueShape) {
		if ($.isArray(data.TongueShape)) {
			$.each(data.TongueShape, function() {
				selTongueShape.find("option[value='" + this + "']").attr("selected", "selected").change();
			});
		} else {
			selTongueShape.find("option[value='" + data.TongueShape + "']").attr("selected", "selected").change();
		}
	}

	/* setup veins underneath */
	var selVeinsUnderneath = $("#selVeinsUnderneath");
	selVeinsUnderneath.dropdownchecklist( {
		width : selVeinsUnderneath.width()
	});
	if (data.VeinsUnderneath) {
		if ($.isArray(data.VeinsUnderneath)) {
			$.each(data.VeinsUnderneath, function() {
				selVeinsUnderneath.find("option[value='" + this + "']").attr("selected", "selected").change();
			});
		} else {
			selVeinsUnderneath.find("option[value='" + data.VeinsUnderneath + "']").attr("selected", "selected").change();
		}
	}

	/* setup pulse right1 */
	var selPulseRight1 = $("#selPulseRight1");
	selPulseRight1.dropdownchecklist( {
		width : selPulseRight1.width()
	});
	if (data.PulseRight1) {
		selPulseRight1.find("option[value='" + data.PulseRight1 + "']").attr("selected", "selected").change();
		selPulseRight1.data().dropdownchecklist.dropWrapper.find(":radio:checked").click();
	}

	/* setup pulse right2 */
	var selPulseRight2 = $("#selPulseRight2");
	selPulseRight2.dropdownchecklist( {
		width : selPulseRight2.width()
	});
	if (data.PulseRight2) {
		selPulseRight2.find("option[value='" + data.PulseRight2 + "']").attr("selected", "selected").change();
		selPulseRight2.data().dropdownchecklist.dropWrapper.find(":radio:checked").click();
	}

	/* setup PulseRight3 */
	var selPulseRight3 = $("#selPulseRight3");
	selPulseRight3.dropdownchecklist( {
		width : selPulseRight3.width()
	});
	if (data.PulseRight3) {
		selPulseRight3.find("option[value='" + data.PulseRight3 + "']").attr("selected", "selected").change();
		selPulseRight3.data().dropdownchecklist.dropWrapper.find(":radio:checked").click();
	}

	/* setup PulseLeft1 */
	var selPulseLeft1 = $("#selPulseLeft1");
	selPulseLeft1.dropdownchecklist( {
		width : selPulseLeft1.width()
	});
	if (data.PulseLeft1) {
		selPulseLeft1.find("option[value='" + data.PulseLeft1 + "']").attr("selected", "selected").change();
		selPulseLeft1.data().dropdownchecklist.dropWrapper.find(":radio:checked").click();
	}

	/* setup PulseLeft2 */
	var selPulseLeft2 = $("#selPulseLeft2");
	selPulseLeft2.dropdownchecklist( {
		width : selPulseLeft2.width()
	});
	if (data.PulseLeft2) {
		selPulseLeft2.find("option[value='" + data.PulseLeft2 + "']").attr("selected", "selected").change();
		selPulseLeft2.data().dropdownchecklist.dropWrapper.find(":radio:checked").click();
	}

	/* setup PulseLeft3 */
	var selPulseLeft3 = $("#selPulseLeft3");
	selPulseLeft3.dropdownchecklist( {
		width : selPulseLeft3.width()
	});
	if (data.PulseLeft3) {
		selPulseLeft3.find("option[value='" + data.PulseLeft3 + "']").attr("selected", "selected").change();
		selPulseLeft3.data().dropdownchecklist.dropWrapper.find(":radio:checked").click();
	}

	/* setup notes */
	if (data.Notes) {
		$("$txtNotes").val(data.Notes);
	}

	/* setup radio buttons */
	$(":radio[name^='rad']").each(function() {
		var name = $(this).attr("name").substring(3);
		if (data[name]) {
			if ($(this).val() == data[name]) {
				$(this).attr("checked", "checked");
			}
		}
	});
	
	if (reload) {
		$.jGrowl("Loaded data for the selected visitdate", {life:2000});
	}
}

/**
 * This fuction clears all the fields selection
 * 
 * @return
 */
function clearFields() {
	/* Remove all the radio buttons checked attribute */
	$(":radio:not([value=-1])").removeAttr("checked");
	/* Remove all the select box options selected attribute */
	$("select:not(#selVisitDates)>option").removeAttr("selected");
	$("option[value=-1]").attr("selected", "selected");
	/* For the dropdownchecklist resync the options and the textfield */
	$("select").each(function() {
		var element = $.data(this, "dropdownchecklist");
		if ($.isEmptyObject(element)) {
			return true;
		}
		element.dropWrapper.find("input:not([disabled]):not([value=-1])").removeAttr("checked");
		element._syncSelected();
	});
	$("$txtNotes").val("");
}

/**
 * This function initializes the page events
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
		selVisitDates.append('<option value="' + this.date + '" '
				+ ((start == this.date) ? "selected='true'" : "") + '">' + this.value + '</option>');
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
	
	/* Handle click on copy button */
	$("#btnCopy").click(function() {
		var selVisitDates = $("#selVisitDates")[0];
		var selDate = selVisitDates.options[selVisitDates.selectedIndex].value;
		$("$txtVisitDatesCopy").val(selDate).blur();		
	});

	/* Handle the save button */
	$(".submitbtn").click(function() {
		/* Get all the form elements those are filled */
		var elements = $(":input").not(":radio[value=-1]").serializeArray();
		/* Convert the elements array to single object */
		var o = {};
		$.each(elements, function() {
			var name = this.name.substring(3);
			if (this.value == "") {
				return true;
			}
			if (o[name]) {
				if (!o[name].push) {
					o[name] = [ o[name] ];
				}
				o[name].push(this.value || '');
			} else {
				o[name] = this.value || '';
			}
		});
		/* Set the value to the jsondata textfield */
		$("$jsondata").val(toJSON(o));
		/* invoke blur so that the data will be sent to server */
		$("$jsondata").blur();
	});
}

/**
 * This function redirects to the next/prev page
 * 
 * @param direction
 * @return
 */
function navigate(direction) {
	if(direction == "Next") {
		var wgt = zk.Widget.$($("$tbtnTreatment").attr("id"));
		zUtl.go(wgt._href);
	} else {
		var wgt = zk.Widget.$($("$tbtnSignin").attr("id"));
		zUtl.go(wgt._href);
	}
}