/* $Id: CreateLoginComposer.java 136 2010-01-31 06:52:07Z kesavkolla $ */
package com.bodyfs.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Radiogroup;

import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;
import com.bodyfs.model.payments.MasterService;
import com.bodyfs.model.payments.PatientPaymentPlan;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class PaymentsCtrlComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -831489659737220006L;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final IPersonDAO personDao = (IPersonDAO) SpringUtil.getBean("personDAO");
		page.setAttribute("patients", personDao.getAllCustomers());
		final Long patid = CommonUtils.getPatientId();
		page.setAttribute("patid", patid);
	}

	public Collection<MasterService> getServices() {
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Collection<MasterService> services = paymentDAO.getMasterServicesList();
		return services;
	}

	/**
	 * This will be executed from the npi flow of the payments.
	 * In this flow we don't create new payment plans every time we keep only one plan for entire flow
	 * It's always the first payment plan that get's created during the NPI process. The NPI flow only works
	 * with that payment plan.
	 * 
	 * @param evt
	 */
	public void onSaveNPI(final ForwardEvent evt) {
		final Long patid = (Long) page.getAttribute("patid");
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Collection<Date> planDates = paymentDAO.getPaymentPlanDates(patid);
		PatientPaymentPlan plan = null;
		if (planDates == null || planDates.size() <= 0) {
			plan = new PatientPaymentPlan();
		} else {
			// Dates are ordered in desc order I need the earliest date for NPI
			Date stDate = null;
			for (final Date dt : planDates) {
				stDate = dt;
			}
			plan = paymentDAO.getPlanByDate(patid, stDate);
		}
		final Textbox txtPaymentData = (Textbox) Path.getComponent(page, "txtPaymentData");
		if (txtPaymentData.getValue() == null || txtPaymentData.getValue().length() <= 0) {
			try {
				Messagebox
						.show("add services before saving the pyament plan", "Error", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}

		plan.setPersonId(patid);
		final Intbox planLength = (Intbox) Path.getComponent(page, "planLength");
		plan.setPlanLength(planLength.getValue());
		// parse the json data in the txtPaymentData
		final Radiogroup careType = (Radiogroup) Path.getComponent(page, "care");
		if(careType != null) {
			plan.setCareType(careType.getSelectedItemApi().getValue());
		}
		final JSONParser parser = new JSONParser();
		final JSONArray arrData = (JSONArray) parser.parse(txtPaymentData.getValue());
		final List<String> planItems = new ArrayList<String>(arrData.size());
		for (int i = 0, len = arrData.size(); i < len; i++) {
			final JSONObject obj = (JSONObject) arrData.get(i);
			planItems.add(obj.toJSONString());
		}
		final Doublebox txtDiscount = (Doublebox) Path.getComponent(page, "txtDiscount");
		if (txtDiscount.getValue() != null) {
			plan.setDiscount(txtDiscount.getValue().floatValue());
		}

		plan.setPlanItems(planItems);
		paymentDAO.createPaymentPlan(plan);

		if (evt.getData().toString().equalsIgnoreCase("Done")) {
			final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
			personDAO.deleteQuickPatient(patid);
		}
		Clients.evalJavaScript("navigate('" + evt.getData() + "')");
	}

	/**
	 * This will be used by the action menu paymentscreate.zul page
	 * 
	 * @param evt
	 */
	public void onSave(final ForwardEvent evt) {
		final Combobox cmbCustomers = (Combobox) Path.getComponent(page, "cmbCustomers");
		final Intbox planLength = (Intbox) Path.getComponent(page, "planLength");
		if (cmbCustomers.getSelectedIndex() < 0 || cmbCustomers.getSelectedItem() == null) {
			try {
				Messagebox.show("Select the customer for saving the pyament plan", "Error", Messagebox.OK,
						Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}
		final Person patient = (Person) cmbCustomers.getSelectedItem().getValue();
		final Long patid = patient.getId();

		final Textbox txtPaymentData = (Textbox) Path.getComponent(page, "txtPaymentData");
		if (txtPaymentData.getValue() == null || txtPaymentData.getValue().length() <= 0) {
			try {
				Messagebox
						.show("add services before saving the pyament plan", "Error", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}
		// create the payment plan and save it

		final PatientPaymentPlan plan = new PatientPaymentPlan();
		plan.setPersonId(patid);
		plan.setPlanLength(planLength.getValue());
		// parse the json data in the txtPaymentData
		final JSONParser parser = new JSONParser();
		final JSONArray arrData = (JSONArray) parser.parse(txtPaymentData.getValue());
		final List<String> planItems = new ArrayList<String>(arrData.size());
		for (int i = 0, len = arrData.size(); i < len; i++) {
			final JSONObject obj = (JSONObject) arrData.get(i);
			planItems.add(obj.toJSONString());
		}
		final Doublebox txtDiscount = (Doublebox) Path.getComponent(page, "txtDiscount");
		if (txtDiscount.getValue() != null) {
			plan.setDiscount(txtDiscount.getValue().floatValue());
		}
		final Radiogroup careType = (Radiogroup) Path.getComponent(page, "care");
		if(careType != null) {
			plan.setCareType(careType.getSelectedItemApi().getValue());
		}
		plan.setPlanItems(planItems);
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		paymentDAO.createPaymentPlan(plan);

		Clients.evalJavaScript("$.jGrowl('Sucessfully saved the plan', {life:2000});");
	}

	/**
	 * The first payment plan is the NPI payment plan in JSON format
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getNPIPlan() {
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Long patid = CommonUtils.getPatientId();
		final Collection<Date> planDates = paymentDAO.getPaymentPlanDates(patid);
		if (planDates == null || planDates.isEmpty()) {
			return "{}";
		}
		Date stDate = null;
		for (final Date dt : planDates) {
			stDate = dt;
		}
		final PatientPaymentPlan plan = paymentDAO.getPlanByDate(patid, stDate);
		final JSONObject planjson = new JSONObject();
		planjson.put("id", plan.getId());
		planjson.put("discount", plan.getDiscount());
		planjson.put("planLength", plan.getPlanLength());
		final JSONArray itemsarr = new JSONArray();
		itemsarr.addAll(plan.getPlanItems());
		planjson.put("planItems", itemsarr);
		return planjson.toJSONString();
	}
}
