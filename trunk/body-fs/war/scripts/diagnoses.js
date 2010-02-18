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
		/* Clear out all the values for the input fields */
		$("$txtFormulaIds").val("");
		$("$txtDiagnosisName").val("");
		$("$txtDescription").val("");
		$("$divformulas").html("");
		var bdformulas = zk.Widget.$($("$bdformulas"));
		bdformulas.setValue("");
		var lstformulas = zk.Widget.$($("$lstformulas").attr("id"));
		lstformulas.setSelectedIndex(-1);
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
		var widget = zk.Widget.$($("$cntdiv").attr("id"));
		widget.show();
		$("$cntdiv").hide().slideDown();
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