/*
 * $Id$
 */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.PatientVisit;

/**
 * 
 * @author kesav
 * 
 */
public class WeeklyVisitComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 3816734829122660780L;

	public final int getVisitsCount() {
		return 3;
	}

	private static Log LOGGER = LogFactory.getLog(WeeklyVisitComposer.class);

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		comp.getPage().setAttribute("patvisit", new PatientVisit());
		comp.getPage().setAttribute("personid", Executions.getCurrent().getParameter("id"));
		super.doAfterCompose(comp);

	}

	public void onSave(final ForwardEvent event) {
		if (page.getAttribute("personid") == null || page.getAttribute("patvisit") == null) {
			return;
		}
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final PatientVisit patvisit = (PatientVisit) page.getAttribute("patvisit");
		if (patvisit != null) {
			patvisit.setPersonId(Long.parseLong(page.getAttribute("personid").toString()));
			personDAO.createPatientVisit(patvisit);
		}
	}

	public void onNoteAdd(final Event event) {

		LOGGER.error(event.getData());
	}
}
