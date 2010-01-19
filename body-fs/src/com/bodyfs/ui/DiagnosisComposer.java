package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.PatientDiagnosis;

public class DiagnosisComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 6365381701330204030L;
	private transient static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

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
		final PatientDiagnosis diagnosis = visitDAO.getPatientDiagnosisByDate(patid, visitDate);
		if (diagnosis != null) {
			final Textbox txtjsondata = (Textbox) Path.getComponent(this.page, "jsondata");
			System.out.println(diagnosis.getDiagnosisData());
			txtjsondata.setText(diagnosis.getDiagnosisData());
		}
	}

	public void onSave(final ForwardEvent event) {
		final Textbox txtjsondata = (Textbox) Path.getComponent(this.page, "jsondata");
		System.out.println(txtjsondata.getValue());
	}

	/**
	 * This event handler handles the click on pagination of dates When ever a
	 * new date is selected the corresponding patient visit data is bound to the
	 * data binder.
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
		datebox.setText(diagnosis.getDiagnosisData());
		Clients.evalJavaScript("setupData()");
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
