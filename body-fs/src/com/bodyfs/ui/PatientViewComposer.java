/*
 * $Id$
 */
package com.bodyfs.ui;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jsr107cache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.api.A;
import org.zkoss.zul.api.Progressmeter;
import org.zkoss.zul.api.Window;

import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;
import com.bodyfs.model.QuickPatient;
import com.bodyfs.model.payments.PatientPaymentPlan;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class PatientViewComposer extends GenericAutowireComposer {

	public static final String QUICK_PATIENT_LIST = "com.bodyfs.quickpatientlist";

	private static final long serialVersionUID = 1503608767014635637L;
	private static Log LOGGER = LogFactory.getLog(PatientViewComposer.class);
	private Label week;
	private Label totalWeeks;
	private Label nextApppointment;
	private Label nextReExam;
	private A newemails;
	private Progressmeter pm;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {

		super.doAfterCompose(comp);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside compose");
		}
		Long id = CommonUtils.getPatientId();
		if (id == null) {
			return;
		}
		LOGGER.debug("Retrieving the details for: " + id);
		pageScope.put("CURRENT_PATIENT_ID", id);
		this.sessionScope.put("patid", pageScope.get("CURRENT_PATIENT_ID"));
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final Person person = personDAO.getPerson(id);
		
		
		// If the savepat is true then create entry for the quickpatient
		if (execution.getParameter("savepat") != null && execution.getParameter("savepat").equals("true")) {
			final QuickPatient qp = new QuickPatient();
			qp.setId(person.getId());
			qp.setName(person.getDisplayName());
			personDAO.createQuickPatient(qp);
			// remove cache
			final Cache cache = (Cache) SpringUtil.getBean("datacache");
			if (cache.containsKey(QUICK_PATIENT_LIST)) {
				cache.remove(QUICK_PATIENT_LIST);
			}
		}

		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final int numweek = visitDAO.countPatientVisits(person.getId());
		final int totalPlanLength = getTotalPlansLength(person.getId());
		final Div rpthead = (Div) Path.getComponent(this.page, "rpthead");
		rpthead.appendChild(new Html("Report Card: " + person.getFirstName() + " " + person.getLastName()));

		if (week != null) {
			week.setValue(numweek + "");
		}
		if (totalWeeks != null) {
			totalWeeks.setValue(totalPlanLength + "");
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
				int percent = Math.round(numweek * 100 / totalPlanLength);
				pm.setValue(percent);
			} else {
				pm.setValue(0);
			}
		}
	}
	
	private int getTotalPlansLength(Long personID) {
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		Collection<PatientPaymentPlan> allPlans = paymentDAO.getAllPlans(personID);
		int total = 0; 
		for (PatientPaymentPlan patientPaymentPlan : allPlans) {
			total += patientPaymentPlan.getPlanLength() != null ? patientPaymentPlan.getPlanLength() : 0 ;
		}
		
		return total;
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
