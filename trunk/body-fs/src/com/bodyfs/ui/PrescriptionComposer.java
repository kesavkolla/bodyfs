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
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import com.bodyfs.Constants;
import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;
import com.bodyfs.model.PatientPrescription;
import com.bodyfs.model.Person;
import com.bodyfs.model.PersonType;
import com.bodyfs.model.payments.MasterService;
import com.bodyfs.model.payments.PatientService;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author Kesav Kumar Kolla
 * 
 */
public class PrescriptionComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -6226037505168911819L;
	private transient static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static Log LOGGER = LogFactory.getLog(PrescriptionComposer.class);

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
		page.setAttribute("patid", patid);
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final PatientPrescription prescription = visitDAO.getPatientPrescriptionByDate(patid, visitDate);
		page.setAttribute("prescription", prescription);
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final List<Diagnosis> diagnoses = herbDAO.getDiagnoses();
		if (diagnoses != null) {
			page.setAttribute("diagnosislist", diagnoses);
			final Combobox cmbDiagnosis = (Combobox) Path.getComponent(page, "cmbDiagnosis");
			if (cmbDiagnosis != null) {
				cmbDiagnosis.setAutocomplete(true);
			}
		}
		final Collection<HerbFormula> formulalist = herbDAO.getFormulas();
		if (formulalist != null) {
			page.setAttribute("formulalist", formulalist);
			final Combobox cmbFormulas = (Combobox) Path.getComponent(page, "cmbFormulas");
			if (cmbFormulas != null) {
				cmbFormulas.setAutocomplete(true);
			}
		}
		final Collection<Herb> herblist = herbDAO.getHerbs();
		if (herblist != null) {
			page.setAttribute("herblist", herblist);
			final Combobox cmbHerbs = (Combobox) Path.getComponent(page, "cmbHerbs");
			if (cmbHerbs != null) {
				cmbHerbs.setAutocomplete(true);
			}
		}

		// setup the service data
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Collection<PatientService> services = paymentDAO.getServicesByVisitDate(prescription.getPersonId(),
				prescription.getVisitDate());
		final String serviceData = getServiceData(services, getServicesList());
		final Textbox txtServices = (Textbox) Path.getComponent(page, "txtServices");
		txtServices.setValue(serviceData);
	}

	/**
	 * This is the event handler for Diagnosis combobox. When ever selected
	 * diagnosis changes retrieve corresponding fomrulas and display them in
	 * formulas combobox.
	 * 
	 * @param event
	 */
	public void onDiagnosisChange(final ForwardEvent event) {
		final Combobox cmbDiagnosis = (Combobox) Path.getComponent(event.getPage(), "cmbDiagnosis");
		final Combobox cmbFormulas = (Combobox) Path.getComponent(page, "cmbFormulas");

		// If the selected item is null nothing to do
		if (cmbDiagnosis.getSelectedIndex() == -1 || cmbDiagnosis.getSelectedItem() == null) {
			return;
		}

		// Get the selected diagnosis and retrieve it's formulas
		final Diagnosis diagnosis = (Diagnosis) cmbDiagnosis.getSelectedItem().getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final Collection<HerbFormula> formulas = herbDAO.getFormulas(diagnosis.getFormulas());

		// Set these formulas to the formula combobox
		page.setAttribute("formulalist", formulas);
		cmbFormulas.setModel(new ListModelList(formulas));
		cmbFormulas.setSelectedIndex(-1);
		cmbFormulas.setSelectedItem(null);
		cmbFormulas.invalidate();
	}

	/**
	 * This is event handler on the formulalist combobox. When ever the selected
	 * formula changes retrieve all the herbs and display them in the Div
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public void onFormulaChange(final ForwardEvent event) {
		final Combobox cmbFormulas = (Combobox) Path.getComponent(page, "cmbFormulas");
		if (cmbFormulas.getSelectedIndex() == -1 || cmbFormulas.getSelectedItem() == null) {
			return;
		}
		final HerbFormula formula = (HerbFormula) cmbFormulas.getSelectedItem().getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final Collection<Herb> herbs = herbDAO.getHerbs(formula.getHerbs());

		final JSONObject retObject = new JSONObject();
		{
			final JSONObject obj = new JSONObject();
			obj.put("id", formula.getId());
			obj.put("name", formula.getName());
			retObject.put("formula", obj);
		}

		final JSONArray herbsArray = new JSONArray();
		for (final Herb herb : herbs) {
			final JSONObject obj = new JSONObject();
			obj.put("id", herb.getId());
			obj.put("name", herb.getName());
			herbsArray.add(obj);
		}
		retObject.put("herbs", herbsArray);
		Clients.evalJavaScript("DisplayData(" + retObject.toJSONString() + ")");
	}

	/**
	 * This is the eventhandling to cmbFormulas1 onchange
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public void onFormula1Change(final ForwardEvent event) {
		final Combobox cmbFormulas1 = (Combobox) Path.getComponent(page, "cmbFormulas1");
		final Combobox cmbHerbs = (Combobox) Path.getComponent(page, "cmbHerbs");
		// If the selected item is null then retrieve all the herbs and set to the cmbHerbs
		if (cmbFormulas1.getSelectedIndex() == -1 || cmbFormulas1.getSelectedItem() == null) {
			final Collection<Herb> herblist = (Collection<Herb>) page.getAttribute("herblist");
			cmbHerbs.setModel(new ListModelList(herblist));
			cmbHerbs.invalidate();
			return;
		}
		// Get the selected formula and retrieve the herbs corresponds to it
		final HerbFormula selFormula = (HerbFormula) cmbFormulas1.getSelectedItem().getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		cmbHerbs.setModel(new ListModelList(herbDAO.getHerbs(selFormula.getHerbs())));
		cmbHerbs.invalidate();
	}

	/**
	 * This will be called when user clicks on either Prev or Done button
	 * 
	 * @param event
	 */
	public void onSave(final ForwardEvent event) {
		// If the value of the textbox is empty then save the data
		final PatientPrescription prescription = (PatientPrescription) page.getAttribute("prescription");
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		visitDAO.createPatientPrescription(prescription);
		final Textbox txtServices = (Textbox) Path.getComponent(page, "txtServices");
		if (txtServices.getValue() != null && txtServices.getValue().length() > 0) {
			saveServiceDate(txtServices.getValue(), prescription.getVisitDate(), prescription.getPersonId());
		}
		Clients.evalJavaScript("navigate('" + event.getData() + "')");
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
		final PatientPrescription prescription = visitDAO.getPatientPrescriptionByDate((Long) page
				.getAttribute("patid"), visitDate);
		if (prescription == null) {
			return;
		}
		final Component controller = evt.getTarget();
		final DataBinder binder = (DataBinder) controller.getAttribute("binder");
		if (binder != null) {
			controller.setAttribute("prescription", prescription);
			// If the change happened on copy box then copy the new prescription data to old object
			if (datebox.getId().equals("txtVisitDatesCopy")) {
				final PatientPrescription origprescirption = (PatientPrescription) page.getAttribute("prescription");
				prescription.copy(origprescirption);
			} else {
				page.setAttribute("prescription", prescription);
			}
			binder.loadAll();
		}

		// setup the service data
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Collection<PatientService> services = paymentDAO.getServicesByVisitDate(prescription.getPersonId(),
				prescription.getVisitDate());
		final String serviceData = getServiceData(services, getServicesList());
		final Textbox txtServices = (Textbox) Path.getComponent(page, "txtServices");
		txtServices.setValue(serviceData);

		Clients.evalJavaScript("loadData(true)");
	}

	/**
	 * Retrieves the list of services that are related to Herbs
	 * 
	 * @return
	 */
	public Collection<MasterService> getServicesList() {
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Collection<MasterService> services = paymentDAO.getAllServices();
		final List<MasterService> herbServices = new ArrayList<MasterService>();
		for (final MasterService service : services) {
			if (service.isHidden() || service.getServiceName().equals("Herbal Treatment")) {
				herbServices.add(service);
			}
		}
		return herbServices;
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

	/**
	 * 
	 * @param services
	 * @param servicesList
	 * @return
	 */
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

	/**
	 * Retrieves the current patient's display name
	 * 
	 * @return
	 */
	public final String getPatientName() {
		final Long patid = CommonUtils.getPatientId();
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final Person person = personDAO.getPerson(patid);
		return person.getDisplayName();
	}
}
