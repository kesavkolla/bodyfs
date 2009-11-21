/*
 * $Id$
 */
package com.bodyfs.ui;

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
	private PatientVisit patientVisit = new PatientVisit();
	private IPersonDAO personDAO;
	private Person person;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		person = personDAO.getByEmail("kesavkolla+bodyfs@gmail.com");
		this.page.setAttribute("patvisit", patientVisit);
	}

	public void onSave(final ForwardEvent event) {
		if (patientVisit != null) {
			patientVisit.setPersonId(person != null ? person.getId() : 1L);
			personDAO.createPatientVisit(patientVisit);
			System.out.println(patientVisit.getId());
		}
	}
}
