/* $Id: DiagnosisComposer.java 338 2010-04-09 06:12:50Z kesavkolla $ */
package com.bodyfs.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.npi.NPIGoals;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class GoalsComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 6365381701330204030L;
	private static Log LOGGER = LogFactory.getLog(GoalsComposer.class);

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final Long patid = CommonUtils.getPatientId();
		page.setAttribute("patid", patid);

		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		NPIGoals npigoals = visitDAO.getNNPIGoalsByPatientId(patid);
		if (npigoals == null) {
			npigoals = new NPIGoals();
			npigoals.setPersonId(patid);
		}
		final Textbox txtJsonData = (Textbox) Path.getComponent(this.page, "txtJsonData");
		txtJsonData.setValue(toJSON(npigoals));
		page.setAttribute("npigoals", npigoals);
	}

	/**
	 * Save the data to the corresponding PatientDiagnsotics object
	 * 
	 * @param event
	 */
	public void onSave(final ForwardEvent event) {
		final Textbox txtJsonData = (Textbox) Path.getComponent(this.page, "txtJsonData");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(txtJsonData.getValue());
		}
		// If the data is empty nothing to do
		if (txtJsonData.getValue() == null || txtJsonData.getValue().length() <= 0) {
			Clients.evalJavaScript("navigate('" + event.getData() + "')");
			return;
		}
		final NPIGoals npigoals = (NPIGoals) page.getAttribute("npigoals");

		JSONParser parser = new JSONParser();
		final JSONObject data = (JSONObject) parser.parse(txtJsonData.getValue());
		final JSONArray arrgoals = (JSONArray) data.get("goals");
		final List<String> strgoals = new ArrayList<String>(arrgoals.size());
		for (int i = 0, len = arrgoals.size(); i < len; i++) {
			strgoals.add(((JSONObject) arrgoals.get(i)).toJSONString());
		}
		npigoals.setGoals(strgoals);

		final JSONArray arrprograms = (JSONArray) data.get("programs");
		final List<String> strprograms = new ArrayList<String>(arrprograms.size());
		for (int i = 0, len = arrprograms.size(); i < len; i++) {
			strprograms.add(((JSONObject) arrprograms.get(i)).toJSONString());
		}
		npigoals.setPrograms(strprograms);

		npigoals.setNotes(data.get("notes").toString());
		npigoals.setDueDate(data.get("duedate").toString());

		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		visitDAO.createNPIGoals(npigoals);

		Clients.evalJavaScript("navigate('" + event.getData() + "')");
	}

	@SuppressWarnings("unchecked")
	private String toJSON(final NPIGoals npigoals) {
		final JSONParser parser = new JSONParser();
		final JSONObject data = new JSONObject();
		if (npigoals.getNotes() == null) {
			data.put("notes", "");
		} else {
			data.put("notes", npigoals.getNotes());
		}
		if (npigoals.getDueDate() == null) {
			data.put("duedate", "");
		} else {
			data.put("duedate", npigoals.getDueDate());
		}
		final List<String> strgoals = npigoals.getGoals();
		if (strgoals == null || strgoals.size() <= 0) {
			data.put("goals", new JSONArray());
		} else {
			final JSONArray goals = new JSONArray();
			for (final String str : strgoals) {
				goals.add(parser.parse(str));
			}
			data.put("goals", goals);
		}

		final List<String> strprograms = npigoals.getPrograms();
		if (strprograms == null || strprograms.size() <= 0) {
			data.put("programs", new JSONArray());
		} else {
			final JSONArray arrprograms = new JSONArray();
			for (final String str : strprograms) {
				arrprograms.add(parser.parse(str));
			}
			data.put("programs", arrprograms);
		}

		return data.toJSONString();
	}
}
