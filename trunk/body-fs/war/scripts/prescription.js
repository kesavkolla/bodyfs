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

	/* laod the service data */
	var txtServices = zk.Widget.$($("$txtServices").attr("id"));
	var serviceData = [];
	if (txtServices.getValue() != "") {
		serviceData = parseJSON(txtServices.getValue());
	}
	$("#tblServices input:checkbox").attr("checked", false);
	$("#tblServices input:text").val("1");

	for ( var i = 0, len = serviceData.length; i < len; i++) {
		var data = serviceData[i];
		$("#tblServices input#chk" + data.id).attr("checked", true);
		$("#tblServices #txt" + data.id).val(data.count);
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

	/* handle click on other checkbox */
	$("#tblServices input[servicename='Other']").click(function() {
		if ($(this).attr("checked")) {
			$("#txtOther").show();
		} else {
			$("#txtOther").val("").hide();
		}
	});

	/* handle click on done and prev button and save the values */
	$(".submitbtn").click(function() {
		var prescArr = new Array();
		/*
		 * Get all the table rows and prepare an object with formula, herb & portion
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

		var arrServices = new Array();
		/* Save the selected services */
		$("#tblServices > tbody > tr").each(function() {
			var chkService = $(this).find("input:checkbox");
			if (chkService.attr("checked")) {
				var txtService = $(this).find("input:text");
				if(txtService.attr("serviceid") == null) {
					txtService = $(this).find("select");
				}
				arrServices.push( {
					"id" : txtService.attr("serviceid"),
					"count" : txtService.val()
				});
			}
		});
		$("$txtServices").val($.toJSON(arrServices)).blur();
		//alert($.toJSON(arrServices));
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
		$("#btnCopy").attr("disabled", "disabled");
	});

	/* disable/enable copy */
	$("#selVisitDates").change(function() {
		var selDate = $(this).val();
		var latestdate = $("#selVisitDates")[0].options[0].value;
		var visitdate = $("$txtVisitDates").val();
		if (visitdate < latestdate) {
			$("#btnCopy").attr("disabled", "disabled");
			return;
		}
		if ($(this).val() < latestdate) {
			$("#btnCopy").removeAttr("disabled");
		} else {
			$("#btnCopy").attr("disabled", "disabled");
		}
	});

	/* Handle the click on Copy */
	$("#btnCopy").click(function() {
		var selVisitDates = $("#selVisitDates")[0];
		var selDate = selVisitDates.options[selVisitDates.selectedIndex].value;
		$("$txtVisitDatesCopy").val(selDate).blur();
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