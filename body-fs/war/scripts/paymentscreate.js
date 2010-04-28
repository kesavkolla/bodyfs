/**
 * This function will be called as after mount
 * 
 * @return
 */
function initPage() {
	var buffer = new Array();
	buffer.push('<option value="-1"></option>');
	$.each(arrServices, function() {
		buffer.push('<option value="' + this.id + '">' + this.serviceName + '</option>');
	});
	$("#selServices").html(buffer.join(''));
	$(".imgDelete").live("click", DeleteRow);
	/* handle the click on add button */
	$("#btnAdd").click(addService);
	$("#btnCalculate").click(calculateService);
	$("#btnPrint").click(printSummary);
	$("#btnCancel").click(resetAll);
	$(".submtbtn").click(prepareSave);
	
	if (!$.isEmptyObject(plan)) {
		populatePlan();
	}
}

/**
 * This function will be triggered when user clicks on calculate button. This will summarize all the services.
 * 
 * @return
 */
function calculateService() {
	/* check wheter any service being added or not */
	if ($("#tblServicesBreakDown > tbody > tr").length <= 0) {
		alert("Add services before clicking on calculate");
		return;
	}
	var cumulativeList = {};
	$("#tblServicesBreakDown > tbody > tr").each(function() {
		/* Validate count & week data */
		var count = parseInt($(this).find("input[class='txtCnt']").val());
		var week = parseInt($(this).find("input[class='txtWeek']").val());
		if (isNaN(count) || count < 1) {
			alert("Provide the count value");
			$(this).find("input[class='txtCnt']").val("").focus();
			return;
		}
		if (isNaN(week) || week < 1) {
			alert("Please provide number of weeks");
			$(this).find("input[class='txtWeek']").val("").focus();
			return;
		}

		/* Get the service and make it part of cumulative service list */
		var serviceid = $(this).attr("serviceid");
		if (cumulativeList[serviceid] == undefined) {
			cumulativeList[serviceid] = {
				"total" : (week * count),
				"week" : week
			};
		} else {
			var tmpval = cumulativeList[serviceid];
			cumulativeList[serviceid] = {
				"total" : tmpval.total + (week * count),
				"week" : tmpval.week + week
			};
		}
	});

	/* Now populate the summary table with each total and cost */
	var maxSession = -1;
	var totalCost = 0;
	$("#tblServicesSummary > tbody").html("");
	for ( var serviceid in cumulativeList) {
		maxSession = Math.max(maxSession, cumulativeList[serviceid].total);
		var service = getServiceById(serviceid);
		/* create the summary row and append to the summary table */
		var buffer = new Array();
		buffer.push("<tr>");
		buffer.push("<td style='height:25px'>" + service.serviceName + "</td>");
		buffer.push("<td>" + cumulativeList[serviceid].total + "</td>");
		buffer.push("<td>" + service.charge + "</td>");
		buffer.push("<td>" + service.charge * cumulativeList[serviceid].total + "</td>");
		buffer.push("</tr>")
		$("#tblServicesSummary > tbody").append(buffer.join(""));
		totalCost += service.charge * cumulativeList[serviceid].total;
	}
	$("#tblServicesSummary > tbody > tr > td:nth-child(4)").formatCurrency()
	$("#tblServicesSummary > tbody > tr > td:nth-child(3)").formatCurrency()
	$("#tblServicesSummary > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesSummary > tbody > tr:odd").addClass("z-listbox-odd");
	$("$planLength").val(maxSession).blur();

	/* populate the totals */
	$("#totalCost").html(totalCost).formatCurrency();
	var discount = $("$txtDiscount").val();
	var totalDiscount = (totalCost * discount / 100);
	
	if (!isNaN(parseFloat(discount))) {
		totalCost -= (totalCost * discount / 100);
	}
	$("#spndiscount").html(totalDiscount).formatCurrency();
	
	$("#spnpayable").html(totalCost).formatCurrency();
}


/**
 * This function will be called when user clicks on Add button
 * 
 * @return
 */
function addService() {
	var services = arrServices;
	/* Check whether services does exist and is array */
	if (!$.isArray(services) || services.length <= 0) {
		return;
	}
	/* If user hasen't select any service alert him */
	var selServiceid = parseInt($("#selServices").val());
	if (selServiceid == -1) {
		alert("Please select a service to add");
		return;
	}
	var selService = getServiceById(selServiceid);
	/* prepare the row and append to the tblServicesBreakDown table */
	var buffer = new Array();
	if (selService.serviceName != "Re-Exam") {
		var treatment = selService.serviceName != "Herbal Treatment" ? " treatment" : "";
		buffer.push("<tr serviceid='" + selService.id + "'>");
		buffer.push("<td><input type='text' class='txtCnt' size='3' value='1'/><span class='space' /></td>");
		buffer.push("<td>" + selService.serviceName + treatment+" per week for</td>");
		buffer.push("<td><span class='space' /><input type='text' class='txtWeek' size='3' value='1' /> weeks</td>");
		buffer.push("<td><span class='space' /><img src='/img/delete.png' class='imgDelete'/></td>");
		buffer.push("</tr>");
	} else {
		buffer.push("<tr serviceid='" + selService.id + "'>");
		buffer.push("<td colspan='2'><input type='text' class='txtCnt' size='3' style='display:none' value='1'/>");
		buffer.push("Number of Re-Exams during treatment</td>");
		buffer.push("<td><span class='space' /><input type='text' class='txtWeek' size='3' value='1' /></td>");
		buffer.push("<td><span class='space' /><img src='/img/delete.png' class='imgDelete'/></td>");
		buffer.push("</tr>");
	}
	var row = $("#tblServicesBreakDown > tbody").append(buffer.join(""));
	$("#tblServicesBreakDown > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesBreakDown > tbody > tr:odd").addClass("z-listbox-odd");
}

