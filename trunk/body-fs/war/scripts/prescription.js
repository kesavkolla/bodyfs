function initJS() {
	$("#btnAdd").click(AddPortion);
	$("#btnHAdd").click(AddHPortion);
	$(".imgDelete").live("click", DeleteRow);
	$.data(document.body, "loadedformulas", new Array());
	initPage();
	loadData(false);
}

/**
 * This function loads the data from the textfields to the grids
 * 
 * @return
 */
function loadData(growl) {
	/* Get the prescription herbs data and populate the table */
	var txtPrescription = zk.Widget.$($("$txtPrescription").attr("id"));
	var arrHerbs = parseJSON(txtPrescription.getValue());
	$("#herbsTable > tbody > tr:not(:last):not(:first)").remove();
	$.each(arrHerbs, function() {
		$("#herbsTable > tbody > tr:last").before(
				"<tr><td class='bordercell'>"
						+ this.formula
						+ "</td><td class='bordercell'>"
						+ this.herb
						+ "</td><td class='bordercell'><input type='text' class='portion' value='"
						+ this.portion
						+ "'/></td>&nbsp;"
						+ ((readonly) ? "<td></td></tr>"
								: "<td><img src='/img/delete.png' class='imgDelete'/></td></tr>"));
	});

	/* populate the checkboxes */
	var arrmedType = $("$txtmedType").val().split(",");

	$("input[name|='chk']").removeAttr("checked");
	if (arrmedType.length > 0) {
		$.each(arrmedType, function(indx, value) {
			/* If the value is Herbal Treatment check the chkHerb */
			if (value == "Herbal Treatment") {
				$("input[name='chkHerb']").attr("checked", "checked");
			} else {
				/*
				 * Find the checkbox with the name that matches with chkName and
				 * make it selected
				 */
				$("input[name='chk" + value + "']").attr("checked", "checked");
			}
		});
	}
	if (growl) {
		$.jGrowl("Loaded data for the selected visitdate", {
			life : 2000
		});
	}
}

/**
 * This function setup the pagination and other initialization tasks
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

	/* handle click on done and prev button and save the values */
	$(".submitbtn").click(function() {
		var prescArr = new Array();
		/*
		 * Get all the table rows and prepare an object with formula, herb &
		 * portion
		 */
		$("#herbsTable > tbody > tr:not(:last):not(:first)").each(function(indx, row) {
			var obj = {
				"formula" : this.cells[0].innerHTML,
				"herb" : this.cells[1].innerHTML,
				"portion" : $(this.cells[2]).find("input").val()
			};
			prescArr.push(obj);
		});
		$("$txtPrescription").val($.toJSON(prescArr)).blur();
	});

	/* Handle click on other checkbox to hide/show textbox for other */
	$("input[name|='chk']").click(function() {
		var txtmedType = $("$txtmedType");
		var arrmedType = txtmedType.val().split(",");

		/* If the checkbox is checked then add it's value to the txtmedType */
		if ($(this).attr("checked") != "") {
			/* if this value is already in the array then nothing to do */
			if ($.inArray($(this).val(), arrmedType) != -1) {
				return;
			} else {
				/* add the value to the array */
				arrmedType[arrmedType.length] = $(this).val();
			}
		} else {
			/* Handle the uncheck */
			/* If the value is not already in the array nothing to do */
			if ($.inArray($(this).val(), arrmedType) == -1) {
				return;
			} else {
				var removeItem = $(this).val();
				/* remove this item from the arrmedType */
				arrmedType = $.map(arrmedType, function(item) {
					if (item == removeItem) {
						return null;
					} else {
						return item;
					}
				});
			}
		}
		txtmedType.val(arrmedType.join(","));
		txtmedType.blur();
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
}

/**
 * This function display the selected herbs
 * 
 * @param data
 * @return
 */
function DisplayData(data) {
	$("$divHerbs").html(data.herbs.map(function(herb) {
		return "<span style='width:100%;border:1px solid black'>" + herb.name + "</span>";
	}).join("<br />"));
	$.data(document.body, "formuladata", data);
}

/**
 * This function gets triggerd when user clicks on add portion on the top
 * 
 * @return
 */
function AddPortion() {
	var formuladata = $.data(document.body, "formuladata");

	var portion = $.trim($("$txtPortion").val());
	if (portion.length <= 0) {
		alert("Please enter the portion");
		return;
	}

	for ( var i = 0, len = formuladata.herbs.length; i < len; i++) {
		$("#herbsTable > tbody > tr:last").before(
				"<tr><td class='bordercell'>" + formuladata.formula.name + "</td>" + "<td class='bordercell'>"
						+ formuladata.herbs[i].name + "</td>"
						+ "<td class='bordercell'><input type='text' class='portion' value='" + portion
						+ "' /></td><td><img src='/img/delete.png' class='imgDelete'/></td>" + "</tr>");
	}
}

function DeleteRow() {
	$(this).parents("tr").remove();
}

/**
 * This function will be called when user click on Add portion in the formula
 * table below
 * 
 * @return
 */
function AddHPortion() {
	/* Validate whether user selected any value in the herbs */
	var cmbHerbs = zk.Widget.$($("$cmbHerbs").attr("id"));

	if (cmbHerbs.getValue() == undefined || cmbHerbs.getValue() == "") {
		alert("Select atleast one herb to add");
		return;
	}

	/* validate the portion */
	var txtHPortion = $("#txtHPortion");
	if (!/^\d+\.?\d*$/.test(txtHPortion.val())) {
		alert("Slect portion to add");
		txtHPortion.focus();
		return;
	}

	var cmbFormulas1 = zk.Widget.$($("$cmbFormulas1").attr("id"));
	var formulaName = (cmbFormulas1.getValue() == undefined || cmbFormulas1.getValue() == "") ? "" : cmbFormulas1
			.getValue();
	$("#herbsTable > tbody > tr:last").before(
			"<tr><td class='bordercell'>" + formulaName + "</td>" + "<td class='bordercell'>" + cmbHerbs.getValue()
					+ "</td>" + "<td class='bordercell'><input type='text' class='portion' value='" + txtHPortion.val()
					+ "' /></td><td><img src='/img/delete.png' class='imgDelete'/></td>" + "</tr>");
}

/**
 * This function redirects to the next/prev page
 * 
 * @param direction
 * @return
 */
function navigate(direction) {
	if (direction == "Prev") {
		var wgt = zk.Widget.$($("$tbtnTreatment").attr("id"));
		zUtl.go(wgt._href);
	} else {
		var wgt = zk.Widget.$($("$tbtnRptCard").attr("id"));
		zUtl.go(wgt._href);
	}
}