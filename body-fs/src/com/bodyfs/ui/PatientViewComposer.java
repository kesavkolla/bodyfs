/*
 * $Id$
 */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.PatientVisit;

/**
 * 
 * @author kesav
 * 
 */
public class PatientViewComposer extends GenericAutowireComposer {

	private static final long serialVersionUID = 1503608767014635637L;
	private static Log LOGGER = LogFactory.getLog(PatientViewComposer.class);
	final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private Listbox visitdates;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside compose");
		}
		if (!this.param.containsKey("id")) {
			return;
		}
		Long id = -1L;
		try {
			id = Long.parseLong(this.param.get("id").toString());
		} catch (final Throwable t) {
			return;
		}
		LOGGER.debug("Retrieving the details for: " + id);
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final Collection<PatientVisit> visits = personDAO.GetPatientVisits(id);
		for (final PatientVisit visit : visits) {
			final Listitem item = new Listitem(sdf.format(visit.getVisitDate()));
			this.visitdates.getItems().add(item);
		}
	}

	public void onHandleClick(final ForwardEvent event) {
		final String pageid = (String) event.getData();
		if (pageid == null || pageid.equals("")) {
			return;
		}
		execution.sendRedirect("/index.zul#" + pageid);
	}
}
