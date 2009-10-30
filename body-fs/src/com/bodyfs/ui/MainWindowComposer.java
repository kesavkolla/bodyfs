package com.bodyfs.ui;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import com.dyuproject.openid.OpenIdUser;

public class MainWindowComposer extends GenericAutowireComposer {

	private static final long serialVersionUID = 7255967269134815134L;
	private static Log LOGGER = LogFactory.getLog(MainWindowComposer.class);

	private Include xcontents;
	private Label username;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final OpenIdUser user = (OpenIdUser) this.requestScope.get(OpenIdUser.ATTR_NAME);
		LOGGER.error("A: " + user.getA());
		LOGGER.error("Attributes: " + user.getAttributes());
		LOGGER.error("Extensions:" + user.getExtensions());
		final Map<String, String> userinfo = (Map<String, String>) user.getAttribute("info");
		if (userinfo != null) {
			username.setValue(userinfo.get("lastname") + ", " + userinfo.get("firstname"));
		}
	}

	public void onHandleClick(ForwardEvent event) {
		final String id = event.getOrigin().getTarget().getId();
		if (id.equals("btnCustomers")) {
			return;
		} else if (id.equals("btnScheduling")) {
			xcontents.setSrc("/WEB-INF/views/calendar.zul");
			return;
		} else if (id.equals("btnNPI")) {
			xcontents.setSrc("/WEB-INF/views/npi.zul");
			return;
		}
	}
}
