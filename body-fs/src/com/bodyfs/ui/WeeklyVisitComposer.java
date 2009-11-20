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

/**
 * 
 * @author kesav
 * 
 */
public class WeeklyVisitComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 3816734829122660780L;
	private PatientVisit patientVisit = new PatientVisit();
	private IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.page.setAttribute("patvisit", patientVisit);
		for (final PatientVisit visit : this.personDAO.GetPatientVisits(1L)) {
			System.out.println(visit.getVisitDate());
		}
	}

	public void onSave(final ForwardEvent event) {
		if (patientVisit != null) {
			patientVisit.setPersonId(1L);
			personDAO.createPatientVisit(patientVisit);
			System.out.println(patientVisit.getId());
		}
	}
}
