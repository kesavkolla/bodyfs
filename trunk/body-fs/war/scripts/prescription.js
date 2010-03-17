function initJS() {
	$("#btnAdd").click(AddPortion);
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
	console.log(data);
	$.data(document.body, "formuladata", data);
}

function AddPortion() {
	var formuladata = $.data(document.body, "formuladata");
	console.log(formuladata);
	var loadedArr = $.data(document.body, "loadedformulas")
	console.log(loadedArr);
	if ($.inArray(formuladata.formula.id, loadedArr) != -1) {
		alert("Already added this formula");
		return;
	}

	var portion = $.trim($("$txtPortion").val());
	if (portion.length <= 0) {
		alert("Please enter the portion");
		return;
	}

	for ( var i = 0, len = formuladata.herbs.length; i < len; i++) {
		$("#herbsTable").append(
				"<tr><td>" + formuladata.formula.name + "</td>" + "<td>" + formuladata.herbs[i].name + "</td>"
						+ "<td><input type='text' value='" + portion + "' /></td>"
						+ "<td><img src='/img/delete.png' class='imgDelete'/></tr>");
	}

	loadedArr[loadedArr.length] = formuladata.formula.id;
	$.data(document.body, "loadedformulas", loadedArr);
}

function DeleteRow() {
	$(this).parents("tr").remove();
}