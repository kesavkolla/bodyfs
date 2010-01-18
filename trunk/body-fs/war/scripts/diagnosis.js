/**
 * This method gets executed after the zk.mount
 * 
 * @return
 */
function setupData() {
	/* Get the data from the jsondata textfield and setup all the input elements */
	var data = parseJSON($("$jsondata").val());
	if (isEmptyObject(data)) {
		return;
	}
	/* setup the tongue color */
	var objselTongueColor = $("#selTongueColor");
	if (data.TongueColor && $.isArray(data.TongueColor)) {
		$.each(data.TongueColor, function() {
			objselTongueColor.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	objselTongueColor.dropdownchecklist( {
		width : objselTongueColor.width()
	});

	/* setup the tongue coating */
	var selTongueCoating = $("#selTongueCoating");
	if (data.TongueCoating && $.isArray(data.TongueCoating)) {
		$.each(data.TongueCoating, function() {
			selTongueCoating.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selTongueCoating.dropdownchecklist( {
		width : selTongueCoating.width()
	});

	/* setup tongue wetness */
	var selTongueWetness = $("#selTongueWetness");
	if (data.TongueWetness && $.isArray(data.TongueWetness)) {
		$.each(data.TongueWetness, function() {
			selTongueWetness.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selTongueWetness.dropdownchecklist( {
		width : selTongueWetness.width()
	});

	/* setup tongue shape */
	var selTongueShape = $("#selTongueShape");
	if (data.TongueShape && $.isArray(data.TongueShape)) {
		$.each(data.TongueShape, function() {
			selTongueShape.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selTongueShape.dropdownchecklist( {
		width : selTongueShape.width()
	});

	/* setup veins underneath */
	var selVeinsUnderneath = $("#selVeinsUnderneath");
	if (data.VeinsUnderneath && $.isArray(data.VeinsUnderneath)) {
		$.each(data.VeinsUnderneath, function() {
			selVeinsUnderneath.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selVeinsUnderneath.dropdownchecklist( {
		width : selVeinsUnderneath.width()
	});

	/* setup pulse right1 */
	var selPulseRight1 = $("#selPulseRight1");
	if (data.PulseRight1 && $.isArray(data.PulseRight1)) {
		$.each(data.PulseRight1, function() {
			selPulseRight1.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selPulseRight1.dropdownchecklist( {
		width : selPulseRight1.width()
	});

	/* setup pulse right2 */
	var selPulseRight2 = $("#selPulseRight2");
	if (data.PulseRight2 && $.isArray(data.PulseRight2)) {
		$.each(data.PulseRight2, function() {
			selPulseRight2.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selPulseRight2.dropdownchecklist( {
		width : selPulseRight2.width()
	});

	/* setup PulseRight3 */
	var selPulseRight3 = $("#selPulseRight3");
	if (data.PulseRight3 && $.isArray(data.PulseRight3)) {
		$.each(data.PulseRight3, function() {
			selPulseRight3.find("option[value'" + this + "']").attr("selected", "selected");
		});
	}
	selPulseRight3.dropdownchecklist( {
		width : selPulseRight3.width()
	});

	/* setup PulseLeft1 */
	var selPulseLeft1 = $("#selPulseLeft1");
	if (data.PulseLeft1 && $.isArray(data.PulseLeft1)) {
		$.each(data.PulseLeft1, function() {
			selPulseLeft1.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selPulseLeft1.dropdownchecklist( {
		width : selPulseLeft1.width()
	});

	/* setup PulseLeft2 */
	var selPulseLeft2 = $("#selPulseLeft2");
	if (data.PulseLeft2 && $.isArray(data.PulseLeft2)) {
		$.each(data.PulseLeft2, function() {
			selPulseLeft2.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selPulseLeft2.dropdownchecklist( {
		width : selPulseLeft2.width()
	});

	/* setup PulseLeft3 */
	var selPulseLeft3 = $("#selPulseLeft3");
	if (data.PulseLeft3 && $.isArray(data.PulseLeft3)) {
		$.each(data.PulseLeft3, function() {
			selPulseLeft3.find("option[value='" + this + "']").attr("selected", "selected");
		});
	}
	selPulseLeft3.dropdownchecklist( {
		width : selPulseLeft3.width()
	});

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
				continue;
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
