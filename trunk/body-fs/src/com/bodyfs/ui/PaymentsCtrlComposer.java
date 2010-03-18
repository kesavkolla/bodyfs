/* $Id: CreateLoginComposer.java 136 2010-01-31 06:52:07Z kesavkolla $ */
package com.bodyfs.ui;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.api.Button;
import org.zkoss.zul.api.Combobox;
import org.zkoss.zul.api.Listbox;
import org.zkoss.zul.api.Listitem;

import com.bodyfs.dao.IPaymentsDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;
import com.bodyfs.model.payments.MasterServices;
import com.bodyfs.model.payments.PaymentBreakDown;
import com.bodyfs.model.payments.TotalCustomerServicesBreakDown;

/**
 * 
 * @author kesav
 * 
 */
public class PaymentsCtrlComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -831489659737220006L;

	ArrayList<MasterServices> masterServicesList = new ArrayList<MasterServices>();

	ArrayList<PaymentBreakDown> paymentBreakDownList = new ArrayList<PaymentBreakDown>();

	ArrayList<TotalCustomerServicesBreakDown> paymentSummaryList = new ArrayList<TotalCustomerServicesBreakDown>();

	AnnotateDataBinder binder;

	Listbox serviceList;
	Listbox servicesBreakDownListBox;
	Listbox ServicesSummaryListBox;
	Button addService;

	Button Print;
	Button save;

	Combobox customers;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final IPaymentsDAO payDao = (IPaymentsDAO) SpringUtil.getBean("paymentDAO");
		final IPersonDAO personDao = (IPersonDAO) SpringUtil.getBean("personDAO");

		ArrayList<Person> allCustomers = new ArrayList<Person>();
		allCustomers.addAll(personDao.getAllCustomersInfo());

		binder = new AnnotateDataBinder(comp);
		masterServicesList.addAll(payDao.getMasterServicesList());
		binder.loadAll();

		serviceList.setModel(new ListModelList(masterServicesList));
		if (masterServicesList.size() > 0) {
			serviceList.setSelectedIndex(0);
		}
		serviceList.invalidate();

		customers.setModel(new ListModelList(allCustomers));
		customers.invalidate();
	}

	public void onClick$addService() {
		MasterServices service = (MasterServices) serviceList.getSelectedItemApi().getValue();
		System.out.println("getting" + ((MasterServices) serviceList.getSelectedItemApi().getValue()).getServiceId());
		PaymentBreakDown brkDownService = new PaymentBreakDown();

		brkDownService.setServiceId(service.getServiceId());
		brkDownService.setServiceName(service.getServiceName());
		brkDownService.setCost(service.getCharge());
		brkDownService.setPerWeek(0);
		brkDownService.setDuration(0);
		paymentBreakDownList.add(brkDownService);
		servicesBreakDownListBox.setModel(new ListModelList(paymentBreakDownList));
		servicesBreakDownListBox.invalidate();
	}

	public void onDeleteService(final ForwardEvent event) {

		Listitem item = (Listitem) event.getTarget().getParent();
		PaymentBreakDown brkDownService = (PaymentBreakDown) item.getValue();
		System.out.println((Listitem) event.getTarget().getParent());
		paymentBreakDownList.remove(brkDownService);
		servicesBreakDownListBox.setModel(new ListModelList(paymentBreakDownList));
		servicesBreakDownListBox.invalidate();
	}

	public void onUpdateSummary(final ForwardEvent event) {
		System.out.println("PaymentsCtrlComposer.updateSummary()");
		paymentSummaryList = new ArrayList<TotalCustomerServicesBreakDown>();
		for (PaymentBreakDown service : paymentBreakDownList) {
			addServiceToSummary(paymentSummaryList, service);
		}
		ServicesSummaryListBox.setModel(new ListModelList(paymentSummaryList));
		ServicesSummaryListBox.invalidate();

	}

	private void addServiceToSummary(ArrayList<TotalCustomerServicesBreakDown> paymentSummaryList,
			PaymentBreakDown service) {
		boolean isNewService = true;
		int sessionsToAdd = service.getPerWeek() * service.getDuration();
		Double serviceCost = sessionsToAdd * service.getCost();
		for (TotalCustomerServicesBreakDown serviceTotals : paymentSummaryList) {
			if (serviceTotals.getServiceId().equals(service.getServiceId())) {
				serviceTotals.setTotalSessions(serviceTotals.getTotalSessions() + sessionsToAdd);
				serviceTotals.setCost(serviceTotals.getCost() + serviceCost);
				isNewService = false;
			}
		}

		if (isNewService) {
			TotalCustomerServicesBreakDown serviceTotals = new TotalCustomerServicesBreakDown();
			serviceTotals.setCost(serviceCost);
			serviceTotals.setServiceCharge(service.getCost());
			serviceTotals.setServiceId(service.getServiceId());
			serviceTotals.setServiceName(service.getServiceName());
			serviceTotals.setTotalSessions(sessionsToAdd);

			paymentSummaryList.add(serviceTotals);
		}
	}

	public void onSave(final Event evt) {

		Long customerID = Long.parseLong(customers.getValue());

	}

	public void onCancel(final Event evt) {
		Executions.sendRedirect("/pages/patient/patientview.zul?id=" + page.getAttribute("patid"));
	}

}
