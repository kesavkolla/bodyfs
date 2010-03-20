/**
 * This function will be called as after mount
 * 
 * @return
 */
function initPage() {

	/* handle the click on add button */
	$("#btnAdd").click(function() {

	});
}

/**
 * This function is called from the afterCompose. This will keep the services
 * object into the jQuery cache. That will be used later on.
 */
function saveServices(services) {
	$.data(document.body, "servicelist", services);
}