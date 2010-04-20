function initPage() {

	/* create the annotate image */
	var annoimg = jq("$imgbody").annotateImage( {
		editable : true,
		useAjax : false
	});

	/* handle click on save button and save all the image annotations to the textbox */
	$("$btnSave").click(function() {
		var notes = $("$imgbody").annotateImage("getNotes");
		if (notes.length > 0) {
			$("$txtAnnotations").val(toJSON(notes)).blur();
		} else {
			$("$txtAnnotations").val("").blur();
		}
		return true;
	});

	$("$btnCancel").click(function() {
		zUtl.go('/pages/signin/customersearch.zul');
		return false;
	});
}

/**
 * 
 * @return
 */
function showConfirmation() {
	alert('Successfully saved the information');
	zUtl.go('/pages/signin/customersearch.zul');
}
