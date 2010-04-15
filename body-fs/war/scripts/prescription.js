function initJS() {
	$("#btnAdd").click(AddPortion);
	$("#btnHAdd").click(AddHPortion);
	$(".imgDelete").live("click", DeleteRow);
	initPage();
	populateDropdowns();
	loadData(false);
}

/**
 * This function loads the data from the textfields to the grids
 * 
 * @return
 */
function loadData(growl) {
	resetDropdowns();
	/* Get the prescription herbs data and populate the table */
	var txtPrescription = zk.Widget.$($("$txtPrescription").attr("id"));
	var arrHerbs = parseJSON(txtPrescription.getValue());
	$("#herbsTable > tbody > tr:not(:last):not(:first)").remove();
	var buffer = new Array();
	$.each(arrHerbs, function() {
		buffer.push("<tr><td class='bordercell'>" + this.formula + "</td>");
		buffer.push("<td class='bordercell'>" + this.herb + "</td>");
		buffer.push("<td class='bordercell'><input type='text' class='portion' value='" + this.portion + "'/></td>");
		if (readonly) {
			buffer.push("<td>&nbsp;</td>");
		} else {
			buffer.push("<td><img src='/img/delete.png' class='imgDelete'/></td>");
		}
		buffer.push("</tr>");
	});
	if (readonly) {
		$("#herbsTable > tbody > tr:first").after(buffer.join(''));
	} else {
		$("#herbsTable > tbody > tr:last").before(buffer.join(''));
	}

	/* laod the service data */
	var txtServices = zk.Widget.$($("$txtServices").attr("id"));
	var serviceData = [];
	if (txtServices.getValue() != "") {
		serviceData = parseJSON(txtServices.getValue());
	}
	$("#tblServices input:checkbox").attr("checked", false);
	$("#tblServices input:text").val("1");
	$("#tblServices select").val("1");
	$("#txtOther").val("");

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
		$("$txtPrescription").val(toJSON(prescArr)).blur();

		var arrServices = new Array();
		/* Save the selected services */
		$("#tblServices > tbody > tr").each(function() {
			var chkService = $(this).find("input:checkbox");
			if (chkService.attr("checked")) {
				var txtService = $(this).find("input:text");
				if (txtService.attr("serviceid") == null) {
					txtService = $(this).find("select");
				}
				arrServices.push( {
					"id" : txtService.attr("serviceid"),
					"count" : txtService.val()
				});
			}
		});
		$("$txtServices").val(toJSON(arrServices)).blur();
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
		if ($("#btnCopy").length <= 0) {
			return;
		}
		var selDate = $(this).val();
		var latestdate = $("#selVisitDates")[0].options[0].value;
		var visitdate = $("$txtVisitDates").val();
		if (visitdate == "") {
			visitdate = latestdate;
		}
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

	/* Handle the click on print */
	$("#btnPrint").click(function() {
		printPrescription();
	});
}

/**
 * This function gets triggerd when user clicks on add portion on the top
 * 
 * @return
 */
function AddPortion() {
	var portion = $.trim($("$txtPortion").val());
	if (portion.length <= 0) {
		alert("Please enter the portion");
		return true;
	}
	if ($("#divHerbs > span").length < 0) {
		alert("Please select herbs to add");
		return true;
	}
	/* for each herb in div herbs add a row in herbstable */
	var buffer = new Array();
	$("#divHerbs > span").each(function(indx, item) {
		buffer.push('<tr><td class="bordercell">' + $(item).attr('formula') + '</td>');
		buffer.push('<td class="bordercell">' + $(item).text() + '</td>');
		buffer.push('<td class="bordercell"><input type="text" class="portion" value="' + portion + '" /></td>');
		buffer.push("<td><img src='/img/delete.png' class='imgDelete'/></td></tr>");
	});
	$("#herbsTable > tbody > tr:last").before(buffer.join(''));
}

function DeleteRow() {
	$(this).parents("tr").remove();
}

/**
 * This function will reset all the herb related dropdown boxes to value -1.
 * This will be called when ever the visitdate change.
 * 
 * @return
 */
function resetDropdowns() {
	$("#selDiagnoses").val("-1");
	$("#selFormulas").val("-1");
	$("$txtPortion").val("");
	$("#divHerbs").html("");
	$("#selFormulas1").val("-1");
	$("#selHerbs").val("-1");
}

/**
 * This function will be called when user click on Add portion in the formula
 * table below
 * 
 * @return
 */
function AddHPortion() {
	/* Validate whether user selected any value in the herbs */
	var selHerb = parseInt($("#selHerbs").val());
	if (selHerb == -1) {
		alert("Select herb to add");
		$("#selHerbs").focus();
		return;
	}

	/* validate the portion */
	var txtHPortion = $("#txtHPortion");
	if (!/^\d+\.?\d*$/.test(txtHPortion.val())) {
		alert("Slect portion to add");
		txtHPortion.focus();
		return;
	}

	var herb = getHerbs(selHerb);
	var formulaid = parseInt($("#selFormulas1").val());
	var formula = formulaid == -1 ? null : getFormulas(formulaid);
	var formulaname = formula == null ? "" : formula.name;
	$("#herbsTable > tbody > tr:last").before(
			"<tr><td class='bordercell'>" + formulaname + "</td>" + "<td class='bordercell'>" + herb.name
					+ "</td>" + "<td class='bordercell'><input type='text' class='portion' value='" + txtHPortion.val()
					+ "' /></td><td><img src='/img/delete.png' class='imgDelete'/></td>" + "</tr>");
}

