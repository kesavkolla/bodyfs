function initJS() {
	$("#btnAdd").click(AddPortion);
	$("#btnHAdd").click(AddHPortion);
	$(".imgDelete").live("click", DeleteRow);
	$.data(document.body, "loadedformulas", new Array());
	initPage();
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
		/* Get all the table rows and prepare an object with formula, herb & portion */
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
						+ "<td><img src='/img/delete.png' class='imgDelete'/></tr>");
	}
}

function DeleteRow() {
	$(this).parents("tr").remove();
}

/**
 * This function will be called when user click on Add portion in the formula table below
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
					+ "<td><img src='/img/delete.png' class='imgDelete'/></tr>");
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