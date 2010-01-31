/* $Id$ */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import com.bodyfs.Constants;
import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.PatientTreatment;
import com.bodyfs.model.PersonType;
import com.bodyfs.ui.util.CommonUtils;

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
		final Long patid = CommonUtils.getPatientId();
		if (patid == null) {
			if (((PersonType) session.getAttribute(Constants.SESSION_PERSON_TYPE)) == PersonType.EMPLOYEE) {
				execution.sendRedirect("/pages/usermgmt/customersearch.zul");
				return;
			} else {
				execution.sendRedirect("/pages/user/index.zul");
				return;
			}
		}

		// If there are no patient visits send to sign-in page
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		if (visitDAO.countPatientVisits(patid) < 1) {
			if (((PersonType) session.getAttribute(Constants.SESSION_PERSON_TYPE)) == PersonType.EMPLOYEE) {
				execution.sendRedirect("/pages/patient/weeklyvisit.zul?id=" + patid);
				return;
			} else {
				execution.sendRedirect("/pages/user/index.zul");
				return;
			}
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
		final Long personId = CommonUtils.getPatientId();
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

	/**
	 * 
	 * @param event
	 */
	public void onSave(final ForwardEvent event) {
		if (page.getAttribute("patid") == null || page.getAttribute("treatment") == null) {
			return;
		}
		final PatientTreatment treatment = (PatientTreatment) page.getAttribute("treatment");
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		LOGGER.debug("Updating the treatment");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(treatment);
		}
		visitDAO.createPatientTreatment(treatment);
		Clients.evalJavaScript("$.jGrowl('Successfully saved the data', {life:3000})");
	}

	/**
	 * This event handler handles the click on pagination of dates When ever a
	 * new date is selected the corresponding patient diagnosis data is
	 * retrieved and set to the jsodata
	 * 
	 * @param evt
	 */
	public void onDateChange(final ForwardEvent evt) {
		final Textbox datebox = (Textbox) evt.getOrigin().getTarget();
		if (datebox == null || datebox.getValue() == null) {
			return;
		}
		final Date visitDate = new Date(new Long(datebox.getValue()));
		final Component treatmentgrid = evt.getTarget();
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final PatientTreatment treatment = visitDAO.getPatientTreatmentByDate((Long) page.getAttribute("patid"),
				visitDate);
		if (treatment == null) {
			return;
		}

		final DataBinder binder = (DataBinder) treatmentgrid.getAttribute("binder");
		if (binder != null) {
			treatmentgrid.setAttribute("treatment", treatment);
			page.setAttribute("treatment", treatment);
			binder.loadAll();
		}

		Clients.evalJavaScript("reloadMarkers()");
	}

}