/**
 * This function prints the current prescription
 * 
 * @return
 */
function printPrescription() {
	/* Check whether any formulas exist or not */
	if ($("#herbsTable > tbody > tr:not(:last):not(:first)").length <= 0) {
		alert("There are no herbs for printing");
	}
	var buffer = new Array();
	/* Construct the print page content */
	buffer
			.push("<html><head><style type='text/css'>body {font-family: 'Monotype Corsiva'; font-size:18px;}</style><body>");
	/* prepare logo */
	buffer
			.push("<div style='width:100%;background-image: url(/img/top_bg.jpg); height: 96px; background-repeat: repeat no-repeat;'>");
	buffer.push("<img style='height: 95px; width: 100%;' src='/img/banner7.jpg' />");
	buffer.push("</div>");
	buffer.push("<br />");
	/* Add the customer name and date */
	buffer.push("<div>");
	buffer.push("For:<span style='display:inline-block;width:150px;border-bottom:1px solid black;'>&nbsp;&nbsp;"
			+ custname + "</span>");
	buffer.push("Date:<span style='display:inline-block;width:100px;border-bottom:1px solid black;'>&nbsp;&nbsp;"
			+ $('#selVisitDates option:selected').text() + "</span><br /><br />");
	buffer.push("</div>");
	/*
	 * add the herbs table don' need to add the header and last row. Also no
	 * need to add the delete image icon
	 */
	buffer.push("<table width='100%'>");
	buffer.push('<tr align="left">');
	buffer.push('<th class="bordercell">Forumula</th>');
	buffer.push('<th class="bordercell">Herbs</th>');
	buffer.push('<th class="bordercell">Portion</th>');
	buffer.push('<th></th>');
	buffer.push('</tr><tbody>');
	$("#herbsTable > tbody > tr:not(:last):not(:first)").each(function() {
		buffer.push("<tr>");
		$(this).find("td:not(:last)").each(function() {
			buffer.push("<td>");
			buffer.push($(this).html());
			buffer.push("</td>");
		});
		buffer.push("</tr>");
	});
	buffer.push("</tbody></table>");
	/* prepare print button */
	buffer.push("<div style='text-align:center'><button onclick='window.print();'>Print</button></div>");
	buffer.push("</body></html>");
	var printWindow = window.open('', 'PrintWindow', 'width=600,height=600');
	printWindow.document.open();
	printWindow.document.write(buffer.join("\n"));
	printWindow.document.close();
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

/**
 * This function populates the diagnoses, formulas and herb related dropdown
 * lists
 * 
 * @return
 */
function populateDropdowns() {
	/* Load Herbs */
	if ($('body').data('Herbs') == undefined) {
		$.ajax( {
			type : 'GET',
			url : '/app/herb/all',
			dataType : "json",
			async : false,
			success : function(data1) {
				$('body').data('Herbs', data1);
			}
		});
	}
	
	/* Load Formulas */
	if ($('body').data('Formulas') == undefined) {
		$.ajax( {
			type : 'GET',
			url : '/app/herb/formulas',
			dataType : "json",
			async : false,
			success : function(data1) {
				$('body').data('Formulas', data1);
			}
		});
	}

	/* Load Diagnoses */
	if ($('body').data('Diagnoses') == undefined) {
		$.ajax( {
			type : 'GET',
			url : '/app/herb/diagnoses',
			dataType : "json",
			async : false,
			success : function(data1) {
				$('body').data('Diagnoses', data1);
			}
		});
	}
	var diagnoses = $('body').data('Diagnoses');
	var buffer = new Array();
	$.each(diagnoses, function(indx, element) {
		buffer.push('<option value="' + diagnoses[indx].id + '">' + diagnoses[indx].name + '</option>');
	});
	$("#selDiagnoses").append(buffer.join(""));


	/* Attach event handler for diagnoses */
	$("#selDiagnoses").change(diagnosesChange);

	/* Attach event handler for formulas */
	$("#selFormulas").change(formulaChange);

	/* Populate selFormulas1 */
	var formulas = $('body').data('Formulas');
	var buffer = new Array();
	buffer.push('<option value="-1"></option>');
	$.each(formulas, function(indx, item) {
		buffer.push('<option value="' + item.id + '">' + item.name + '</option>');
	});
	$("#selFormulas1").html(buffer.join('')).change(formula1Change);

	/* Popuate selHerbs */
	var herbs = $('body').data('Herbs');
	var buffer = new Array();
	buffer.push('<option value="-1"></option>');
	$.each(herbs, function(indx, item) {
		buffer.push('<option value="' + item.id + '">' + item.name + '</option>');
	});
	$("#selHerbs").html(buffer.join(''));
}

/**
 * This function will be called from diagnoses select change event This function
 * finds the formulas for the selected diagnosis and populates the formulas
 * dropdown
 * 
 * @return
 */
function diagnosesChange() {
	var selVal = parseInt($(this).val());
	if (selVal == -1) {
		$("#selFormulas").html('<option value="-1"></option>');
		return true;
	} else {
		/* Get the diagnosis object corresponds to the selected id */
		var selDiagnosis = getDiagnosis(selVal);
		if (selDiagnosis.formulas.length <= 0) {
			$("#selFormulas").html('<option value="-1"></option>');
			return true;
		}
		/* Get formula objects */
		var formulas = getFormulas(selDiagnosis.formulas);
		if (formulas == null) {
			$("#selFormulas").html('<option value="-1"></option>');
			return true;
		}
		formulas = $.makeArray(formulas);
		var buffer = new Array();
		buffer.push('<option value="-1"></option>');
		$.each(formulas, function(indx, item) {
			buffer.push('<option value="' + item.id + '">' + item.name + '</option>');
		});
		$("#selFormulas").html(buffer.join(''));
		return true;
	}
}

/**
 * This function will be called from formulas dropdown change event. This will
 * finds herbs corresponds to the selected formula and populate divHerbs
 * 
 * @return
 */
function formulaChange() {
	var selVal = parseInt($(this).val());
	if (selVal == -1) {
		$("#divHerbs").html("");
		return true;
	}
	/* Get the selected formula */
	var formula = getFormulas(selVal);
	if (formula == null) {
		$("#divHerbs").html("");
		return true;
	}
	/* Loop through all the herbs and add to the divHerbs */
	var buffer = new Array();
	var herbs = getHerbs(formula.herbs);
	if (herbs == null) {
		$("#divHerbs").html("");
		return true;
	}
	herbs = $.makeArray(herbs);
	$.each(herbs, function(indx, item) {
		buffer.push("<span style='width:100%;display:inline-block;' formula='" + formula.name + "'>" + item.name + "</span>");
	});
	$("#divHerbs").html(buffer.join('<br />'));
	return true;
}

/**
 * This function gets invoked from the selFormulas1 change event. This will
 * populate the selHerbs with corresponding selection
 * 
 * @return
 */
function formula1Change() {
	var selVal = parseInt($(this).val());
	var buffer = new Array();
	buffer.push('<option value="-1"></option>');
	if (selVal == -1) {
		/* Add all herbs back */
		var herbs = $('body').data('Herbs');
		$.each(herbs, function(indx, item) {
			buffer.push('<option value="' + item.id + '">' + item.name + '</option>');
		});
		$("#selHerbs").html(buffer.join(''));
		return true;
	}
	var formula = getFormulas(selVal);
	if (formula == null) {
		$("#selHerbs").html(buffer.join(''));
		return true;
	}
	var herbs = getHerbs(formula.herbs);
	if (herbs == null) {
		$("#selHerbs").html(buffer.join(''));
		return true;
	}
	herbs = $.makeArray(herbs);
	$.each(herbs, function(indx, item) {
		buffer.push('<option value="' + item.id + '">' + item.name + '</option>');
	});
	$("#selHerbs").html(buffer.join(''));
	return true;
}

/**
 * This function returns the diagnosis object by it's id
 * 
 * @param id
 * @return diagnosis object
 */
function getDiagnosis(id) {
	var diagnoses = $('body').data('Diagnoses');
	for ( var i = 0, len = diagnoses.length; i < len; i++) {
		if (diagnoses[i].id == id) {
			return diagnoses[i];
		}
	}
	return null;
}

/**
 * Returns formula object corresponds to given id. multiple ids can be passed in
 * as array and return will be multiple formula objects
 * 
 * @param id formula id or array of ids
 * @return
 */
function getFormulas(id) {
	var retFormulas = new Array();
	var formulas = $('body').data('Formulas');
	var ids = $.makeArray(id);
	for ( var i = 0, len = formulas.length; i < len; i++) {
		if (ids.indexOf(formulas[i].id) != -1) {
			retFormulas.push(formulas[i]);
			if (ids.length == retFormulas.length) {
				break;
			}
		}
	}
	return retFormulas.length == 0 ? null : retFormulas.length == 1 ? retFormulas[0] : retFormulas;
}

/**
 * This function finds the herbs objects for the given herb ids
 * 
 * @param id id or array of ids
 * @return
 */
function getHerbs(id) {
	var ids = $.makeArray(id);
	var retHerbs = new Array();
	var herbs = $('body').data('Herbs');
	for ( var i = 0, len = herbs.length; i < len; i++) {
		if (ids.indexOf(herbs[i].id) != -1) {
			retHerbs.push(herbs[i]);
			if (ids.length == retHerbs.length) {
				break;
			}
		}
	}
	return retHerbs.length == 0 ? null : retHerbs.length == 1 ? retHerbs[0] : retHerbs;
}