/**
 * 
 * @return
 */
function populatePlan() {
	$("$txtDiscount").val(plan.discount);
	$("$planLength").val(plan.planLength);
	var buffer = new Array();
	$.each(plan.planItems, function(indx, val) {
		var item = $.parseJSON(val);
		var selService = getServiceById(item.serviceid);
		/* prepare the row and append to the tblServicesBreakDown table */
		if (selService.serviceName != "Re-Exam") {
			var treatment = selService.serviceName != "Herbal Treatment" ? " treatment" : "";
			buffer.push("<tr serviceid='" + selService.id + "'>");
			buffer.push("<td><input type='text' class='txtCnt' size='3' value='" + item.count
					+ "'/><span class='space' /></td>");
			buffer.push("<td>" + selService.serviceName + treatment+" per week for</td>");
			buffer.push("<td><span class='space' /><input type='text' class='txtWeek' size='3' value='" + item.weeks
					+ "' /> weeks</td>");
			buffer.push("<td><span class='space' /><img src='/img/delete.png' class='imgDelete'/></td>");
			buffer.push("</tr>");
		} else {
			buffer.push("<tr serviceid='" + selService.id + "'>");
			buffer.push("<td colspan='2'><input type='text' class='txtCnt' size='3' style='display:none' value='"
					+ item.count + "'/>");
			buffer.push("Number of Re-Exams during treatment</td>");
			buffer.push("<td><span class='space' /><input type='text' class='txtWeek' size='3' value='" + item.weeks
					+ "' /></td>");
			buffer.push("<td><span class='space' /><img src='/img/delete.png' class='imgDelete'/></td>");
			buffer.push("</tr>");
		}
	});
	$("#tblServicesBreakDown > tbody").append(buffer.join(""));
	$("#tblServicesBreakDown > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesBreakDown > tbody > tr:odd").addClass("z-listbox-odd");
	calculateService();

}

/**
 * This function opens a new browser window with the print summary
 * 
 * @return
 */
