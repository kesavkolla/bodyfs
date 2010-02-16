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
				+ "</span><small class='close' title='close'>x</small></label></span>");
		txtFormulaIds.push(formulaid);
		$("$txtFormulaIds").val(txtFormulaIds.join(","));
	});

	/*
	 * Attach the live handler for close on the tagbox
	 */
	$("small.close").live("click", function() {
		$(this).parent().parent().remove();
	});
});