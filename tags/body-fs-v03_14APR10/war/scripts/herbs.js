zk.afterMount(function() {

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
		if (jq.trim($("$txtHerbName").val()).length <= 0) {
			jq.alert("Please provide the herb name", {
				title : "Error",
				icon : "ERROR"
			});
			evt.preventDefault();
			evt.stopPropagation();
			$("$txtHerbName").focus();
			return;
		}
		$("$txtHerbId").blur();
	});

	$(".btnEdit").live("click", function() {
		var btnEdit = zk.Widget.$($(this).attr("id"));
		var lstherb = zk.Widget.$($("$lstherb").attr("id"));
		lstherb.setSelectedItem(btnEdit.parent.parent.parent);
	});

	$(".btnDelete").live("click", function() {
		var btnEdit = zk.Widget.$($(this).attr("id"));
		var lstherb = zk.Widget.$($("$lstherb").attr("id"));
		lstherb.setSelectedItem(btnEdit.parent.parent.parent);
	});

});

/**
 * This will be invoked by the server when it finishes saving successfully.
 * 
 * @return
 */
function onSave() {
	$.jGrowl('Successfully saved Herb', {
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
	$("$txtHerbName").val("").blur();
	$("$txtDescription").val("").blur();
	$("$txtHerbId").val("").blur();
}

function setupAdd() {
	var widget = zk.Widget.$($("$cntdiv").attr("id"));
	widget.show();
	$("$cntdiv").hide().slideDown();
}

/**
 * 
 * @param data
 * @return
 */
function SetupEdit() {
	/* open the div */
	var widget = zk.Widget.$($("$cntdiv").attr("id"));
	widget.show();
	$("$cntdiv").hide().slideDown();
}