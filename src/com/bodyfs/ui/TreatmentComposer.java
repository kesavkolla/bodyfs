/* $Id$ */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import com.bodyfs.Constants;
import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.model.PatientTreatment;
import com.bodyfs.model.PersonType;
import com.bodyfs.model.payments.MasterService;
import com.bodyfs.model.payments.PatientService;
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
				execution.sendRedirect("/pages/patient/patientview.zul?id=" + patid);
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

		// Get the patient services that are part of this visit
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Collection<PatientService> services = paymentDAO.getServicesByVisitDate(treatment.getPersonId(),
				treatment.getVisitDate());
		final String serviceData = getServiceData(services, getServicesList());
		final Textbox txtServices = (Textbox) Path.getComponent(page, "txtServices");
		txtServices.setValue(serviceData);
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
		final Textbox txtServices = (Textbox) Path.getComponent(page, "txtServices");
		if (txtServices.getValue() != null && txtServices.getValue().length() > 0) {
			saveServiceDate(txtServices.getValue(), treatment.getVisitDate(), treatment.getPersonId());
		}
		Clients.evalJavaScript("navigate('" + event.getData() + "')");
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
			binder.loadAll();
			// if the textfield is copy one don't save the treatment just copy the data from this new one to old object
			if (datebox.getId().equals("txtVisitDatesCopy")) {
				final PatientTreatment origTreatment = (PatientTreatment) page.getAttribute("treatment");
				treatment.copy(origTreatment);
			} else {
				page.setAttribute("treatment", treatment);
			}
		}

		// Get the patient services that are part of this visit
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Collection<PatientService> services = paymentDAO.getServicesByVisitDate(treatment.getPersonId(),
				treatment.getVisitDate());
		final String serviceData = getServiceData(services, getServicesList());
		final Textbox txtServices = (Textbox) Path.getComponent(page, "txtServices");
		txtServices.setValue(serviceData);

		Clients.evalJavaScript("reloadMarkers(false)");
	}

	/**
	 * Retrieves the list of all the services that are available
	 * 
	 * @return
	 */
	public Collection<MasterService> getServicesList() {
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		return paymentDAO.getMasterServicesList();
	}

	/**
	 * This will parse the servicedata from json object and convert it to the PatientService object and persists the
	 * services
	 * 
	 * @param serviceData
	 * @param visitDate
	 * @param patientId
	 */
	private void saveServiceDate(final String serviceData, final Date visitDate, final Long patientId) {
		final JSONParser parser = new JSONParser();
		final JSONArray arrServices = (JSONArray) parser.parse(serviceData);
		if (arrServices.size() <= 0) {
			return;
		}
		final List<PatientService> services = new ArrayList<PatientService>(arrServices.size());
		for (int i = 0, len = arrServices.size(); i < len; i++) {
			final JSONObject obj = (JSONObject) arrServices.get(i);
			final PatientService pService = new PatientService();
			pService.setPersonId(patientId);
			pService.setVisitDate(visitDate);
			pService.setServiceId(Long.parseLong(obj.get("id").toString()));
			pService.setNumServices(Float.parseFloat(obj.get("count").toString()));
			services.add(pService);
		}
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		paymentDAO.createVisitServices(services);
	}

	@SuppressWarnings("unchecked")
	private String getServiceData(final Collection<PatientService> services,
			final Collection<MasterService> servicesList) {
		final JSONArray arrServices = new JSONArray();
		for (final PatientService service : services) {
			// Check this service exists in servicesList
			boolean exists = false;
			for (final MasterService mservice : servicesList) {
				if (service.getServiceId().equals(mservice.getId())) {
					exists = true;
					break;
				}
			}
			if (exists) {
				final JSONObject obj = new JSONObject();
				obj.put("id", service.getServiceId());
				obj.put("count", service.getNumServices());
				arrServices.add(obj);
			}
		}
		return arrServices.toJSONString();
	}

}
