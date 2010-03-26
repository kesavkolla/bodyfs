function initPage() {

	/* create the annotate image */
	annoimg = jq("$imgbody").annotateImage( {
		editable : true,
		useAjax : false
	});

	/* handle click on save button and save all the image annotations to the textbox */
	$("$btnSave").click(function() {
		if (annoimg.notes.length > 0) {
			$("$txtAnnotations").val($.toJSON(annoimg.notes)).blur();
		} else {
			$("$txtAnnotations").val("").blur();
		}
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
	jq.alert('Successfully saved the information', {
		title : 'Success',
		mode : 'modal',
		OK : function() {
			zUtl.go('/pages/signin/customersearch.zul');
		}
	});
}
