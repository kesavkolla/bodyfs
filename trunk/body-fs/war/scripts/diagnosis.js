/**
 * This method gets executed after the zk.mount
 * 
 * @return
 */
function setupData(reload) {
	if (reload) {
		clearFields();
	}
	/* Get the data from the jsondata textfield and setup all the input elements */
	var wgt = zk.Widget.$($("$jsondata").attr("id"));
	var data = {};
	if (isEmptyObject(wgt)) {
		data = {};
	} else {
		data = parseJSON(wgt.getValue());
	}

	/* setup the tongue color */
	var objselTongueColor = $("#selTongueColor");
	objselTongueColor.dropdownchecklist( {
		width : objselTongueColor.width()
	});
	if (data.TongueColor && $.isArray(data.TongueColor)) {
		$.each(data.TongueColor, function() {
			objselTongueColor.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup the tongue coating */
	var selTongueCoating = $("#selTongueCoating");
	selTongueCoating.dropdownchecklist( {
		width : selTongueCoating.width()
	});
	if (data.TongueCoating && $.isArray(data.TongueCoating)) {
		$.each(data.TongueCoating, function() {
			selTongueCoating.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup tongue wetness */
	var selTongueWetness = $("#selTongueWetness");
	selTongueWetness.dropdownchecklist( {
		width : selTongueWetness.width()
	});
	if (data.TongueWetness && $.isArray(data.TongueWetness)) {
		$.each(data.TongueWetness, function() {
			selTongueWetness.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup tongue shape */
	var selTongueShape = $("#selTongueShape");
	selTongueShape.dropdownchecklist( {
		width : selTongueShape.width()
	});
	if (data.TongueShape && $.isArray(data.TongueShape)) {
		$.each(data.TongueShape, function() {
			selTongueShape.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup veins underneath */
	var selVeinsUnderneath = $("#selVeinsUnderneath");
	selVeinsUnderneath.dropdownchecklist( {
		width : selVeinsUnderneath.width()
	});
	if (data.VeinsUnderneath && $.isArray(data.VeinsUnderneath)) {
		$.each(data.VeinsUnderneath, function() {
			selVeinsUnderneath.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup pulse right1 */
	var selPulseRight1 = $("#selPulseRight1");
	selPulseRight1.dropdownchecklist( {
		width : selPulseRight1.width()
	});
	if (data.PulseRight1 && $.isArray(data.PulseRight1)) {
		$.each(data.PulseRight1, function() {
			selPulseRight1.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup pulse right2 */
	var selPulseRight2 = $("#selPulseRight2");
	selPulseRight2.dropdownchecklist( {
		width : selPulseRight2.width()
	});
	if (data.PulseRight2 && $.isArray(data.PulseRight2)) {
		$.each(data.PulseRight2, function() {
			selPulseRight2.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup PulseRight3 */
	var selPulseRight3 = $("#selPulseRight3");
	selPulseRight3.dropdownchecklist( {
		width : selPulseRight3.width()
	});
	if (data.PulseRight3 && $.isArray(data.PulseRight3)) {
		$.each(data.PulseRight3, function() {
			selPulseRight3.find("option[value'" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup PulseLeft1 */
	var selPulseLeft1 = $("#selPulseLeft1");
	selPulseLeft1.dropdownchecklist( {
		width : selPulseLeft1.width()
	});
	if (data.PulseLeft1 && $.isArray(data.PulseLeft1)) {
		$.each(data.PulseLeft1, function() {
			selPulseLeft1.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup PulseLeft2 */
	var selPulseLeft2 = $("#selPulseLeft2");
	selPulseLeft2.dropdownchecklist( {
		width : selPulseLeft2.width()
	});
	if (data.PulseLeft2 && $.isArray(data.PulseLeft2)) {
		$.each(data.PulseLeft2, function() {
			selPulseLeft2.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
	}

	/* setup PulseLeft3 */
	var selPulseLeft3 = $("#selPulseLeft3");
	selPulseLeft3.dropdownchecklist( {
		width : selPulseLeft3.width()
	});
	if (data.PulseLeft3 && $.isArray(data.PulseLeft3)) {
		$.each(data.PulseLeft3, function() {
			selPulseLeft3.find("option[value='" + this + "']").attr("selected", "selected").change();
		});
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

	/* Handle the save button */
	$("$btnSave").click(function() {
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

	if (!reload) {
		setupPagination();
	} else {
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
	$("select>option").removeAttr("selected");
	$("option[value=-1]").attr("selected", "selected");
	/* For the dropdownchecklist resync the options and the textfield */
	$("select").each(function() {
		var element = $.data(this, "dropdownchecklist");
		if (isEmptyObject(element)) {
			return true;
		}
		element.dropWrapper.find("input:not([disabled]):not([value=-1])").removeAttr("checked");
		element._syncSelected();
	});
	$("$txtNotes").val("");
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
			var wgt = jq("$tbtnDiagnosis").zk.widget()[0];
			wgt._href = $.param.querystring(wgt._href, "visitDate=" + selectedObj.date);
			var wgt = jq("$tbtnTreatment").zk.widget()[0];
			wgt._href = $.param.querystring(wgt._href, "visitDate=" + selectedObj.date);
			var wgt = jq("$tbtnPrescription").zk.widget()[0];
			wgt._href = $.param.querystring(wgt._href, "visitDate=" + selectedObj.date);
		}
	});
}