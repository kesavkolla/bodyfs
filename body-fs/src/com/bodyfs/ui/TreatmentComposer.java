/* $Id$ */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;

import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.PatientTreatment;

/**
 * 
 * @author kesav
 * 
 */
public class TreatmentComposer extends GenericForwardComposer {
	private static final long serialVersionUID = 8736551335071256485L;
	private transient static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static Log LOGGER = LogFactory.getLog(TreatmentComposer.class);

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		// Do the sanity checks
		// If the id is not present in the URL send to customer search
		if (execution.getParameter("id") == null || execution.getParameter("id").trim().equals("")) {
			execution.sendRedirect("/pages/usermgmt/customersearch.zul");
			return;
		}
		final Long patid = new Long(execution.getParameter("id"));
		// If there are no patient visits send to sign-in page
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		if (visitDAO.countPatientVisits(patid) < 1) {
			execution.sendRedirect("/pages/patient/weeklyvisit.zul");
			return;
		}

		page.setAttribute("patid", patid);
		Date visitDate = null;
		if (execution.getParameter("visitDate") != null) {
			try {
				visitDate = new Date(new Long(execution.getParameter("visitDate")));
			} catch (final Throwable t) {
				visitDate = null;
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Patid: " + patid + "\tvisitDate:" + visitDate);
		}
		final PatientTreatment treatment = visitDAO.getPatientTreatmentByDate(patid, visitDate);
		this.page.setAttribute("treatment", treatment);
	}

	/**
	 * This method returns the dates of the patient visits for the current
	 * patient id
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final String getVisitsDates() {
		final Long personId = Long.parseLong(Executions.getCurrent().getParameter("id"));
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final JSONArray arr = new JSONArray();
		for (final Date date : visitDAO.getPatientVisitDates(personId)) {
			final JSONObject obj = new JSONObject();
			obj.put("value", sdf.format(date));
			obj.put("date", date.getTime());
			arr.add(obj);
		}
		return arr.toJSONString();
	}

}
