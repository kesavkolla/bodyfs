/*
 * $Id$
 */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.api.A;
import org.zkoss.zul.api.Groupbox;
import org.zkoss.zul.api.Progressmeter;
import org.zkoss.zul.api.Window;

import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;

/**
 * 
 * @author kesav
 * 
 */
public class PatientViewComposer extends GenericAutowireComposer {

	private static final long serialVersionUID = 1503608767014635637L;
	private static Log LOGGER = LogFactory.getLog(PatientViewComposer.class);
	private Label week;
	private Label nextApppointment;
	private Label nextReExam;
	private A newemails;
	private Progressmeter pm;
	private Groupbox gbox;

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
		pageScope.put("CURRENT_PATIENT_ID", id);
		this.sessionScope.put("patid", pageScope.get("CURRENT_PATIENT_ID"));
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final Person person = personDAO.getPerson(id);
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final int numweek = visitDAO.countPatientVisits(person.getId());
		final int totalWeeks = 10;
		if (gbox != null) {
			gbox.getCaptionApi().setLabel("Report Card: " + person.getFirstName() + " " + person.getLastName());
		}
		if (week != null) {
			week.setValue(numweek + "");
		}
		if (nextApppointment != null) {
			nextApppointment.setValue("12/12/2009 - 3:15 pm");
		}
		if (nextReExam != null) {
			nextReExam.setValue("12/12/2009");
		}
		if (newemails != null) {
			newemails.setLabel("3 New emails");
		}
		if (pm != null) {
			if (numweek > 0) {
				int percent = Math.round(numweek * 100 / totalWeeks);
				pm.setValue(percent);
			} else {
				pm.setValue(0);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void onHandleClick(final ForwardEvent event) {
		final String pageid = (String) event.getData();
		if (pageid == null || pageid.equals("")) {
			return;
		}
		if (pageid.equals("weeklyvisit")) {
			this.sessionScope.put("patid", pageScope.get("CURRENT_PATIENT_ID"));
		}
		execution.sendRedirect("/index.zul#" + pageid);
	}

	public void onCreateLogin(final ForwardEvent event) {
		LOGGER.error("create Login");
		final Window win = (Window) Executions.createComponents("/pages/usermgmt/createLoginInfo.zul", null, null);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
