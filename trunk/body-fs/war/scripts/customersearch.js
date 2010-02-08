/**
 * This function setup the live events to the person row
 * 
 * @return
 */
function setupResultsClick() {
	$("$persons").find("tr.z-listitem").live("dblclick", function() {
		openPatient($(this));
	});
	setupPanel();
}

/**
 * This function navigates the selected patient
 * 
 * @param zkobj
 * @return
 */
function openPatient(jqobj) {
	var sclass = jqobj.zk.widget()[0]._sclass;
	if (sclass) {
		window.location.href = "/pages/patient/patientview.zul?id=" + sclass;
	}
}

/**
 * This function setup the option panel
 * 
 * @return
 */
function setupPanel() {
	var panel = zk("$optionsPanel").widget()[0];
	var node = panel.$n();
	var body = jq(panel.$n('body'));
	var zcls = panel.getZclass();
	jq(node).addClass(zcls + '-colpsd');
	panel._hideShadow();
	if (zk.ie6_ && !node.style.width) {
		node.runtimeStyle.width = "100%";
	}
	body.zk.slideUp(panel, {
		beforeAnima : panel._beforeSlideUp
	});
}