/* $Id: DiagnosisComposer.java 216 2010-03-17 06:36:26Z kesavkolla $ */
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
import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.model.PersonType;
import com.bodyfs.model.payments.MasterService;
import com.bodyfs.model.payments.PatientPaymentPlan;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class PaymentComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 6365381701330204030L;
	private transient static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static Log LOGGER = LogFactory.getLog(PaymentComposer.class);

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

		// If there are no payment plans for this user send to the report card page
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		if (paymentDAO.getPaymentPlanDates(patid).size() <= 0) {
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

		Date paymentDate = null;
		if (execution.getParameter("paymentDate") != null) {
			try {
				paymentDate = new Date(new Long(execution.getParameter("paymentDate")));
			} catch (final Throwable t) {
				paymentDate = null;
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Patid: " + patid + "\tpaymentDate:" + paymentDate);
		}
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		// Get the payment plan and setup the txtPaymendData
		final PatientPaymentPlan plan = paymentDAO.getPlanByDate(patid, paymentDate);
		if (plan != null) {
			final Textbox txtPaymentData = (Textbox) Path.getComponent(this.page, "txtPaymentData");
			txtPaymentData.setText(getPlanItems(plan));
		}

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
		final Date paymentDate = new Date(new Long(datebox.getValue()));
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final PatientPaymentPlan plan = paymentDAO.getPlanByDate((Long) page.getAttribute("patid"), paymentDate);
		if (plan == null) {
			return;
		}
		final Textbox txtPaymentData = (Textbox) Path.getComponent(this.page, "txtPaymentData");
		txtPaymentData.setText(getPlanItems(plan));
		Clients.evalJavaScript("setupData(true)");
	}

	/**
	 * This method returns the dates of the patient visits for the current
	 * patient id
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final String getPaymentDates() {
		final Long patid = CommonUtils.getPatientId();
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		final JSONArray arr = new JSONArray();
		for (final Date date : paymentDAO.getPaymentPlanDates(patid)) {
			final JSONObject obj = new JSONObject();
			obj.put("value", sdf.format(date));
			obj.put("date", date.getTime());
			arr.add(obj);
		}
		return arr.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public final String getServicesList() {
		// create JSON Array object of services
		final JSONArray arrServices = new JSONArray();
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		for (final MasterService service : paymentDAO.getMasterServicesList()) {
			final JSONObject obj = new JSONObject();
			obj.put("id", service.getId());
			obj.put("serviceName", service.getServiceName());
			obj.put("charge", service.getCharge());
			arrServices.add(obj);
		}
		return arrServices.toJSONString();
	}

	/**
	 * 
	 * @param plan
	 * @return
	 */
	private String getPlanItems(final PatientPaymentPlan plan) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("[");
		for (final String item : plan.getPlanItems()) {
			buffer.append(item);
			buffer.append(",");
		}
		if (buffer.length() > 1) {
			buffer.setLength(buffer.length() - 1);
		}
		buffer.append("]");
		return buffer.toString();
	}
}
