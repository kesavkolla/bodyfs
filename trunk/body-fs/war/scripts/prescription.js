function initJS() {
	console.log("init js");
	console.log($("#btnAdd"));
	$("#btnAdd").click(AddPortion);
	$(".imgDelete").live("click", DeleteRow);
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
	$.data(document.body, data);
}

function AddPortion() {
	console.log("AddPortion");
	var cmbDiagnosis = zk.Widget.$($("$cmbDiagnosis").attr("id"));
	var data = $.data(document.body, cmbDiagnosis.getValue());
	if (isEmptyObject(data) || isEmptyObject(data.formulas)) {
		return;
	}
	if (data.loaded) {
		alert("Already added this diagnosis");
		return;
	}
	var formulas = data.formulas;
	var herbs = data.herbs;
	var portion = $("$txtPortion").val();
	for ( var i = 0, len = formulas.length; i < len; i++) {
		for ( var j = 0, len1 = herbs.length; j < len1; j++) {
			$("#herbsTable").append(
					"<tr><td>" + formulas[i].name + "</td>" + "<td>" + herbs[j].name + "</td>"
							+ "<td><input type='text' value='" + portion + "' /></td>"
							+ "<td><img src='/img/delete.png' class='imgDelete'/></tr>");
		}
	}
	$.data(document.body, cmbDiagnosis.getValue(), $.extend(data, {
		"loaded" : true
	}));
}

function DeleteRow() {
	$(this).parents("tr").remove();
}