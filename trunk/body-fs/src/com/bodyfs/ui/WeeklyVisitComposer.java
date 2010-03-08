/*
 * $Id$
 */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.PatientDiagnosis;
import com.bodyfs.model.PatientTreatment;
import com.bodyfs.model.PatientVisit;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class WeeklyVisitComposer extends GenericForwardComposer {

	private static transient final long serialVersionUID = 3816734829122660780L;

	private transient static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private transient static Log LOGGER = LogFactory.getLog(WeeklyVisitComposer.class);

	public static String format(final Date date) {
		return sdf.format(date);
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

	/*
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
				execution.sendRedirect("/pages/patient/patientview.zul?id=" + patid);
				return null;
			} else {
				execution.sendRedirect("/pages/user/index.zul");
				return null;
			}
		}
		page.setAttribute("patid", patid);
		return super.doBeforeCompose(page, parent, compInfo);
	}*/

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final Long patid = CommonUtils.getPatientId();
		// If the visitDate parameter exists in the URL convert to the date
		Date visitDate = null;
		if (execution.getParameter("visitDate") != null) {
			try {
				visitDate = new Date(new Long(execution.getParameter("visitDate")));
			} catch (final Throwable t) {
				visitDate = null;
			}
		}

		comp.getPage().setAttribute("personid", patid);
		/*
		 * This composer is used in two different scenarios 1. During the
		 * administrative page where admins will see the visit and be able to
		 * make changes 2. Actual patient signin process In the signin process
		 * we will always create a new patient visit object for that date For
		 * admin page if there is no visitDate in URL then we will show the
		 * latest visit
		 */
		if (visitDate != null || comp.getAttribute("showlast") != null) {
			final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
			final PatientVisit patvisit = visitDAO.getPatientVisitByDate(patid, visitDate);
			this.page.setAttribute("patvisit", patvisit);
		} else {
			final PatientVisit patvisit = new PatientVisit();
			patvisit.setPersonId(patid);
			this.page.setAttribute("patvisit", patvisit);
		}
	}

	/**
	 * This event handles the save functionality of the current visit. This will
	 * persist the patient visit and also creates the corresponding diagnosis
	 * and other related objects
	 * 
	 * @param event
	 */
	public void onSave(final ForwardEvent event) {
		if (page.getAttribute("personid") == null || page.getAttribute("patvisit") == null) {
			return;
		}
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final PatientVisit patvisit = (PatientVisit) page.getAttribute("patvisit");
		if (patvisit != null) {
			visitDAO.createPatientVisit(patvisit);
			LOGGER.debug("Successfully created patient visit with id: " + patvisit.getId());
			// create an empty patient diagnosis for this visit
			final PatientDiagnosis diag = new PatientDiagnosis();
			diag.setPersonId(patvisit.getPersonId());
			diag.setVisitDate(patvisit.getVisitDate());
			visitDAO.createPatientDiagnosis(diag);
			LOGGER.debug("Successfully created diagnosis with id: " + diag.getId());

			// create an empty treatment for this visit
			final PatientTreatment treatment = new PatientTreatment();
			treatment.setPersonId(patvisit.getPersonId());
			treatment.setVisitDate(patvisit.getVisitDate());
			visitDAO.createPatientTreatment(treatment);
			LOGGER.debug("Successfully created treatment with id: " + treatment.getId());
			Clients.evalJavaScript("showConfirmation()");
		}
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
		final Component visitgrid = evt.getTarget();
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final PatientVisit visit = visitDAO.getPatientVisitByDate((Long) page.getAttribute("personid"), visitDate);
		if (visit == null) {
			return;
		}

		final DataBinder binder = (DataBinder) visitgrid.getAttribute("binder");
		if (binder != null) {
			visitgrid.setAttribute("patvisit", visit);
			binder.loadAll();
		}
	}

	/**
	 * This event handler handles the click on new button. This will create a
	 * new patientvisit object and binds to the binder
	 * 
	 * @param evt
	 */
	public void onNewVisit(final ForwardEvent evt) {
		final Component visitgrid = evt.getTarget();
		final DataBinder binder = (DataBinder) visitgrid.getAttribute("binder");
		if (binder != null) {
			final PatientVisit visit = new PatientVisit();
			visit.setPersonId((Long) visitgrid.getPage().getAttribute("personid"));
			visitgrid.setAttribute("patvisit", visit);
			binder.loadAll();
		}
	}

	public void onNoteAdd(final Event event) {
		LOGGER.error(event.getData());
	}
}
