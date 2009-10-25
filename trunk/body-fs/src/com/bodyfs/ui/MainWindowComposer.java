package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Include;

public class MainWindowComposer extends GenericAutowireComposer {

	private static final long serialVersionUID = 7255967269134815134L;
	private static Log LOGGER = LogFactory.getLog(MainWindowComposer.class);

	private Include xcontents;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		LOGGER.fatal("Entering after compose");
		super.doAfterCompose(comp);
	}

	public void onHandleClick(ForwardEvent event) {
		final String id = event.getOrigin().getTarget().getId();
		LOGGER.error("Event id: " + id);
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
