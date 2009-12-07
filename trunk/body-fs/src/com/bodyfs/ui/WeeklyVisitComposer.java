/*
 * $Id$
 */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.PatientVisit;
import com.bodyfs.model.Person;

/**
 * 
 * @author kesav
 * 
 */
public class WeeklyVisitComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 3816734829122660780L;
	private PatientVisit patientVisit;
	private IPersonDAO personDAO;
	private Person person;

	private static Log LOGGER = LogFactory.getLog(WeeklyVisitComposer.class);

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		if (Executions.getCurrent().getAttribute("id")!= null) {//("CURRENT_PATIENT_ID") 
			person = personDAO.getPerson((Long) Executions.getCurrent().getAttribute("id"));
		} else {
			person = personDAO.getByEmail("kesavkolla+bodyfs@gmail.com");
		}
		this.patientVisit = new PatientVisit();
		super.doAfterCompose(comp);
		
	}

	public void onSave(final ForwardEvent event) {
		if (patientVisit != null) {
			patientVisit.setPersonId(person != null ? person.getId() : 1L);
			personDAO.createPatientVisit(patientVisit);
		}
	}
	public int visitsCount(){
		
		return 3;
	}
	public void onNoteAdd(final Event event) {

		LOGGER.error(event.getData());
	}
}
