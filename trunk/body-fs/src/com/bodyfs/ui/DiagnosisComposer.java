/* $Id$ */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import com.bodyfs.Constants;
import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.PatientDiagnosis;
import com.bodyfs.model.PersonType;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class DiagnosisComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 6365381701330204030L;
	private transient static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static Log LOGGER = LogFactory.getLog(DiagnosisComposer.class);

	@Override
	public ComponentInfo doBeforeCompose(final Page page, final Component parent, final ComponentInfo compInfo) {
		final Session session = Sessions.getCurrent(false);
		final Execution execution = Executions.getCurrent();
		// Do the sanity checks
		// If the id is not present in the URL send to customer search
		final Long patid = CommonUtils.getPatientId();
		if (patid == null) {
			if (((PersonType) session.getAttribute(Constants.SESSION_PERSON_TYPE)) == PersonType.EMPLOYEE) {
				execution.sendRedirect("/pages/usermgmt/customersearch.zul");
				return null;
			} else {
				execution.sendRedirect("/pages/user/index.zul");
				return null;
			}
		}

		// If there are no patient visits send to sign-in page
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		if (visitDAO.countPatientVisits(patid) < 1) {
			if (((PersonType) session.getAttribute(Constants.SESSION_PERSON_TYPE)) == PersonType.EMPLOYEE) {
				execution.sendRedirect("/pages/patient/weeklyvisit.zul?id=" + patid);
				return null;
			} else {
				execution.sendRedirect("/pages/user/index.zul");
				return null;
			}
		}
		page.setAttribute("patid", patid);
		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final Long patid = (Long) page.getAttribute("patid");

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
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final PatientDiagnosis diagnosis = visitDAO.getPatientDiagnosisByDate(patid, visitDate);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(diagnosis);
		}
		if (diagnosis != null) {
			final Textbox txtjsondata = (Textbox) Path.getComponent(this.page, "jsondata");
			if (diagnosis.getDiagnosisData() != null) {
				txtjsondata.setText(diagnosis.getDiagnosisData());
			}
			final Textbox txtVisitDate = (Textbox) Path.getComponent(this.page, "txtVisitDates");
			txtVisitDate.setValue(diagnosis.getVisitDate().getTime() + "");
		}
	}

	/**
	 * Save the data to the corresponding PatientDiagnsotics object
	 * 
	 * @param event
	 */
	public void onSave(final ForwardEvent event) {
		final Textbox txtjsondata = (Textbox) Path.getComponent(this.page, "jsondata");
		final Textbox txtVisitDate = (Textbox) Path.getComponent(this.page, "txtVisitDates");

		final Date visitDate = new Date(new Long(txtVisitDate.getValue()));

		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final PatientDiagnosis diagnosis = visitDAO.getPatientDiagnosisByDate((Long) page.getAttribute("patid"),
				visitDate);
		diagnosis.setDiagnosisData(txtjsondata.getValue());
		visitDAO.createPatientDiagnosis(diagnosis);
		Clients.evalJavaScript("$.jGrowl('Successfully saved diagnosis', {life:3000})");
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
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final PatientDiagnosis diagnosis = visitDAO.getPatientDiagnosisByDate((Long) page.getAttribute("patid"),
				visitDate);
		if (diagnosis == null) {
			return;
		}
		final Textbox jsondata = (Textbox) Path.getComponent(this.page, "jsondata");
		jsondata.setText(diagnosis.getDiagnosisData() == null ? "" : diagnosis.getDiagnosisData());
		Clients.evalJavaScript("setupData(true)");
	}

	/**
	 * This method returns the dates of the patient visits for the current
	 * patient id
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final String getVisitsDates() {
		final Long patid = CommonUtils.getPatientId();
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final JSONArray arr = new JSONArray();
		for (final Date date : visitDAO.getPatientVisitDates(patid)) {
			final JSONObject obj = new JSONObject();
			obj.put("value", sdf.format(date));
			obj.put("date", date.getTime());
			arr.add(obj);
		}
		return arr.toJSONString();
	}
}
