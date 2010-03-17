/* $Id$ */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.bodyfs.Constants;
import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;
import com.bodyfs.model.PersonType;
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
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final List<Diagnosis> diagnoses = herbDAO.getDiagnoses();
		if (diagnoses != null) {
			page.setAttribute("diagnosislist", diagnoses);
			final Combobox cmbDiagnosis = (Combobox) Path.getComponent(page, "cmbDiagnosis");
			cmbDiagnosis.setAutocomplete(true);
		}
		final Collection<HerbFormula> formulalist = herbDAO.getFormulas();
		if (formulalist != null) {
			page.setAttribute("formulalist", formulalist);
			final Combobox cmbFormulas = (Combobox) Path.getComponent(page, "cmbFormulas");
			cmbFormulas.setAutocomplete(true);
		}
		final Collection<Herb> herblist = herbDAO.getHerbs();
		if (herblist != null) {
			page.setAttribute("herblist", herblist);
			final Combobox cmbHerbs = (Combobox) Path.getComponent(page, "herblist");
			cmbHerbs.setAutocomplete(true);
		}
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
}
