/*
 * $Id$
 */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.Component;
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
	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		if (sessionScope.get("CURRENT_PATIENT_ID") != null) {
			person = personDAO.getPerson((Long) sessionScope.get("CURRENT_PATIENT_ID"));
		} else {
			person = personDAO.getByEmail("kesavkolla+bodyfs@gmail.com");
		}
		this.patientVisit = new PatientVisit();
	}

	public void onSave(final ForwardEvent event) {
		if (patientVisit != null) {
			patientVisit.setPersonId(person != null ? person.getId() : 1L);
			personDAO.createPatientVisit(patientVisit);
		}
	}
}
