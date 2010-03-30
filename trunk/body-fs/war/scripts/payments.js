/**
 * This function invoked after mount
 * 
 * @return
 */
function initPage() {
	$(".imgDelete").live("click", DeleteRow);
	/* Loop through all the plans and construct the page */
	for ( var i = 0, len = plans.length; i < len; i++) {
		var plan = plans[i];
		appendPlan(plan);
	}
}

/**
 * This function generates HTML for one plan
 * 
 * @param plan
 * @return
 */
function appendPlan(plan) {
	var buffer = new Array();
	buffer.push('<fieldset id="' + plan.id + '">');
	var paymentDate = new Date(plan.paymentDate);
	var strDate = (paymentDate.getMonth() + 1) + "/" + paymentDate.getDate() + "/" + paymentDate.getFullYear();
	buffer.push('<legend>' + strDate + '</legend>');
	/* Add plan length and services list */
	buffer.push('Treatement Plan Length :<input type="text" id="txtPlanLength' + plan.id + '" size="10" value="'
			+ plan.planLength + '" />');
	addServiceDropdown(buffer, plan);
	buffer.push("<br /><br />");

	/* Add all the plan items in a table */
	buffer.push('<div style="background-color:white">');
	buffer.push('<table id="tblServicesList' + plan.id + '"><tbody>');
	for ( var i = 0, len = plan.planItems.length; i < len; i++) {
		var service = getServiceById(plan.planItems[i].serviceid);
		var planitem = plan.planItems[i];
		if (service.serviceName != "Re-Exam") {
			buffer.push("<tr serviceid='" + service.id + "'>");
			buffer.push("<td><input type='text' class='txtCnt' size='3' value='" + planitem.count
					+ "'/><span class='space' /></td>");
			buffer.push("<td>" + service.serviceName + " per week for</td>");
			buffer.push("<td><span class='space' /><input type='text' class='txtWeek' size='3' value='"
					+ plan.planItems[i].weeks + "' /> weeks</td>");
			if (!plan.active) {
				buffer.push("<td><span class='space' /><img src='/img/delete.png' class='imgDelete'/></td>");
			}
			buffer.push("</tr>");
		} else {
			buffer.push("<tr serviceid='" + service.id + "'>");
			buffer.push("<td colspan='2'><input type='text' class='txtCnt' size='3' style='display:none' value='1'/>");
			buffer.push("Number of Re-Exams during treatment</td>");
			buffer.push("<td><span class='space' /><input type='text' class='txtWeek' size='3' value='"
					+ planitem.weeks + "' /></td>");
			if (!plan.active) {
				buffer.push("<td><span class='space' /><img src='/img/delete.png' class='imgDelete'/></td>");
			}
			buffer.push("</tr>");
		}
	}
	/* Add discount and calculate button */
	buffer.push('</tbody></table>')
	buffer.push('</div>');
	buffer.push("<br />");
	buffer.push('Discount: <input type="text" size="10" id="txtDiscount' + plan.id + '" value="'
			+ (plan.discount > 0 ? plan.discount : "") + '" />  %');
	if (!plan.active) {
		buffer.push('<span class="space" />');
		buffer.push('<span class="space" />');
		buffer.push('<button id="btnCalculate' + plan.id + '">calculate</button>');
	}
	/* Add the summary table */
	buffer.push('<br /><br />');
	buffer.push('<div style="font-size:large">Summary:</div>');
	buffer.push('<br />');
	buffer.push('<div style="background-color:white">');
	buffer.push('<table id="tblServicesSummary' + plan.id + '" width="100%" align="top" cellspacing="0" cellpadding="0" style="table-layout:fixed;border-collapse:collapse;vertical-align:top;">');
	buffer.push('<thead>');
	buffer.push('<tr>');
	buffer.push('<th align="left">Service</th>');
	buffer.push('<th align="left"># of Session</th>');
	buffer.push('<th align="left">Charge per session</th>');
	buffer.push('<th align="left">Total</th>');
	buffer.push('</tr>');
	buffer.push('</thead>');
	buffer.push('<tbody></tbody>');
	buffer.push('</table>');
	buffer.push('</div>');
	buffer.push('<br />');
	/* Add totals */
	buffer.push('<div style="width:90%;text-align:right;">');
	buffer.push('<span style="font-weight:bold">Total cost:</span>');
	buffer.push('<span id="spntotal' + plan.id + '">&nbsp;&nbsp;</span>');
	buffer.push('<br />');
	buffer.push('<span style="font-weight:bold">Discount %</span>');
	buffer.push('<span id="spndiscount' + plan.id + '">&nbsp;&nbsp;</span>');
	buffer.push('<br />');
	buffer.push('<span style="font-weight:bold">Total payable:</span>');
	buffer.push('<span id="spnpayable' + plan.id + '">&nbsp;&nbsp;</span>');
	buffer.push('</div>');
	buffer.push('<button id="btnPrint' + plan.id + '">Print</button>');
	if(!plan.active) {
		buffer.push('<button id="btnSave' + plan.id + '">Save</button>');
		buffer.push('<button id="btnActive' + plan.id + '">Activate</button>');
		buffer.push('<button id="btnArchive' + plan.id + '">Archive</button>');
	}
	buffer.push('</fieldset>');
	/* Add buttons */

	$("#mainDiv").append(buffer.join(""));

	$("#tblServicesList" + plan.id + " > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesList" + plan.id + " > tbody > tr:odd").addClass("z-listbox-odd");
	calculateService(plan.id);
	if (plan.active) {
		$("#" + plan.id + " :text").attr("disabled", "disabled");
	}

	/* Attach event handlers */
	$("#btnPrint" + plan.id).click(function(evt) {
		printSummary(plan.id);
	});
	if (!plan.active) {
		$("#btnAdd" + plan.id).click(function(evt) {
			addService(plan.id);
		});
		$("#btnCalculate" + plan.id).click(function(evt) {
			calculateService(plan.id);
		});
		$("#btnSave" + plan.id).click(function(evt) {
			prepareSave(plan.id);
			$("$txtAction").val("Save:" + plan.id).blur();
		});
		$("#btnActive" + plan.id).click(function(evt) {
			$("$txtAction").val("Activate:" + plan.id).blur();
		});

		$("#btnArchive" + plan.id).click(function(evt) {
			$("$txtAction").val("Archive:" + plan.id).blur();
		});
	}
	
}

