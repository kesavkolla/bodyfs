/* $Id: CreateLoginComposer.java 136 2010-01-31 06:52:07Z kesavkolla $ */
package com.bodyfs.ui;

import java.util.ArrayList;
import java.util.Collection;

import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;
import com.bodyfs.model.payments.MasterService;
import com.bodyfs.model.payments.PaymentBreakDown;
import com.bodyfs.model.payments.TotalCustomerServicesBreakDown;

/**
 * 
 * @author kesav
 * 
 */
public class PaymentsCtrlComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -831489659737220006L;

	ArrayList<MasterService> masterServicesList = new ArrayList<MasterService>();

	ArrayList<PaymentBreakDown> paymentBreakDownList = new ArrayList<PaymentBreakDown>();

	ArrayList<TotalCustomerServicesBreakDown> paymentSummaryList = new ArrayList<TotalCustomerServicesBreakDown>();

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final IPersonDAO personDao = (IPersonDAO) SpringUtil.getBean("personDAO");
		final Collection<MasterService> services = paymentDAO.getMasterServicesList();
		page.setAttribute("services", services);
		page.setAttribute("patients", personDao.getAll());

		// create JSON Array object of services
		final JSONArray arrServices = new JSONArray();
		for (final MasterService service : services) {
			final JSONObject obj = new JSONObject();
			obj.put("id", service.getId());
			obj.put("serviceName", service.getServiceName());
			obj.put("charge", service.getCharge());
			arrServices.add(obj);
		}
		page.setAttribute("arrServices", arrServices.toJSONString());
		Clients.evalJavaScript("saveServices(" + arrServices.toJSONString() + ");");
	}

	public void onSave(final ForwardEvent evt) {
		final Combobox cmbCustomers = (Combobox) Path.getComponent(page, "cmbCustomers");
		if (cmbCustomers.getSelectedIndex() < 1) {
			try {
				Messagebox.show("Select the customer for saving the pyament plan", "Error", Messagebox.OK,
						Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}
		final Textbox txtSummaryData = (Textbox) Path.getComponent(page, "txtSummaryData");
		if (txtSummaryData.getValue() == null || txtSummaryData.getValue().length() <= 0) {
			try {
				Messagebox
						.show("add services before saving the pyament plan", "Error", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}

		final Person patient = (Person) cmbCustomers.getSelectedItem().getValue();
	}

	public void onPatientChange(final ForwardEvent event) {

	}
}
