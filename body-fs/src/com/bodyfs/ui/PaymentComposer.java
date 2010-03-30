/* $Id: DiagnosisComposer.java 216 2010-03-17 06:36:26Z kesavkolla $ */
package com.bodyfs.ui;

import java.util.ArrayList;
import java.util.Collection;
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
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import com.bodyfs.Constants;
import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;
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

		page.setAttribute("patid", patid);
		return super.doBeforeCompose(page, parent, compInfo);
	}

	/**
	 * Retrieves all the patient's payment plans
	 * 
	 * @return
	 */
	public Collection<PatientPaymentPlan> getAllPlans() {
		final Long patid = CommonUtils.getPatientId();
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		return paymentDAO.getAllPlans(patid);
	}

	/**
	 * Retrieves only active plans
	 * 
	 * @return
	 */
	public Collection<PatientPaymentPlan> getActivePlans() {
		final Collection<PatientPaymentPlan> plans = getAllPlans();
		final List<PatientPaymentPlan> activePlans = new ArrayList<PatientPaymentPlan>();
		for (final PatientPaymentPlan plan : plans) {
			if (plan.getActive()) {
				activePlans.add(plan);
			}
		}
		return activePlans;
	}

	/**
	 * Retrieves all the master services in JSON array
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @param event
	 */
	public void onAction(final ForwardEvent event) {
		final Textbox txtAction = (Textbox) event.getOrigin().getTarget();
		LOGGER.debug("Action: " + txtAction.getValue());
		final String[] action = txtAction.getValue().split(":");
		final Long planid = new Long(action[1]);
		final IPaymentDAO paymentDAO = (IPaymentDAO) SpringUtil.getBean("paymentDAO");
		if (action[0].equals("Activate")) {
			paymentDAO.makeFinal(planid);
		} else if (action[0].equals("Archive")) {
			paymentDAO.archivePlan(planid);
		} else if (action[0].equals("Save")) {
			final PatientPaymentPlan plan = paymentDAO.getPlayById(planid);
			final Textbox txtPaymentData = (Textbox) Path.getComponent(page, "txtPaymentData");
			System.out.println(txtPaymentData.getValue());
			parsePlanData(txtPaymentData.getValue(), plan);
			paymentDAO.createPaymentPlan(plan);
			Clients.evalJavaScript("$.jGrowl('Sucessfully saved the plan', {life:2000});");
			return;
		}
		String pageurl = page.getRequestPath();
		if (pageurl.contains("patient")) {
			pageurl += "?id=" + page.getAttribute("patid");
		}
		execution.sendRedirect(pageurl);
	}

	/**
	 * 
	 * @param data
	 * @param plan
	 */
	private void parsePlanData(final String data, final PatientPaymentPlan plan) {
		final JSONParser parser = new JSONParser();
		final JSONObject obj = (JSONObject) parser.parse(data);
		plan.setPlanLength(new Integer(obj.get("planLength").toString()));
		plan.setDiscount(new Float(obj.get("discount").toString()));
		final JSONArray arrData = (JSONArray) obj.get("planItems");
		final List<String> planItems = new ArrayList<String>(arrData.size());
		for (int i = 0, len = arrData.size(); i < len; i++) {
			final JSONObject item = (JSONObject) arrData.get(i);
			planItems.add(item.toJSONString());
		}
		plan.setPlanItems(planItems);
	}
}
