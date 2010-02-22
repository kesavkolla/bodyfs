function initJS() {
	$("#btnAdd").click(AddPortion);
	$(".imgDelete").live("click", DeleteRow);
}

function DisplayData(data) {
	var cmbDiagnosis = zk.Widget.$($("$cmbDiagnosis").attr("id"));
	$.data(document.body, cmbDiagnosis.getValue(), data);
	$("$divFormulas").html($.map(data.formulas, function(formula) {
		return formula.name;
	}).join("<br>"));

	$("$divHerbs").html(data.herbs.map(function(herb) {
		return herb.name;
	}).join("<br />"));
}

function AddPortion() {
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