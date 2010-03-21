/**
 * This method gets executed after the zk.mount
 * 
 * @return
 */
function setupData(reload) {
	if (reload) {
		// clearFields();
	} else {
		initPage();
	}

	/* Get the data from the jsondata textfield and setup all the input elements */
	var wgt = zk.Widget.$($("$txtPaymentData").attr("id"));
	var paymentdata = {};
	if ($.isEmptyObject(wgt)) {
		paymentdata = [];
	} else {
		paymentdata = parseJSON(wgt.getValue());
	}

	/* setup the plan break down and summay tables */
	$("#tblServicesBreakDown > tbody").html("");
	$("#tblServicesSummary > tbody").html("");

	/* Populate the tblServicesBreakDown from payment data */
	$.each(paymentdata, function() {
		var service = getServiceById(this.serviceid);
		var buffer = new Array();
		buffer.push("<tr serviceid='" + this.serviceid + "'>");
		buffer.push("<td><input type='text' class='txtCnt' readonly='readonly' value='" + this.count + "'/></td>");
		buffer.push("<td>" + service.serviceName + " per weeks for</td>");
		buffer.push("<td><input type='text' class='txtWeek' readonly='readonly' value='" + this.weeks + "' /></td>");
		buffer.push("</tr>");
		$("#tblServicesBreakDown > tbody").append(buffer.join());
	});
	$("#tblServicesBreakDown > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesBreakDown > tbody > tr:odd").addClass("z-listbox-odd");

	/* Populate the tblServicesSummary table */
	populateSummary();
}

/**
 * This function will be triggered when user clicks on calculate button. This
 * will summarize all the services.
 * 
 * @return
 */
function populateSummary() {
	/* check wheter any service being added or not */
	if ($("#tblServicesBreakDown > tbody > tr").length <= 0) {
		return;
	}
	var cumulativeList = {};
	$("#tblServicesBreakDown > tbody > tr").each(function() {
		var count = parseInt($(this).find("input[class='txtCnt']").val());
		var week = parseInt($(this).find("input[class='txtWeek']").val());
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
		buffer.push("<td style='height:25px'>" + cumulativeList[serviceid].total + ":" + service.serviceName + "</td>");
		buffer.push("<td>" + service.charge + "</td>");
		buffer.push("<td>" + service.charge * cumulativeList[serviceid].total + "</td>");
		buffer.push("</tr>")
		$("#tblServicesSummary > tbody").append(buffer.join());
		totalCost += service.charge * cumulativeList[serviceid].total;
	}
	$("#tblServicesSummary > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblServicesSummary > tbody > tr:odd").addClass("z-listbox-odd");
	$("#totalCost").html(totalCost);
}

/**
 * This function initializes the page events
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
	var selPaymenttDates = $("#selPaymenttDates");
	$.each(data, function() {
		selPaymenttDates.append('<option value="' + this.date + '" ' + ((start == this.date) ? "selected='true'" : "")
				+ '">' + this.value + '</option>');
	});

	/* Handle the click on View */
	$("#btnView").click(function() {
		var selPaymenttDates = $("#selPaymenttDates")[0];
		var selDate = selPaymenttDates.options[selPaymenttDates.selectedIndex].value;
		$("$txtPaymentDate").val(selDate).blur();
	});

	$("#btnPrint").click(function() {
		$.param.querystring()
		window.open($.param.querystring("printinvoice.jsp", $.deparam.querystring()));
	});
}

/**
 * This function retrieves the service by it's id from the data cache
 * 
 * @param serviceid
 * @return
 */
function getServiceById(serviceid) {
	for ( var i = 0, len = serviceslist.length; i < len; i++) {
		if (serviceslist[i].id == serviceid) {
			return serviceslist[i];
		}
	}
	return {};
}