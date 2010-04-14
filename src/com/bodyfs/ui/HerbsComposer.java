/* $Id$ */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.model.Herb;

/**
 * 
 * @author kesav
 * 
 */
public class HerbsComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 3620393406621812305L;
	private static Log LOGGER = LogFactory.getLog(HerbsComposer.class);

	@Override
	public ComponentInfo doBeforeCompose(final Page page, final Component parent, final ComponentInfo compInfo) {
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		final ListModelList herbs = new ListModelList(herbDAO.getHerbs());
		LOGGER.debug("Retrieving herbs size:" + herbs.size());
		page.setAttribute("mherbs", herbs);
		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	/**
	 * This method will be called when user clicks on Create button on the herbs
	 * page
	 * 
	 * @param event
	 */
	public void onCreateHerb(final ForwardEvent event) {
		// Do all the usual validations
		final Textbox txtHerbName = (Textbox) Path.getComponent(page, "txtHerbName");
		if (txtHerbName.getValue() == null || txtHerbName.getValue().trim().length() <= 0) {
			try {
				Messagebox.show("Missing Herb name", "Error", Messagebox.OK, Messagebox.ERROR);
			} catch (final Exception e) {
			}
			return;
		}
		final String herbname = txtHerbName.getValue().trim();
		final Textbox txtHerbId = (Textbox) Path.getComponent(page, "txtHerbId");
		final Textbox txtDescription = (Textbox) Path.getComponent(page, "txtDescription");

		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");

		boolean isNew = txtHerbId.getValue() == null || txtHerbId.getValue().equals("");

		// For new Herb do the validation of name
		if (isNew) {

			if (herbDAO.checkHerbName(herbname)) {
				try {
					Messagebox.show("Herb name already in use", "Error", Messagebox.OK, Messagebox.ERROR);
				} catch (final Exception e) {
				}
				return;
			}
		}

		final ListModelList listmodel = (ListModelList) page.getAttribute("mherbs");
		Herb herb = null;
		int selHerbIndx = -1;
		if (isNew) {
			// If it's new herb create the herb object
			herb = new Herb();
		} else {
			// Retrieve the selected herb object from the herb list
			// model
			final Long herbid = new Long(txtHerbId.getValue());
			for (int i = 0, len = listmodel.getSize(); i < len; i++) {
				final Herb hb = (Herb) listmodel.get(i);
				if (hb.getId().equals(herbid)) {
					herb = hb;
					selHerbIndx = i;
					break;
				}
			}
		}
		// Save the herb data
		herb.setDescription(txtDescription.getValue());
		herb.setName(txtHerbName.getValue());

		herbDAO.createHerb(herb);

		if (isNew) {
			listmodel.add(herb);
		} else {
			listmodel.set(selHerbIndx, herb);
		}

		final Listbox lstherb = (Listbox) Path.getComponent(page, "lstherb");
		final AnnotateDataBinder binder = (AnnotateDataBinder) lstherb.getAttribute("binder", true);
		binder.loadComponent(lstherb);

		Clients.evalJavaScript("onSave()");
	}

	/**
	 * This will delete the herb clicked by the user
	 * 
	 * @param event
	 */
	public void onDeleteHerb(final ForwardEvent event) {
		// Get the listitem from the event's target (button)
		// parent(hbox)->parent(listcell)->parent(listitem)
		final Listitem li = (Listitem) event.getOrigin().getTarget().getParent().getParent().getParent();
		final Herb herb = (Herb) li.getValue();
		final IHerbDAO herbDAO = (IHerbDAO) SpringUtil.getBean("herbDAO");
		herbDAO.deleteHerbById(herb.getId());
		// Remove it from the model and refresh the binder
		final ListModelList listmodel = (ListModelList) page.getAttribute("mherbs");
		listmodel.remove(herb);
		final Listbox lstherb = (Listbox) Path.getComponent(page, "lstherb");
		final AnnotateDataBinder binder = (AnnotateDataBinder) lstherb.getAttribute("binder", true);
		binder.loadComponent(lstherb);
	}

	/**
	 * This is event handler on click of add button
	 * 
	 * @param event
	 */
	public void onAddHerb(final ForwardEvent event) {
		final Textbox txtHerbId = (Textbox) Path.getComponent(page, "txtHerbId");
		txtHerbId.setValue("");
		final Textbox txtDescription = (Textbox) Path.getComponent(page, "txtDescription");
		txtDescription.setValue("");
		final Textbox txtHerbName = (Textbox) Path.getComponent(page, "txtHerbName");
		txtHerbName.setValue("");
		txtHerbName.setReadonly(false);
		Clients.evalJavaScript("setupAdd();");
	}

	/**
	 * Click on Edit button in the listbox setup all the editing fields
	 * 
	 * @param event
	 */
	public void onEditHerb(final ForwardEvent event) {
		// Get the listitem from the event's target (button)
		// parent(hbox)->parent(listcell)->parent(listitem)
		final Listitem li = (Listitem) event.getOrigin().getTarget().getParent().getParent().getParent();
		final Herb herb = (Herb) li.getValue();

		final Textbox txtHerbId = (Textbox) Path.getComponent(page, "txtHerbId");
		txtHerbId.setValue(herb.getId().toString());
		final Textbox txtDescription = (Textbox) Path.getComponent(page, "txtDescription");
		txtDescription.setValue(herb.getDescription());
		final Textbox txtHerbName = (Textbox) Path.getComponent(page, "txtHerbName");
		txtHerbName.setValue(herb.getName());
		txtHerbName.setReadonly(true);
		Clients.evalJavaScript("SetupEdit();");
	}
}
