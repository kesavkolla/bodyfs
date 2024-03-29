/*
 * $Id$
 */
package com.bodyfs.ui;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.api.Progressmeter;
import org.zkoss.zul.api.Window;

import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;
import com.bodyfs.model.payments.PatientPaymentPlan;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class PatientViewComposer extends GenericAutowireComposer {

	private static final long serialVersionUID = 1503608767014635637L;
	private static Log LOGGER = LogFactory.getLog(PatientViewComposer.class);
	private Label week;
	private Label totalWeeks;
	private Label nextApppointment;
	private Label sessionsUsed;
	private Label lastExamDate;
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
		final Person person = personDAO.getPerson(id, true);

		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final int numweek = visitDAO.countPatientVisits(person.getId());
		final int totalPlanLength = getTotalPlansLength(person.getId());

		if (week != null) {
			week.setValue(numweek + "");
		}
		if (sessionsUsed != null) {
			sessionsUsed.setValue(numweek + "");
		}
		if (totalWeeks != null) {
			totalWeeks.setValue(totalPlanLength + "");
		}
		if (numweek == 0) {
			if (nextApppointment != null) {
				nextApppointment.setValue("8 more sessions to go.");
			}
			if (lastExamDate != null) {
				lastExamDate.setValue("-");
			}

		} else {
			int modulus = numweek % 8;
			if (nextApppointment != null) {
				if (totalPlanLength - numweek >= 8)
					nextApppointment.setValue(8 - modulus + " more sessions to go.");
				else
					nextApppointment.setValue("-");
			}
			if (lastExamDate != null) {
				if (numweek > 8)
					lastExamDate.setValue(modulus + " sessions ago.");
				else
					lastExamDate.setValue("-");
			}
		}

		if (pm != null) {
			if (numweek > 0 && totalPlanLength > 0) {
				int percent = Math.round(numweek * 100 / totalPlanLength);
				pm.setValue(percent > 100 ? 100 : percent);
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
			total += patientPaymentPlan.getPlanLength() != null ? patientPaymentPlan.getPlanLength() : 0;
		}

		return total;
	}

	@SuppressWarnings("unused")
	private double getAmmountInAccount(Long personID) {
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		Collection<PatientPaymentPlan> allPlans = paymentDAO.getAllPlans(personID);
		int total = 0;
		for (PatientPaymentPlan patientPaymentPlan : allPlans) {
			total += patientPaymentPlan.getPlanLength() != null ? patientPaymentPlan.getPlanLength() : 0;
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
