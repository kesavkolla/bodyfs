/**
 * This function will be called as after mount
 * 
 * @return
 */
function initPage() {
	$(".imgDelete").live("click", DeleteRow);
	/* handle the click on add button */
	$("#btnAdd").click(addService);
	$("#btnCalculate").click(calculateService);
	$("#btnPrint").click(printSummary);
	$("#btnCancel").click(resetAll);
	$("$btnSave").click(prepareSave);
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
	var maxWeek = -1;
	var totalCost = 0;
	$("#tblServicesSummary > tbody").html("");
	for ( var serviceid in cumulativeList) {
		maxWeek = Math.max(maxWeek, cumulativeList[serviceid].week);
		var service = getServiceById(serviceid);
		/* create the summary row and append to the summary table */
		var buffer = new Array();
		buffer.push("<tr>");
		buffer.push("<td style='height:25px'>" + service.serviceName + "</td>");
		buffer.push("<td>" + cumulativeList[serviceid].total + "</td>");
		buffer.push("<td>$" + service.charge + "</td>");
		buffer.push("<td>$" + service.charge * cumulativeList[serviceid].total + "</td>");
		buffer.push("</tr>")
		$("#tblServicesSummary > tbody").append(buffer.join());
		totalCost += service.charge * cumulativeList[serviceid].total;
	}
	$("#tblServicesSummary > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesSummary > tbody > tr:odd").addClass("z-listbox-odd");
	$("$planLength").val(maxWeek).blur();
	
	/* populate the totals */
	$("#totalCost").html(totalCost);
	$("#spndiscount").html($("$txtDiscount").val());
	var discount = $("$txtDiscount").val();
	if (!isNaN(parseFloat(discount))) {
		totalCost -= (totalCost * discount / 100);
	}
	$("#spnpayable").html(totalCost);
}


/**
 * This function will be called when user clicks on Add button
 * 
 * @return
 */
function addService() {
	var services = $.data(document.body, "servicelist");
	/* Check whether services does exist and is array */
	if (!$.isArray(services) || services.length <= 0) {
		return;
	}
	/* If user hasen't select any service alert him */
	var cmbServices = zk.Widget.$($("$cmbServices").attr("id"));
	if (cmbServices.getValue() == undefined) {
		alert("Please select a service to add");
		return;
	}
	var selService = getSelectedService(services, cmbServices.getValue());
	/* prepare the row and append to the tblServicesBreakDown table */
	var buffer = new Array();
	buffer.push("<tr serviceid='" + selService.id + "'>");
	buffer.push("<td><input type='text' class='txtCnt' value='1'/></td>");
	buffer.push("<td>" + selService.serviceName + " per weeks for</td>");
	buffer.push("<td><input type='text' class='txtWeek' value='1' /></td>");
	buffer.push("<td><img src='/img/delete.png' class='imgDelete'/></td>");
	buffer.push("</tr>");
	$("#tblServicesBreakDown > tbody").append(buffer.join());
	$("#tblServicesBreakDown > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesBreakDown > tbody > tr:odd").addClass("z-listbox-odd");
}

/**
 * This function opens a new browser window with the print summary
 * 
 * @return
 */
