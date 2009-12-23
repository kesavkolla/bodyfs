/*
 * $Id$
 */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.api.Div;
import org.zkoss.zul.api.Grid;
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
	Grid detailsGrid;

	private transient static SimpleDateFormat sdf = new SimpleDateFormat(
			"MM/dd/yyyy");

	public static String format(final Date date) {
		return sdf.format(date);
	}

	public final String getVisitsDates() {
		final JSONArray arr = new JSONArray();
		if (patvisits != null) {
			for (String key : patvisits.keySet()) {
				arr.add(key);
			}
		}
		return arr.toJSONString();
	}

	private static Log LOGGER = LogFactory.getLog(WeeklyVisitComposer.class);

	static TreeMap<String, PatientVisit> patvisits;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		visitgrid = (Div) comp;
		comp.getPage().setAttribute("personid",
				Executions.getCurrent().getParameter("id"));
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil
				.getBean("personDAO");
		patvisits = new TreeMap<String, PatientVisit>();
		for (PatientVisit visit : personDAO.GetPatientVisits(Long
				.parseLong(Executions.getCurrent().getParameter("id")))) {
			String datekey = format(visit.getVisitDate());
			if (patvisits.get(datekey) != null) {
				int i = 1;
				do {
					if (patvisits.get(datekey + "-" + i) != null) {
						i++;
						continue;
					}
					datekey = datekey + "-" + i;
					break;
				} while (true);
			}

			patvisits.put(datekey, visit);
		}
		if (patvisits.size() > 0)
			this.page.setAttribute("patvisit", patvisits.firstEntry()
					.getValue());
		else {
			this.page.setAttribute("patvisit", new PatientVisit());
		}
		// this.page.setAttribute("patvisit", new PatientVisit());
		// Clients.evalJavaScript("initPagination()");

	}

	public void onSayHello(Event evt) {
		System.out.println("hello");
		// evt.getTarget().appendChild(new Label("Hello"));
	}

	public void onSave(final ForwardEvent event) {
		if (page.getAttribute("personid") == null
				|| page.getAttribute("patvisit") == null) {
			return;
		}
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil
				.getBean("personDAO");
		final PatientVisit patvisit = (PatientVisit) page
				.getAttribute("patvisit");
		if (patvisit != null) {
			patvisit.setPersonId(Long.parseLong(page.getAttribute("personid")
					.toString()));
			personDAO.createPatientVisit(patvisit);
		}
	}

	public void onBlur$sss(Event evt) {
		if (sss == null) {
			return;
		}
		System.out.println("COMING!!!!" + sss.getValue());
		this.page.setAttribute("patvisit", patvisits.get(sss.getValue()));
		final DataBinder binder = (DataBinder) visitgrid.getAttribute("binder");
		if (binder != null) {
			binder.bindBean("patvisit", patvisits.get(sss.getValue()));
			binder.loadAll();
		}
	}

	public void onNoteAdd(final Event event) {

		LOGGER.error(event.getData());
	}
}
