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
	$("#herbsTable > tbody > tr:not(:last):not(:first)").remove()
	$.each(arrHerbs, function() {
		$("#herbsTable > tbody > tr:last").before(
				"<tr><td>" + this.formula + "</td><td>" + this.herb + "</td><td><input type='text' value='"
						+ this.portion + "'/></td>" + "<td><img src='/img/delete.png' class='imgDelete'/></td></tr>");
	});

	/* populate the radio buttons */
	var txtmedType = $("$txtmedType").hide().val();
	$("input[name='radtmedType']").removeAttr("checked");
	if (txtmedType.length > 0) {
		var selRad = $("input[name='radtmedType'][value='" + txtmedType + "']");
		if (selRad.length > 0) {
			selRad.attr("checked", "checked");
		} else {
			$("input[name='radtmedType'][value='Other']").attr("checked", "checked");
			$("$txtmedType").show();
		}
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

	/* Handle click on radio buttons to hide/show textbox for other */
	$("input[name='radtmedType']").click(function() {
		if ($(this).val() == "Other") {
			$("$txtmedType").show();
		} else {
			$("$txtmedType").val($(this).val()).hide().blur();
		}
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
		return herb.name;
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
				"<tr><td>" + formuladata.formula.name + "</td>" + "<td>" + formuladata.herbs[i].name + "</td>"
						+ "<td><input type='text' value='" + portion + "' /></td>"
						+ "<td><img src='/img/delete.png' class='imgDelete'/></td></tr>");
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
			"<tr><td>" + formulaName + "</td>" + "<td>" + cmbHerbs.getValue() + "</td>"
					+ "<td><input type='text' value='" + txtHPortion.val() + "' /></td>"
					+ "<td><img src='/img/delete.png' class='imgDelete'/></td></tr>");
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