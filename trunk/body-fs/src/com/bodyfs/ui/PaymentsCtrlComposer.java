/* $Id: CreateLoginComposer.java 136 2010-01-31 06:52:07Z kesavkolla $ */
package com.bodyfs.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;
import com.bodyfs.model.payments.MasterService;
import com.bodyfs.model.payments.PatientPaymentPlan;

/**
 * 
 * @author kesav
 * 
 */
public class PaymentsCtrlComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -831489659737220006L;

	@Override
	public ComponentInfo doBeforeCompose(final Page page, final Component parent, final ComponentInfo compInfo) {
		System.out.println("Before compose");
		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		System.out.println("doAfterCompose1");
		super.doAfterCompose(comp);
		System.out.println("doAfterCompose2");
		final IPersonDAO personDao = (IPersonDAO) SpringUtil.getBean("personDAO");
		page.setAttribute("patients", personDao.getAllCustomers());
	}

	public Collection<MasterService> getServices() {
		System.out.println("getServices");
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final Collection<MasterService> services = paymentDAO.getMasterServicesList();
		return services;
	}

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
		final Person patient = (Person) cmbCustomers.getSelectedItem().getValue();
		final PatientPaymentPlan plan = new PatientPaymentPlan();
		plan.setPersonId(patient.getId());
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

		plan.setPlanItems(planItems);
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		paymentDAO.createPaymentPlan(plan);
		Clients.evalJavaScript("$.jGrowl('Sucessfully saved the plan', {life:2000});");
	}
}
