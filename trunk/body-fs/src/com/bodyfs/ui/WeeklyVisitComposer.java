/*
 * $Id$
 */
package com.bodyfs.ui;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.api.Div;
import org.zkoss.zul.api.Textbox;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.PatientVisit;

/**
 * 
 * @author kesav
 * 
 */
public class WeeklyVisitComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 3816734829122660780L;
	AnnotateDataBinder binder;
	Textbox sss;
	Div visitgrid;
	
	public final int getVisitsCount() {
		if(patvisits == null)
			return 0;
		return patvisits.size();
	}
	
	public void onPageSelection(final Event event){
		Executions.getCurrent().getParameterMap();
		System.out.println("COMING!!!!!");
		visitgrid.getPage().setAttribute("patvisit", patvisits.get(Integer.parseInt("1")));
		visitgrid.invalidate();
	}

	private static Log LOGGER = LogFactory.getLog(WeeklyVisitComposer.class);
	
	static ArrayList<PatientVisit> patvisits;
	
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		visitgrid = (Div)comp;
		binder = new AnnotateDataBinder(comp);
		comp.getPage().setAttribute("personid", Executions.getCurrent().getParameter("id"));
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		patvisits = new ArrayList<PatientVisit>();
		patvisits.addAll(personDAO.GetPatientVisits(Long.parseLong(Executions.getCurrent().getParameter("id"))));
		if(patvisits.size() > 0)
			comp.getPage().setAttribute("patvisit", patvisits.get(0));
		else{
			comp.getPage().setAttribute("patvisit", new PatientVisit());
		}
		//Clients.evalJavaScript("initPagination()");
		super.doAfterCompose(comp);
		binder.loadAll();

	}
	
	public void onSayHello(Event evt) {
		System.out.println("hello");
		//evt.getTarget().appendChild(new Label("Hello"));
	}

	public void onSave(final ForwardEvent event) {
		if (page.getAttribute("personid") == null || page.getAttribute("patvisit") == null) {
			return;
		}
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final PatientVisit patvisit = (PatientVisit) page.getAttribute("patvisit");
		if (patvisit != null) {
			patvisit.setPersonId(Long.parseLong(page.getAttribute("personid").toString()));
			personDAO.createPatientVisit(patvisit);
		}
	}
	
	public void onClick$sss(Event evt){
		if(sss==null){
			return;
		}
		System.out.println("COMING!!!!");
		visitgrid.getPage().setAttribute("patvisit", patvisits.get(Integer.parseInt(sss.getValue())));
		visitgrid.invalidate();
	}
	
	public void onNoteAdd(final Event event) {

		LOGGER.error(event.getData());
	}
}
