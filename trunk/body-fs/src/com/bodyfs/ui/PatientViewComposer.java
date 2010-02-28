/*
 * $Id$
 */
package com.bodyfs.ui;

import java.util.concurrent.ArrayBlockingQueue;

import net.sf.jsr107cache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.api.A;
import org.zkoss.zul.api.Groupbox;
import org.zkoss.zul.api.Progressmeter;
import org.zkoss.zul.api.Window;

import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class PatientViewComposer extends GenericAutowireComposer {

	public static final String QUICK_PATIENT_LIST = "com.bodyfs.quickpatientlist";

	private static final long serialVersionUID = 1503608767014635637L;
	private static Log LOGGER = LogFactory.getLog(PatientViewComposer.class);
	private Label week;
	private Label nextApppointment;
	private Label nextReExam;
	private A newemails;
	private Progressmeter pm;
	private Groupbox gbox;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {

		super.doAfterCompose(comp);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside compose");
		}
		Long id = CommonUtils.getPatientId();
		if (id == null) {
			return;
		}
		LOGGER.debug("Retrieving the details for: " + id);
		pageScope.put("CURRENT_PATIENT_ID", id);
		this.sessionScope.put("patid", pageScope.get("CURRENT_PATIENT_ID"));
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final Person person = personDAO.getPerson(id);

		savePatientQuickList(person);
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final int numweek = visitDAO.countPatientVisits(person.getId());
		final int totalWeeks = 10;
		if (gbox != null) {
			gbox.getCaptionApi().setLabel("Report Card: " + person.getFirstName() + " " + person.getLastName());
		}
		if (week != null) {
			week.setValue(numweek + "");
		}
		if (nextApppointment != null) {
			nextApppointment.setValue("12/12/2009 - 3:15 pm");
		}
		if (nextReExam != null) {
			nextReExam.setValue("12/12/2009");
		}
		if (newemails != null) {
			newemails.setLabel("3 New emails");
		}
		if (pm != null) {
			if (numweek > 0) {
				int percent = Math.round(numweek * 100 / totalWeeks);
				pm.setValue(percent);
			} else {
				pm.setValue(0);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void onHandleClick(final ForwardEvent event) {
		final String pageid = (String) event.getData();
		if (pageid == null || pageid.equals("")) {
			return;
		}
		if (pageid.equals("weeklyvisit")) {
			this.sessionScope.put("patid", pageScope.get("CURRENT_PATIENT_ID"));
		}
		execution.sendRedirect("/index.zul#" + pageid);
	}

	public void onCreateLogin(final ForwardEvent event) {
		LOGGER.error("create Login");
		final Window win = (Window) Executions.createComponents("/pages/usermgmt/createLoginInfo.zul", null, null);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method adds the selected patient into the quick patient list. If the
	 * URL is coming from the customer search then only save the patient in the
	 * quicklist. This list is saved in memcache.
	 * 
	 * @param person
	 */
	@SuppressWarnings("unchecked")
	private synchronized void savePatientQuickList(final Person person) {
		// Check for the URL parameter if the parameter doesn't exist nothing to
		// do
		if (execution.getParameter("savepat") == null || !execution.getParameter("savepat").equals("true")) {
			return;
		}
		// Get the queue object from cache if doesn't exist create a new one
		final Cache cache = (Cache) SpringUtil.getBean("datacache");
		ArrayBlockingQueue<String> quicklist = null;
		if (cache.containsKey(QUICK_PATIENT_LIST)) {
			quicklist = (ArrayBlockingQueue<String>) cache.get(QUICK_PATIENT_LIST);
		} else {
			quicklist = new ArrayBlockingQueue<String>(5);
		}
		// If the queue is already full remove one
		if (quicklist.remainingCapacity() == 0) {
			quicklist.poll();
		}
		final String patStr = person.getId() + ":" + person.getDisplayName();
		if (quicklist.contains(patStr)) {
			quicklist.remove(patStr);
		}
		// Add this patient in the queue
		quicklist.offer(patStr);
		// Save the queue back into the cache
		cache.put(QUICK_PATIENT_LIST, quicklist);
	}
}
