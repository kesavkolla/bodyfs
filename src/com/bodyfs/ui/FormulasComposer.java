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
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;

/**
 * 
 * @author kesav
 * 
 */
public class FormulasComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 3620393406621812305L;
	private static Log LOGGER = LogFactory.getLog(FormulasComposer.class);

	@Override
	public ComponentInfo doBeforeCompose(final Page page, final Component parent, final ComponentInfo compInfo) {
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final ListModelList formulas = new ListModelList(herbDAO.getFormulas());
		LOGGER.debug("Retrieving formulas size:" + formulas.size());
		page.setAttribute("mformulas", formulas);
		final ListModelList herbs = new ListModelList(herbDAO.getHerbs());
		page.setAttribute("mherbs", herbs);
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
	public void onSelectHerb(final ForwardEvent event) {
		final Listbox lstherbs = (Listbox) Path.getComponent(page, "lstherbs");
		final Bandbox bdherbs = (Bandbox) Path.getComponent(page, "bdherbs");
		final Herb herb = (Herb) lstherbs.getSelectedItem().getValue();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("formula: " + herb);
		}
		bdherbs.setValue(herb.getName());
		bdherbs.close();
		final Image imgAdd = (Image) Path.getComponent(page, "imgAdd");
		imgAdd.setAttribute("herb", herb);
	}

	/**
	 * This method will be called when user clicks on Create button on the
	 * formula page
	 * 
	 * @param event
	 */
	public void onCreateFormula(final ForwardEvent event) {
		// Do all the usual validations
		final Textbox txtFormulaName = (Textbox) Path.getComponent(page, "txtFormulaName");
		if (txtFormulaName.getValue() == null || txtFormulaName.getValue().trim().length() <= 0) {
			try {
				Messagebox.show("Missing Formula name", "Error", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}
		final String formulaname = txtFormulaName.getValue().trim();
		final Textbox txtFormulaId = (Textbox) Path.getComponent(page, "txtFormulaId");
		final Textbox txtherbIds = (Textbox) Path.getComponent(page, "txtherbIds");
		final Textbox txtDescription = (Textbox) Path.getComponent(page, "txtDescription");

		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");

		boolean isNew = txtFormulaId.getValue() == null || txtFormulaId.getValue().equals("");

		// For new Formula do the validation of name
		if (isNew) {

			if (herbDAO.checkFormulaName(formulaname)) {
				try {
					Messagebox.show("Formula name already in use", "Error", Messagebox.OK, Messagebox.ERROR);
				} catch (final Exception e) {
				}
				return;
			}
		}

		if (txtherbIds.getValue() == null || txtherbIds.getValue().trim().length() <= 0) {
			try {
				Messagebox.show("Select atleast one herb per formula", "Error", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}

		final ListModelList listmodel = (ListModelList) page.getAttribute("mformulas");
		HerbFormula formula = null;
		int selFormulaIndx = -1;
		if (isNew) {
			// If it's new formula create the formula object
			formula = new HerbFormula();
		} else {
			// Retrieve the selected formula object from the formula list
			// model
			final Long formulaid = new Long(txtFormulaId.getValue());
			for (int i = 0, len = listmodel.getSize(); i < len; i++) {
				final HerbFormula hf = (HerbFormula) listmodel.get(i);
				if (hf.getId().equals(formulaid)) {
					formula = hf;
					selFormulaIndx = i;
					break;
				}
			}
		}
		// Save the formula data
		formula.setDescription(txtDescription.getValue());
		formula.setName(txtFormulaName.getValue());
		final List<Long> herbIds = new ArrayList<Long>();
		for (final String s : txtherbIds.getValue().split(",")) {
			if (s.trim().length() <= 0) {
				continue;
			}
			herbIds.add(new Long(s));
		}

		formula.setHerbs(herbIds);
		herbDAO.createFormula(formula);

		if (isNew) {
			listmodel.add(formula);
		} else {
			listmodel.set(selFormulaIndx, formula);
		}

		final Listbox lstformula = (Listbox) Path.getComponent(page, "lstformula");
		final AnnotateDataBinder binder = (AnnotateDataBinder) lstformula.getAttribute("binder", true);
		binder.loadComponent(lstformula);

		Clients.evalJavaScript("onSave()");
	}

	/**
	 * This will delete the formula clicked by the user
	 * 
	 * @param event
	 */
	public void onDeleteFormula(final ForwardEvent event) {
		// Get the listitem from the event's target (button)
		// parent(hbox)->parent(listcell)->parent(listitem)
		final Listitem li = (Listitem) event.getOrigin().getTarget().getParent().getParent().getParent();
		final HerbFormula formula = (HerbFormula) li.getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		herbDAO.deleteFormulaById(formula.getId());
		// Remove it from the model and refresh the binder
		final ListModelList listmodel = (ListModelList) page.getAttribute("mformulas");
		listmodel.remove(formula);
		final Listbox lstformula = (Listbox) Path.getComponent(page, "lstformula");
		final AnnotateDataBinder binder = (AnnotateDataBinder) lstformula.getAttribute("binder", true);
		binder.loadComponent(lstformula);
	}

	/**
	 * This is event handler on click of add button
	 * 
	 * @param event
	 */
	public void onAddFormula(final ForwardEvent event) {
		final Textbox txtFormulaId = (Textbox) Path.getComponent(page, "txtFormulaId");
		txtFormulaId.setValue("");
		final Textbox txtDescription = (Textbox) Path.getComponent(page, "txtDescription");
		txtDescription.setValue("");
		final Textbox txtFormulaName = (Textbox) Path.getComponent(page, "txtFormulaName");
		txtFormulaName.setValue("");
		txtFormulaName.setReadonly(false);
		final Textbox txtherbIds = (Textbox) Path.getComponent(page, "txtherbIds");
		txtherbIds.setText("");
		Clients.evalJavaScript("setupAdd();");
	}

	/**
	 * Click on Edit button in the listbox setup all the editing fields
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public void onEditFormula(final ForwardEvent event) {
		// Get the listitem from the event's target (button)
		// parent(hbox)->parent(listcell)->parent(listitem)
		final Listitem li = (Listitem) event.getOrigin().getTarget().getParent().getParent().getParent();
		final HerbFormula formula = (HerbFormula) li.getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final Collection<Herb> herbs = herbDAO.getHerbs(formula.getHerbs());

		final Textbox txtFormulaId = (Textbox) Path.getComponent(page, "txtFormulaId");
		txtFormulaId.setValue(formula.getId().toString());
		final Textbox txtDescription = (Textbox) Path.getComponent(page, "txtDescription");
		txtDescription.setValue(formula.getDescription());
		final Textbox txtFormulaName = (Textbox) Path.getComponent(page, "txtFormulaName");
		txtFormulaName.setValue(formula.getName());
		txtFormulaName.setReadonly(true);
		final Textbox txtherbIds = (Textbox) Path.getComponent(page, "txtherbIds");
		final StringBuilder sb = new StringBuilder();
		for (Herb herb : herbs) {
			sb.append(herb.getId()).append(",");
		}
		sb.setLength(sb.length() - 1);
		txtherbIds.setValue(sb.toString());

		final JSONArray arr = new JSONArray();
		for (final Herb herb : herbs) {
			final JSONObject o = new JSONObject();
			o.put("id", herb.getId());
			o.put("name", herb.getName());
			arr.add(o);
		}
		Clients.evalJavaScript("SetupEdit(" + arr.toJSONString() + ");");
	}
}