function printSummary() {
	/* check whether it's ready to show the print page or not */
	if(isNaN(parseFloat($("#totalCost").html()))) {
		alert("Click Caculate button before printing");
		return;
	}
	var buffer = new Array();
	/* Construct the print page content */
	buffer.push("<html><head><style type='text/css'>body {font-family: 'Monotype Corsiva'; font-size:18px;}</style><body>");
	/* prepare logo */
	buffer.push("<div style='width:100%;background-image: url(/img/top_bg.jpg); height: 96px; background-repeat: repeat no-repeat;'>");
	buffer.push("<img style='height: 95px; width: 100%;' src='/img/banner7.jpg' />");
	buffer.push("</div>");
	buffer.push("<div style='text-align:center;font-weight:bold;font-size:20px'>Your Corrective Care Program</div>");
	/* prepare header content */
	buffer.push("<div style='margin-left:20px'>");
	buffer.push("<br /><br />")
	buffer.push("For:<span style='display:inline-block;width:150px;border-bottom:1px solid black;'>&nbsp;</span>");
	buffer.push("Date:<span style='display:inline-block;width:100px;border-bottom:1px solid black;'>&nbsp;</span><br /><br />");
	/* show the treatment plan length */
	buffer.push("Treatment Plan Length: <span style='border-bottom:1px solid black;'>" + $("$planLength").val() + "</span>&nbsp;weeks<br /><br />");
	/* prepare the summary breakdown */
	$("#tblServicesBreakDown > tbody > tr").each(function() {
		var count = parseInt($(this).find("input[class='txtCnt']").val());
		var week = parseInt($(this).find("input[class='txtWeek']").val());
		var serviceid = $(this).attr("serviceid");
		var service = getServiceById(serviceid);
		buffer.push(count + "&nbsp;" + service.serviceName + "&nbsp; Session per week for " + week + "&nbsp; week" + (week > 1 ? "s":""));
		buffer.push("<br />");
	});
	buffer.push("<p></p>");
	buffer.push("<span style='font-weight:bold'>Total Treatments:</span><br /><br />");
	buffer.push("<table>");
	/* prepare the cost of session */
	$("#tblServicesSummary > tbody > tr").each(function() {
		var cell1 = $(this).find("td:first").html().split(":");
		var cost = $(this).find("td:nth-child(2)").html();
		var total = $(this).find("td:last").html();
		buffer.push("<tr><td>");
		buffer.push(cell1[1]);
		buffer.push("</td><td><span style='display:inline-block;width:20px'>&nbsp;</td>");
		buffer.push("<td>$");
		buffer.push(cost);
		buffer.push("&nbsp;X&nbsp;");
		buffer.push(cell1[0]);
		buffer.push("&nbsp;=&nbsp;$");
		buffer.push(total);
		buffer.push("</td></tr>");
	});
	buffer.push("<tr><td span='3'>&nbsp;</td></tr>");
	buffer.push("<tr><td span='3'>&nbsp;</td></tr>");
	/* prepare the final totals */
	buffer.push("<tr><td style='font-weight:bold;'>Total</td><td>" + $("#totalCost").html() + "</td></tr>");
	buffer.push("<tr><td style='font-weight:bold;'>Discount%</td><td>" + $("$txtDiscount").val() + "</td><td></td></tr>");
	var discount = $("$txtDiscount").val();
	var totalCost = parseFloat($("#totalCost").html());
	if(!isNaN(parseFloat(discount))) {
		totalCost -= (totalCost * discount / 100); 
	}
	buffer.push("<tr><td style='font-color:blue;font-weight:bold;'>Total Payable</td><td>" + totalCost + "</td><td></td></tr>");
	buffer.push("</table>");
	buffer.push("</div>");
	/* prepare print button */
	buffer.push("<div style='text-align:center'><button onclick='window.print();'>Print</button></div>");
	buffer.push("</body></html>");
	var printWindow =  window.open('','PrintWindow','width=600,height=600');
	printWindow.document.open();
	printWindow.document.write(buffer.join("\n"));
	printWindow.document.close();
}

/**
 * This function is called from the afterCompose. This will keep the services object into the jQuery data. That will be
 * used later on.
 */
function saveServices(services) {
	$.data(document.body, "servicelist", services);
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
 * This function loops through all the services and find the service that matches with the service that is selected in
 * combobox
 * 
 * @return
 */
function getSelectedService(services, selServiceName) {
	for ( var i = 0, len = services.length; i < len; i++) {
		if (services[i].serviceName == selServiceName) {
			return services[i];
		}
	}
	return {};
}

/**
 * This function retrieves the service by it's id from the data cache
 * 
 * @param serviceid
 * @return
 */
function getServiceById(serviceid) {
	var services = $.data(document.body, "servicelist");
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
	if(isNaN(parseFloat($("#totalCost").html()))) {
		alert("Click Caculate button before saving");
		evt.stopPropagation();
		evt.preventDefault();
		return false;
	}
	var cmbCustomers = zk.Widget.$($("$cmbCustomers").attr("id"));
	if (cmbCustomers.getValue() == undefined) {
		alert("select the patient before saving");
		evt.stopPropagation();
		evt.preventDefault();
		return false;
	}

	/* prepare the json data object */
	var arrServices = new Array();
	$("#tblServicesBreakDown > tbody > tr").each(function() {
		var count = parseInt($(this).find("input[class='txtCnt']").val());
		var week = parseInt($(this).find("input[class='txtWeek']").val());
		var serviceid = $(this).attr("serviceid");
		arrServices.push({"serviceid": serviceid, "count":count, "weeks": week});
	});
	$("$txtPaymentData").val($.toJSON(arrServices)).blur();
	return true;
}

/**
 * This function will be called when ever patient combobox is changed and that patient's plan is retrieved and displayed
 * 
 * @return
 */
function loadServices(data) {
	resetAll();
	if (!$.isArray(data) || data.length <= 0) {
		return;
	}
	$.each(data, function() {
		var service = getServiceById(this.serviceid);
		var buffer = new Array();
		buffer.push("<tr serviceid='" + this.serviceid + "'>");
		buffer.push("<td><input type='text' class='txtCnt' value='" + this.count + "'/></td>");
		buffer.push("<td>" + service.serviceName + " per weeks for</td>");
		buffer.push("<td><input type='text' class='txtWeek' value='" + this.weeks + "' /></td>");
		buffer.push("<td><img src='/img/delete.png' class='imgDelete'/></td>");
		buffer.push("</tr>");
		$("#tblServicesBreakDown > tbody").append(buffer.join());
	});
	$("#tblServicesBreakDown > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesBreakDown > tbody > tr:odd").addClass("z-listbox-odd");
	$("#btnCalculate").click();
}