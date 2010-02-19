zk.afterMount(function() {
	/* Get the image add icon and attach click handler to it */
	var imgAdd = $("$imgAdd");
	imgAdd.click(function(evt) {
		/* Prevent the default handlers on the image click */
		evt.preventDefault();
		evt.stopPropagation();
		/* Get the listbox and check for the selected item */
		var lstformulas = $("$lstformulas");
		var wgtformulas = zk.Widget.$(lstformulas.attr("id"));
		if (!wgtformulas.getSelectedItem()) {
			/* If there is no item selected raise an alert */
			jq.alert("Select the formula to add", {
				title : "Error",
				icon : "ERROR"
			});
			return;
		}
		/* Get the selected formula's name and create a tagbox with it */
		var selrow = $("#" + wgtformulas.getSelectedItem().uuid);
		/* Get the formula id of selected item */
		var formulaid = selrow.find("td:last>div.z-listcell-cnt").text();
		/* check whether the formula is already selected or not */
		var txtFormulaIds = $("$txtFormulaIds").val().split(",");
		if (jq.inArray(formulaid, txtFormulaIds) > -1) {
			jq.alert("Formula is already being added", {
				title : "Error",
				icon : "ERROR"
			});
			return;
		}
		var divformulas = $("$divformulas");
		divformulas.append("<span class='tag'><label><span>" + selrow.find("td:first>div.z-listcell-cnt").text()
				+ "</span><small class='close' title='close' formulaid='" + formulaid + "'>x</small></label></span>");
		txtFormulaIds.push(formulaid);
		$("$txtFormulaIds").val(txtFormulaIds.join(","));
	});

	/*
	 * Attach the live handler for close on the tagbox
	 */
	$("small.close").live("click", function() {
		var txtFormulaIds = $("$txtFormulaIds").val().split(",");
		var indx = jq.inArray($(this).attr("formulaid"), txtFormulaIds);
		txtFormulaIds.splice(indx, 1);
		$("$txtFormulaIds").val(txtFormulaIds.join(","));
		$(this).parent().parent().remove();
	});

	/*
	 * Handle the click on btnCancel
	 */
	$("$btnCancel").click(function() {
		clearData();
		var widget = zk.Widget.$($("$cntdiv").attr("id"));
		widget.hide();
	});

	/*
	 * Handle the click on create Do all the necessary validations before
	 * sending data to server
	 */
	$("$btnCreate").click(function(evt) {
		if (jq.trim($("$txtDiagnosisName").val()).length <= 0) {
			jq.alert("Please provide the Diagnosis name", {
				title : "Error",
				icon : "ERROR"
			});
			evt.preventDefault();
			evt.stopPropagation();
			$("$txtDiagnosisName").focus();
			return;
		}
		var formulaids = $("$txtFormulaIds").val().split(",")
		if (formulaids.length < 2) {
			jq.alert("Select atleast one formula to the diagnosis", {
				title : "Error",
				icon : "ERROR"
			});
			evt.preventDefault();
			evt.stopPropagation();
			return;
		}
		$("$txtFormulaIds").blur();
	});

	/*
	 * Handle the click event on btnAdd
	 */
	$("$btnAdd").click(function() {
		clearData();
		var widget = zk.Widget.$($("$cntdiv").attr("id"));
		widget.show();
		$("$cntdiv").hide().slideDown();
	});

	$(".btnEdit").live("click", function() {
		var btnEdit = zk.Widget.$($(this).attr("id"));
		var lstdiagnosis = zk.Widget.$($("$lstdiagnosis").attr("id"));
		lstdiagnosis.setSelectedItem(btnEdit.parent.parent.parent);
	});

	$(".btnDelete").live("click", function() {
		var btnEdit = zk.Widget.$($(this).attr("id"));
		var lstdiagnosis = zk.Widget.$($("$lstdiagnosis").attr("id"));
		lstdiagnosis.setSelectedItem(btnEdit.parent.parent.parent);
	});

});

/**
 * This will be invoked by the server when it finishes saving successfully.
 * 
 * @return
 */
function onSave() {
	$.jGrowl('Successfully saved diagnosis', {
		life : 3000
	});
	$("$btnCancel").click();
}

/**
 * This function clears all the data fields
 * 
 * @return
 */
function clearData() {
	/* Clear out all the values for the input fields */
	var txtDiagnosisName = zk.Widget.$($("$txtDiagnosisName").attr("id"));
	txtDiagnosisName.setValue("", true);

	var txtDescription = zk.Widget.$($("$txtDescription").attr("id"));
	txtDescription.setValue("", true);

	var txtDiagnosisId = zk.Widget.$($("$txtDiagnosisId").attr("id"));
	txtDiagnosisId.setValue("", true);

	$("$divformulas").html("");
	var bdformulas = zk.Widget.$($("$bdformulas"));
	bdformulas.setValue("", true);

	var lstformulas = zk.Widget.$($("$lstformulas").attr("id"));
	lstformulas.setSelectedIndex(-1);
}

/**
 * 
 * @param data
 * @return
 */
function SetupEdid(data) {
	/* Setup id */
	var txtDiagnosisId = zk.Widget.$($("$txtDiagnosisId").attr("id"));
	txtDiagnosisId.setValue(data.id, true);
	/* Setup name */
	var txtDiagnosisName = zk.Widget.$($("$txtDiagnosisName").attr("id"));
	txtDiagnosisName.setValue(data.name, true);
	/* setup description */
	var txtDescription = zk.Widget.$($("$txtDescription").attr("id"));
	txtDescription.setValue(data.description, true);
	/* Setup the txtFormulaIds */
	var txtFormulaIds = zk.Widget.$($("$txtFormulaIds").attr("id"));
	var tmpText = data.formulas[0].id;
	for ( var i = 1, len = data.formulas.length; i < len; i++) {
		tmpText = tmpText + "," + data.formulas[i].id;
	}
	txtFormulaIds.setValue(tmpText, true);
	/* setup the formula names */
	var divformulas = $("$divformulas");
	divformulas.html("");
	for ( var i = 0, len = data.formulas.length; i < len; i++) {
		divformulas.append("<span class='tag'><label><span>" + data.formulas[i].name
				+ "</span><small class='close' title='close' formulaid='" + data.formulas[i].id
				+ "'>x</small></label></span>");
	}
	/* open the div */
	var widget = zk.Widget.$($("$cntdiv").attr("id"));
	widget.show();
	$("$cntdiv").hide().slideDown();
}