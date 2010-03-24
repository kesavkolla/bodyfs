zk.afterMount(function() {
	/* Get the image add icon and attach click handler to it */
	var imgAdd = $("$imgAdd");
	imgAdd.click(function(evt) {
		/* Prevent the default handlers on the image click */
		evt.preventDefault();
		evt.stopPropagation();
		/* Get the listbox and check for the selected item */
		var lstherbs = $("$lstherbs");
		var wgtherbs = zk.Widget.$(lstherbs.attr("id"));
		if (!wgtherbs.getSelectedItem()) {
			/* If there is no item selected raise an alert */
			jq.alert("Select the herb to add", {
				title : "Error",
				icon : "ERROR"
			});
			return;
		}
		/* Get the selected formula's name and create a tagbox with it */
		var selrow = $("#" + wgtherbs.getSelectedItem().uuid);
		/* Get the formula id of selected item */
		var herbid = selrow.find("td:last>div.z-listcell-cnt").text();
		/* check whether the formula is already selected or not */
		var txtherbIds = $("$txtherbIds").val().split(",");
		if (jq.inArray(herbid, txtherbIds) > -1) {
			jq.alert("Herb is already being added", {
				title : "Error",
				icon : "ERROR"
			});
			return;
		}
		var divherbs = $("$divherbs");
		divherbs.append("<span class='tag'><label><span>" + selrow.find("td:first>div.z-listcell-cnt").text()
				+ "</span><small class='close' title='close' formulaid='" + herbid + "'>x</small></label></span>");
		txtherbIds.push(herbid);
		$("$txtherbIds").val(txtherbIds.join(","));
	});

	/*
	 * Attach the live handler for close on the tagbox
	 */
	$("small.close").live("click", function() {
		var txtherbIds = $("$txtherbIds").val().split(",");
		var indx = jq.inArray($(this).attr("herbid"), txtherbIds);
		txtherbIds.splice(indx, 1);
		$("$txtherbIds").val(txtherbIds.join(","));
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
		if (jq.trim($("$txtFormulaName").val()).length <= 0) {
			jq.alert("Please provide the formula name", {
				title : "Error",
				icon : "ERROR"
			});
			evt.preventDefault();
			evt.stopPropagation();
			$("$txtFormulaName").focus();
			return;
		}
		var herbids = $("$txtherbIds").val().split(",")
		if (herbids.length == 0 || (herbids.length == 1 && herbids[0] == "")) {
			jq.alert("Select atleast one herb to the formula", {
				title : "Error",
				icon : "ERROR"
			});
			evt.preventDefault();
			evt.stopPropagation();
			return;
		}
		$("$txtherbIds").blur();
		$("$txtFormulaId").blur();
	});

	$(".btnEdit").live("click", function() {
		var btnEdit = zk.Widget.$($(this).attr("id"));
		var lstformula = zk.Widget.$($("$lstformula").attr("id"));
		lstformula.setSelectedItem(btnEdit.parent.parent.parent);
	});

	$(".btnDelete").live("click", function() {
		var btnEdit = zk.Widget.$($(this).attr("id"));
		var lstformula = zk.Widget.$($("$lstformula").attr("id"));
		lstformula.setSelectedItem(btnEdit.parent.parent.parent);
	});

});

/**
 * This will be invoked by the server when it finishes saving successfully.
 * 
 * @return
 */
function onSave() {
	$.jGrowl('Successfully saved Formula', {
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
	$("$txtFormulaName").val("").blur();
	$("$txtDescription").val("").blur();
	$("$txtFormulaId").val("").blur();

	$("$divherbs").html("");
	var bdherbs = zk.Widget.$($("$bdherbs"));
	bdherbs.setValue("");

	var lstherbs = zk.Widget.$($("$lstherbs").attr("id"));
	lstherbs.setSelectedIndex(-1);
}

function setupAdd() {
	var widget = zk.Widget.$($("$cntdiv").attr("id"));
	widget.show();
	$("$divherbs").html("");
	$("$cntdiv").hide().slideDown();
}

/**
 * 
 * @param data
 * @return
 */
function SetupEdit(data) {
	/* setup the formula names */
	var divherbs = $("$divherbs");
	divherbs.html("");
	for ( var i = 0, len = data.length; i < len; i++) {
		divherbs.append("<span class='tag'><label><span>" + data[i].name
				+ "</span><small class='close' title='close' herbid='" + data[i].id + "'>x</small></label></span>");
	}
	/* open the div */
	var widget = zk.Widget.$($("$cntdiv").attr("id"));
	widget.show();
	$("$cntdiv").hide().slideDown();
}