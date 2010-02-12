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
	var widget = zk.Widget.$(jqobj.attr("id"));
	var sclass = widget._sclass;
	if (sclass) {
		if (!pageurl) {
			zUtl.go("/pages/patient/patientview.zul?id=" + sclass);
		} else {
			zUtl.go(pageurl + "?id=" + sclass);
		}
	}
}

/**
 * This function setup the option panel
 * 
 * @return
 */
function setupPanel() {
	var panel = zk.Widget.$($("$optionsPanel").attr("id"));
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