function printSummary() {
	/* check whether it's ready to show the print page or not */
	if (isNaN(parseFloat($("#totalCost").html().substring(1)))) {
		alert("Click Caculate button before printing");
		return;
	}
	/* Get the customer name */
	var custName = "";
	if (window.patname == undefined) {
		custName = zk.Widget.$($("$cmbCustomers").attr("id")).getValue();
	} else {
		custName = patname;
	}
	
	if(custName == undefined) {
		custName = "";
	}
	/* date in mm/dd/yyyy format */
	var today = new Date();
	today = (today.getMonth() + 1) + "/" + today.getDate() + "/" + today.getFullYear();

	var buffer = new Array();
	/* Construct the print page content */
	buffer
			.push("<html><head><style type='text/css'>body {font-family: 'Monotype Corsiva'; font-size:18px;}</style><body>");
	/* prepare logo */
	buffer
			.push("<div style='width:100%;background-image: url(/img/top_bg.jpg); height: 96px; background-repeat: repeat no-repeat;'>");
	buffer.push("<img style='height: 95px; width: 100%;' src='/img/banner7.jpg' />");
	buffer.push("</div>");
	buffer.push("<div style='text-align:center;font-weight:bold;font-size:20px'>Your Corrective Care Program</div>");
	/* prepare header content */
	buffer.push("<div style='margin-left:20px'>");
	buffer.push("<br /><br />")
	buffer.push("Name:<span style='display:inline-block;width:300px;border-bottom:1px solid black;'>&nbsp;&nbsp;"
			+ custName + "</span>");
	buffer.push("Date:<span style='display:inline-block;width:100px;border-bottom:1px solid black;'>&nbsp;&nbsp;"
			+ today + "</span><br /><br />");
	/* show the treatment plan length */
	buffer.push("Treatment Plan Length: <span style='border-bottom:1px solid black;'>" + $("$planLength").val()
			+ "</span>&nbsp;sessions<br /><br />");
	/* prepare the summary breakdown */
	$("#tblServicesBreakDown > tbody > tr").each(
			function() {
				var count = parseInt($(this).find("input[class='txtCnt']").val());
				var week = parseInt($(this).find("input[class='txtWeek']").val());
				var serviceid = $(this).attr("serviceid");
				var service = getServiceById(serviceid);
				if (service.serviceName != "Re-Exam") {
					var treatment = service.serviceName != "Herbal Treatment" ? " treatment" : "";
					buffer.push(count + "&nbsp;" + service.serviceName + treatment +"&nbsp; per week for " + week
							+ "&nbsp; week" + (week > 1 ? "s" : ""));
				} else {
					buffer.push("Number of Re-Exams during treatment&nbsp;" + week);
				}
				buffer.push("<br />");
			});
	buffer.push("<p></p>");
	buffer.push("<span style='font-weight:bold'>Total Treatments:</span><br /><br />");
	buffer.push("<table>");
	buffer.push("<tr><td colspan='2'><table border='1' style='border: 2px solid black; border-collapse: collapse;' >");
	buffer.push("<tr><td><b>Treatment</b></td><td><b>Number of Sessions</b></td><td><b>Total Cost</b></td></tr");
	
	/* prepare the cost of session */
	$("#tblServicesSummary > tbody > tr").each(function() {
		var cell1 = $(this).find("td:first").html().split(":");
		var cost = $(this).find("td:nth-child(2)").html();
		var total = $(this).find("td:last").html();
		buffer.push("<tr>");
		buffer.push("<td>");
		buffer.push(cell1[0]);
		buffer.push("</td><td>");
		buffer.push(cost);
		buffer.push("&nbsp;&nbsp;</td><td style='text-align: right;'>");
		buffer.push(total);
		buffer.push("</td></tr>");
	});
	buffer.push("</table></td></tr>");
	buffer.push("<tr><td colspan='2' style='display:inline-block;width:100px;border-bottom:1px solid black;'>&nbsp;</td></tr>");
	buffer.push("<tr><td colspan='2'>&nbsp;</td></tr>");

	/* prepare the final totals */
	buffer.push("<tr><td style='font-weight:bold;'>Total</td><td style='text-align: right;'>" + $("#totalCost").html() + "</td></tr>");
	buffer.push("<tr><td style='font-weight:bold;'>Discount</td><td style='text-align: right;'>" + $("#spndiscount").html()
			+ "</td><td></td></tr>");
	buffer.push("<tr><td style='font-color:blue;font-weight:bold;'>Total Payable</td><td style='text-align: right;'>" + $("#spnpayable").html()
			+ "</td><td></td></tr>");
	buffer.push("</table>");
	buffer.push("</div>");
	/* prepare print button */
	buffer.push("<div style='text-align:center'><button onclick='window.print();'>Print</button></div>");
	buffer.push("</body></html>");
	var printWindow = window.open('', 'PrintWindow', 'width=600,height=600');
	printWindow.document.open();
	printWindow.document.write(buffer.join("\n"));
	printWindow.document.close();
}

function DeleteRow() {
	$(this).parents("tr").remove();
	$("#tblServicesBreakDown > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesBreakDown > tbody > tr:odd").addClass("z-listbox-odd");
}

/**
 * This function resets all the data
 * 
 * @return
 */
function resetAll() {
	$("#tblServicesBreakDown > tbody").html("");
	$("#tblServicesSummary > tbody").html("");
	$("$planLength").val("");
	$("#totalCost").html("&nbsp;");
}

/**
 * This function retrieves the service by it's id from the data cache
 * 
 * @param serviceid
 * @return
 */
function getServiceById(serviceid) {
	var services = arrServices;
	for ( var i = 0, len = services.length; i < len; i++) {
		if (services[i].id == serviceid) {
			return services[i];
		}
	}
	return {};
}

/**
 * This funciton will check whether calculation is happened before saving and also make sure patient is selected. This
 * will create a json object of all the services and then it will be passed to the server.
 * 
 * @return
 */
function prepareSave(evt) {
	/* do the validations */
	if (isNaN(parseFloat($("#totalCost").html().substring(1)))) {
		alert("Click Caculate button before saving");
		evt.stopPropagation();
		evt.preventDefault();
		return false;
	}

	if (window.patname == undefined) {
		var cmbCustomers = zk.Widget.$($("$cmbCustomers").attr("id"));
		if (cmbCustomers.getValue() == undefined) {
			alert("select the patient before saving");
			evt.stopPropagation();
			evt.preventDefault();
			return false;
		}
	}

	/* prepare the json data object */
	var arrServices = new Array();
	$("#tblServicesBreakDown > tbody > tr").each(function() {
		var count = parseInt($(this).find("input[class='txtCnt']").val());
		var week = parseInt($(this).find("input[class='txtWeek']").val());
		var serviceid = $(this).attr("serviceid");
		arrServices.push( {
			"serviceid" : serviceid,
			"count" : count,
			"weeks" : week
		});
	});
	$("$txtPaymentData").val(toJSON(arrServices)).blur();
	return true;
}


/**
 * Navigates to prev/next page
 * 
 * @param direction
 * @return
 */
function navigate(direction) {
	if (direction == "Done") {
		zUtl.go('/pages/usermgmt/customersearch.zul');
	} else {
		var wgt = zk.Widget.$($("$tbtnDiagnosis").attr("id"));
		zUtl.go(wgt._href);
	}
}