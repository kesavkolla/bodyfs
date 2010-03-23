package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.model.payments.MasterService;

public class MasterServiceComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 96308215036898599L;
	private static Log LOGGER = LogFactory.getLog(MasterServiceComposer.class);

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");

		final ListModelList services = new ListModelList(paymentDAO.getAllServices());
		page.setAttribute("serviceslist", services);
	}

	/**
	 * This method is triggered by clicking on Add button. This will create the new service and rebinds the service list
	 * 
	 * @param event
	 */
	public void onAdd(final ForwardEvent event) {
		// Get the values and validate the data
		final Textbox txtService = (Textbox) Path.getComponent(page, "txtService");
		final String serviceName = txtService.getValue().trim();
		final Doublebox txtCharge = (Doublebox) Path.getComponent(page, "txtCharge");
		final Checkbox chkHidden = (Checkbox) Path.getComponent(page, "chkHidden");
		LOGGER.debug("Adding new service: " + txtService.getValue() + "\t" + txtCharge.getValue());
		if (serviceName.length() <= 0 || txtCharge.getValue() == null) {
			try {
				Messagebox.show("Enter the service name and charge", "Error in input", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		if (paymentDAO.checkService(serviceName)) {
			try {
				Messagebox.show("Service already exists", "Error in input", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}

		// Create new service and save it
		final MasterService service = new MasterService();
		service.setServiceName(txtService.getValue().trim());
		service.setCharge(txtCharge.getValue());
		service.setHidden(chkHidden.isChecked());
		paymentDAO.addMasterService(service);

		// Add this new service to listmodel and invalidate the list
		final ListModelList services = (ListModelList) page.getAttribute("serviceslist");
		services.add(service);
		page.setAttribute("serviceslist", services);
		final Listbox lstservice = (Listbox) Path.getComponent(page, "lstservice");
		lstservice.setModel(services);
		lstservice.invalidate();
		LOGGER.debug("added service");
	}

	/**
	 * This will be triggered by the click on Save button
	 * 
	 * @param event
	 */
	public void onSaveService(final ForwardEvent event) {
		// Get the listitem from the event's target (button)
		// parent(hbox)->parent(listcell)->parent(listitem)
		final Listitem li = (Listitem) event.getOrigin().getTarget().getParent().getParent().getParent();
		final MasterService service = (MasterService) li.getValue();

		// save the service
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		paymentDAO.addMasterService(service);

		// Get the listmodel and update this service in it
		final ListModelList services = (ListModelList) page.getAttribute("serviceslist");
		int indx = getListIndex(service, services);
		services.set(indx, service);
		page.setAttribute("serviceslist", services);
		final Listbox lstservice = (Listbox) Path.getComponent(page, "lstservice");
		lstservice.setModel(services);
		lstservice.invalidate();
		Clients.evalJavaScript("$.jGrowl(\"Successfully Saved the service\", {life:2000});");
	}

	/**
	 * This will be triggered when user deletes the servce
	 * 
	 * @param event
	 */
	public void onDeleteService(final ForwardEvent event) {
		// Get the listitem from the event's target (button)
		// parent(hbox)->parent(listcell)->parent(listitem)
		final Listitem li = (Listitem) event.getOrigin().getTarget().getParent().getParent().getParent();
		final MasterService service = (MasterService) li.getValue();

		// delete the service
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		paymentDAO.deleteService(service);
		final ListModelList services = (ListModelList) page.getAttribute("serviceslist");
		int indx = getListIndex(service, services);
		services.remove(indx);
		page.setAttribute("serviceslist", services);
		final Listbox lstservice = (Listbox) Path.getComponent(page, "lstservice");
		lstservice.setModel(services);
		lstservice.invalidate();
		Clients.evalJavaScript("$.jGrowl(\"Successfully Deleted the service\", {life:2000});");
	}

	private int getListIndex(final MasterService service, final ListModelList services) {
		for (int i = 0, len = services.getSize(); i < len; i++) {
			final MasterService svc = (MasterService) services.get(i);
			if (svc.getId().equals(service.getId())) {
				return i;
			}
		}
		return -1;
	}
}
