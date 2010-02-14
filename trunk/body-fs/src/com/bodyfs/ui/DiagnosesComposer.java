/* $Id$ */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.bodyfs.dao.IHerbDAO;
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
		// Clients.evalJavaScript("");
	}

	/**
	 * This event will be triggered when user clicks on add image
	 * 
	 * @param event
	 */
	public void onAddFormula(final ForwardEvent event) {
		final HerbFormula formula = (HerbFormula) event.getOrigin().getTarget().getAttribute("formula");
		final Div divformulas = (Div) Path.getComponent(page, "divformulas");
		final Html html = new Html("<span class='tag'><label><span>" + formula.getName()
				+ "</span><small class='close' title='close'>x</small></label></span>");
		divformulas.appendChild(html);
	}
}