/**
 * This function will be triggered when user clicks on calculate button. This
 * will summarize all the services.
 * 
 * @return
 */
function calculateService(id) {
	/* check wheter any service being added or not */
	if ($("#tblServicesList" + id +" > tbody > tr").length <= 0) {
		alert("Add services before clicking on calculate");
		return;
	}
	var cumulativeList = {};
	$("#tblServicesList" + id +" > tbody > tr").each(function() {
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
	$("#tblServicesSummary" + id +" > tbody").html("");
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
		$("#tblServicesSummary" + id + " > tbody").append(buffer.join(""));
		totalCost += service.charge * cumulativeList[serviceid].total;
	}
	$("#tblServicesSummary" + id + " > tbody > tr > td:nth-child(4)").formatCurrency()
	$("#tblServicesSummary" + id + " > tbody > tr > td:nth-child(3)").formatCurrency()
	$("#tblServicesSummary" + id + " > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesSummary" + id + " > tbody > tr:odd").addClass("z-listbox-odd");
	$("#txtPlanLength" + id).val(maxSession);

	/* populate the totals */
	$("#spntotal" + id).html(totalCost).formatCurrency();
	$("#spndiscount" + id).html($("#txtDiscount" + id).val());
	var discount = $("$txtDiscount").val();
	if (!isNaN(parseFloat(discount))) {
		totalCost -= (totalCost * discount / 100);
	}
	$("#spnpayable" + id).html(totalCost).formatCurrency();
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
 * This function opens a new browser window with the print summary
 * 
 * @return
 */
function printSummary(id) {
	/* check whether it's ready to show the print page or not */
	if (isNaN(parseFloat($("#spntotal" + id).html().substring(1)))) {
		alert("Click Caculate button before printing");
		return;
	}
	/* date in mm/dd/yyyy format */
	var plandate = $("#" + id + " > legend").text();

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
	buffer.push("For:<span style='display:inline-block;width:150px;border-bottom:1px solid black;'>&nbsp;&nbsp;"
			+ custname + "</span>");
	buffer.push("Date:<span style='display:inline-block;width:100px;border-bottom:1px solid black;'>&nbsp;&nbsp;"
			+ plandate + "</span><br /><br />");
	/* show the treatment plan length */
	buffer.push("Treatment Plan Length: <span style='border-bottom:1px solid black;'>" + $("#txtPlanLength" + id).val()
			+ "</span>&nbsp;weeks<br /><br />");
	/* prepare the summary breakdown */
	$("#tblServicesList" + id +" > tbody > tr").each(
			function() {
				var count = parseInt($(this).find("input[class='txtCnt']").val());
				var week = parseInt($(this).find("input[class='txtWeek']").val());
				var serviceid = $(this).attr("serviceid");
				var service = getServiceById(serviceid);
				if (service.serviceName != "Re-Exam") {
					buffer.push(count + "&nbsp;" + service.serviceName + "&nbsp; Session per week for " + week
							+ "&nbsp; week" + (week > 1 ? "s" : ""));
				} else {
					buffer.push("Number of Re-Exams during treatment&nbsp;" + week);
				}
				buffer.push("<br />");
			});
	buffer.push("<p></p>");
	buffer.push("<span style='font-weight:bold'>Total Treatments:</span><br /><br />");
	buffer.push("<table>");
	/* prepare the cost of session */
	$("#tblServicesSummary" + id + " > tbody > tr").each(function() {
		var cell1 = $(this).find("td:first").html().split(":");
		var cost = $(this).find("td:nth-child(2)").html();
		var total = $(this).find("td:last").html();
		buffer.push("<tr><td>");
		buffer.push(cell1[1]);
		buffer.push("</td><td><span style='display:inline-block;width:20px'>&nbsp;</td>");
		buffer.push("<td>");
		buffer.push(cost);
		buffer.push("&nbsp;X&nbsp;");
		buffer.push(cell1[0]);
		buffer.push("&nbsp;=&nbsp;");
		buffer.push(total);
		buffer.push("</td></tr>");
	});
	buffer.push("<tr><td span='3'>&nbsp;</td></tr>");
	buffer.push("<tr><td span='3'>&nbsp;</td></tr>");
	/* prepare the final totals */
	buffer.push("<tr><td style='font-weight:bold;'>Total</td><td>" + $("#spntotal" + id).html() + "</td></tr>");
	buffer.push("<tr><td style='font-weight:bold;'>Discount%</td><td>" + $("#spndiscount" + id).html()
			+ "</td><td></td></tr>");
	buffer.push("<tr><td style='font-color:blue;font-weight:bold;'>Total Payable</td><td>" + $("#spnpayable" + id).html()
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

/**
 * This function will be called when user clicks on Add button
 * 
 * @return
 */
function addService(id) {
	var services = $.data(document.body, "servicelist");
	/* Check whether services does exist and is array */
	if (!$.isArray(services) || services.length <= 0) {
		return;
	}
	/* If user hasen't select any service alert him */
	var cmbServices = $("#cmbService" + id);
	console.log(cmbServices);
	if (cmbServices.val() == "-1") {
		alert("Please select a service to add");
		return;
	}
	var selService = getSelectedService(services, cmbServices.val());
	/* prepare the row and append to the tblServicesBreakDown table */
	var buffer = new Array();
	if (selService.serviceName != "Re-Exam") {
		buffer.push("<tr serviceid='" + selService.id + "'>");
		buffer.push("<td><input type='text' class='txtCnt' size='3' value='1'/><span class='space' /></td>");
		buffer.push("<td>" + selService.serviceName + " per week for</td>");
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
	var row = $("#tblServicesList" + id + " > tbody").append(buffer.join(""));
	$("#tblServicesList" + id + " > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesList" + id + " > tbody > tr:odd").addClass("z-listbox-odd");
}

/**
 * This method prepares the service list dropdown
 * 
 * @param buffer
 * @return
 */
function addServiceDropdown(buffer, plan) {
	if (plan.active) {
		return;
	}
	buffer.push('<span class="space" /><span class="space" />Add a service : ');
	buffer.push('<select id="cmbService' + plan.id + '">');
	buffer.push('<option value="-1"></option>');
	var services = $.data(document.body, "servicelist");
	for ( var i = 0, len = services.length; i < len; i++) {
		buffer.push('<option value="' + services[i].id + '">' + services[i].serviceName + '</option>');
	}
	buffer.push('</select>');
	buffer.push('<span class="space"/>');
	buffer.push('<span class="space"/>');
	buffer.push('<button id="btnAdd' + plan.id + '">Add</button>');
}

/**
 * This funciton will check whether calculation is happened before saving and
 * also make sure patient is selected. This will create a json object of all the
 * services and then it will be passed to the server.
 * 
 * @return
 */
function prepareSave(id) {
	/* do the validations */
	if(isNaN(parseFloat($("#spntotal" + id).html().substring(1)))) {
		alert("Click Caculate button before saving");
		evt.stopPropagation();
		evt.preventDefault();
		return false;
	}

	/* prepare the json data object */
	var arrServices = new Array();
	$("#tblServicesList" + id + " > tbody > tr").each(function() {
		var count = parseInt($(this).find("input[class='txtCnt']").val());
		var week = parseInt($(this).find("input[class='txtWeek']").val());
		var serviceid = $(this).attr("serviceid");
		arrServices.push({"serviceid": serviceid, "count":count, "weeks": week});
	});
	var planlen = $("#txtPlanLength" + id).val();
	var discount = $("#txtDiscount" + id).val();
	var obj = {'planLength': planlen, 'discount': discount, 'planItems': arrServices};
	$("$txtPaymentData").val($.toJSON(obj)).blur();
	return true;
}

function DeleteRow() {
	var id = $(this).parents("table").attr("id").substring("tblServicesList".length);
	$(this).parents("tr").remove();
	$("#tblServicesList" + id + " > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesList" + id + " > tbody > tr:odd").addClass("z-listbox-odd");
}

/**
 * This function loops through all the services and find the service that
 * matches with the service that is selected in combobox
 * 
 * @return
 */
function getSelectedService(services, selServiceName) {
	for ( var i = 0, len = services.length; i < len; i++) {
		if (services[i].id == selServiceName) {
			return services[i];
		}
	}
	return {};
}