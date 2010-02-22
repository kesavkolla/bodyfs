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
import org.zkoss.zul.Div;

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
	}

	/**
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public void onDiagnosisChange(final ForwardEvent event) {
		final Combobox cmbDiagnosis = (Combobox) Path.getComponent(event.getPage(), "cmbDiagnosis");

		if (cmbDiagnosis.getSelectedIndex() == -1 || cmbDiagnosis.getSelectedItem() == null) {
			return;
		}

		final Diagnosis diagnosis = (Diagnosis) cmbDiagnosis.getSelectedItem().getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final Collection<HerbFormula> formulas = herbDAO.getFormulas(diagnosis.getFormulas());
		final List<Long> herbIds = new ArrayList<Long>();
		final JSONObject retObj = new JSONObject();
		final JSONArray arrFormulas = new JSONArray();
		for (final HerbFormula formula : formulas) {
			for (final Long herbId : formula.getHerbs()) {
				if (herbIds.indexOf(herbId) == -1) {
					herbIds.add(herbId);
				}
			}
			final JSONObject obj = new JSONObject();
			obj.put("name", formula.getName());
			obj.put("herbs", formula.getHerbs());
			arrFormulas.add(obj);
		}
		final Collection<Herb> herbs = herbDAO.getHerbs(herbIds);
		final JSONArray arrHerbs = new JSONArray();
		for (final Herb herb : herbs) {
			final JSONObject obj = new JSONObject();
			obj.put("name", herb.getCommonName());
			obj.put("id", herb.getId());
			arrHerbs.add(obj);
		}
		retObj.put("formulas", arrFormulas);
		retObj.put("herbs", arrHerbs);
		Clients.evalJavaScript("DisplayData(" + retObj.toJSONString() + ");");
	}

}
