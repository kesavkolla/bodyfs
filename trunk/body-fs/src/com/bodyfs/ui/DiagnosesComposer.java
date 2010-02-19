/* $Id$ */
package com.bodyfs.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.HerbFormula;

/**
 * 
 * @author kesav
 * 
 */
public class DiagnosesComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 3620393406621812305L;
	private static Log LOGGER = LogFactory.getLog(DiagnosesComposer.class);

	@Override
	public ComponentInfo doBeforeCompose(final Page page, final Component parent, final ComponentInfo compInfo) {
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final ListModelList formulas = new ListModelList(herbDAO.getFormulas());
		LOGGER.debug("Retrieving formulas size:" + formulas.size());
		page.setAttribute("mformulas", formulas);
		final ListModelList diagnoses = new ListModelList(herbDAO.getDiagnoses());
		page.setAttribute("mdiagnoses", diagnoses);
		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	/**
	 * This event will be triggerd when user selects any item in the band box
	 * 
	 * @param event
	 */
	public void onSelectFormula(final ForwardEvent event) {
		final Listbox lstformulas = (Listbox) Path.getComponent(page, "lstformulas");
		final Bandbox bdformulas = (Bandbox) Path.getComponent(page, "bdformulas");
		final HerbFormula formula = (HerbFormula) lstformulas.getSelectedItem().getValue();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("formula: " + formula);
		}
		bdformulas.setValue(formula.getName());
		bdformulas.close();
		final Image imgAdd = (Image) Path.getComponent(page, "imgAdd");
		imgAdd.setAttribute("formula", formula);
	}

	/**
	 * This method will be called when user clicks on Create button on the
	 * diagnoses page
	 * 
	 * @param event
	 */
	public void onCreateDiagnosis(final ForwardEvent event) {
		// Do all the usual validations
		final Textbox txtDiagnosisName = (Textbox) Path.getComponent(page, "txtDiagnosisName");
		if (txtDiagnosisName.getValue() == null || txtDiagnosisName.getValue().trim().length() <= 0) {
			try {
				Messagebox.show("Missing Diagnosis name", "Error", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}
		final String diagname = txtDiagnosisName.getValue().trim();
		final Textbox txtDiagnosisId = (Textbox) Path.getComponent(page, "txtDiagnosisId");

		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");

		// For new Diagnoses do the validation of name
		if (txtDiagnosisId.getValue() == null || txtDiagnosisId.getValue().equals("")) {

			if (herbDAO.checkDiagnosisName(diagname)) {
				try {
					Messagebox.show("Diagnosis name already in use", "Error", Messagebox.OK, Messagebox.ERROR);
				} catch (final Exception e) {
				}
				return;
			}
		}
		final Textbox txtFormulaIds = (Textbox) Path.getComponent(page, "txtFormulaIds");
		if (txtFormulaIds.getValue() == null || txtFormulaIds.getValue().trim().length() <= 0) {
			try {
				Messagebox.show("Select atleast one formula per diagnosis", "Error", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}

		final Textbox txtDescription = (Textbox) Path.getComponent(page, "txtDescription");
		// Save the diagnois data
		final Diagnosis diagnosis = new Diagnosis();
		if (txtDiagnosisId.getValue() != null && txtDiagnosisId.getValue().length() > 0) {
			diagnosis.setId(new Long(txtDiagnosisId.getValue()));
		}
		diagnosis.setDescription(txtDescription.getValue());
		diagnosis.setName(txtDiagnosisName.getValue());
		final List<Long> formulaIds = new ArrayList<Long>();
		for (final String s : txtFormulaIds.getValue().split(",")) {
			if (s.trim().length() <= 0) {
				continue;
			}
			formulaIds.add(new Long(s));
		}

		diagnosis.setFormulas(formulaIds);
		herbDAO.createDiagnosis(diagnosis);
		final ListModelList listmodel = (ListModelList) page.getAttribute("mdiagnoses");
		listmodel.add(diagnosis);

		final Listbox lstdiagnosis = (Listbox) Path.getComponent(page, "lstdiagnosis");
		final AnnotateDataBinder binder = (AnnotateDataBinder) lstdiagnosis.getAttribute("binder", true);
		binder.loadComponent(lstdiagnosis);

		Clients.evalJavaScript("onSave()");
	}

	/**
	 * This will delete the diagnosis clicked by the user
	 * 
	 * @param event
	 */
	public void onDeleteDiagnosis(final ForwardEvent event) {
		// Get the listitem from the event's target (button)
		// parent(hbox)->parent(listcell)->parent(listitem)
		final Listitem li = (Listitem) event.getOrigin().getTarget().getParent().getParent().getParent();
		final Diagnosis diagnosis = (Diagnosis) li.getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		herbDAO.deleteDiagnosisById(diagnosis.getId());
		// Remove it from the model and refresh the binder
		final ListModelList listmodel = (ListModelList) page.getAttribute("mdiagnoses");
		listmodel.remove(diagnosis);
		final Listbox lstdiagnosis = (Listbox) Path.getComponent(page, "lstdiagnosis");
		final AnnotateDataBinder binder = (AnnotateDataBinder) lstdiagnosis.getAttribute("binder", true);
		binder.loadComponent(lstdiagnosis);
	}

	/**
	 * Click on Edit button in the listbox setup all the editing fields
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public void onEditDiagnosis(final ForwardEvent event) {
		// Get the listitem from the event's target (button)
		// parent(hbox)->parent(listcell)->parent(listitem)
		final Listitem li = (Listitem) event.getOrigin().getTarget().getParent().getParent().getParent();
		final Diagnosis diagnosis = (Diagnosis) li.getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final Collection<HerbFormula> formulas = herbDAO.getFormulas(diagnosis.getFormulas());

		final Textbox txtDiagnosisId = (Textbox) Path.getComponent(page, "txtDiagnosisId");
		txtDiagnosisId.setValue(diagnosis.getId().toString());
		final Textbox txtDescription = (Textbox) Path.getComponent(page, "txtDescription");
		txtDescription.setValue(diagnosis.getDescription());
		final Textbox txtDiagnosisName = (Textbox) Path.getComponent(page, "txtDiagnosisName");
		txtDiagnosisName.setValue(diagnosis.getName());
		txtDiagnosisName.setReadonly(true);
		final Textbox txtFormulaIds = (Textbox) Path.getComponent(page, "txtFormulaIds");
		final StringBuilder sb = new StringBuilder();
		for (HerbFormula formula : formulas) {
			sb.append(formula.getId()).append(",");
		}
		sb.setLength(sb.length() - 1);
		txtFormulaIds.setValue(sb.toString());

		final JSONArray arr = new JSONArray();
		for (final HerbFormula formula : formulas) {
			final JSONObject o = new JSONObject();
			o.put("id", formula.getId());
			o.put("name", formula.getName());
			arr.add(o);
		}
		Clients.evalJavaScript("SetupEdit(" + arr.toJSONString() + ");");
	}
}
