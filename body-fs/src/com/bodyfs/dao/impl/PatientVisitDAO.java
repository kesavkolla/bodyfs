package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.controllers.PersonController;
import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.PatientDiagnosis;
import com.bodyfs.model.PatientVisit;

@Repository(value = "patientVisitDAO")
public class PatientVisitDAO implements IPatientVisitDAO, Serializable {

	private static final long serialVersionUID = -5022100019051536692L;
	@SuppressWarnings("unused")
	private static final Log LOGGER = LogFactory.getLog(PersonController.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Override
	public PatientVisit createPatientVisit(final PatientVisit visit) {
		return this.jdoTemplate.makePersistent(visit);
	}

	@Override
	public Collection<PatientVisit> getPatientVisits(final Long personId) {
		return this.jdoTemplate.find(PatientVisit.class, "personId ==" + personId, "visitDate desc");
	}

	@Override
	public PatientVisit getPatientVisitByDate(final Long personId, final Date visitDate) {
		final Collection<PatientVisit> visits = this.jdoTemplate.find(PatientVisit.class,
				"personId==pid && visitDate==pdate", "String pid, java.util.Date pdate", personId, visitDate);
		if (visits != null && visits.size() > 0) {
			return visits.iterator().next();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Date> getPatientVisitDates(final Long personId) {
		final Map<String, Object> vals = new HashMap<String, Object>();
		vals.put("pid", personId);
		return jdoTemplate
				.find(
						"SELECT visitDate FROM com.bodyfs.model.PatientVisit WHERE personId==pid PARAMETERS Long pid  ORDER BY visitDate DESC",
						vals);
	}

	@Override
	public int countPatientVisits(final Long patid) {
		final Collection<Date> dates = getPatientVisitDates(patid);
		if (dates == null) {
			return 0;
		}
		return dates.size();
	}

	@Override
	public PatientDiagnosis getPatientDiagnosisByDate(final Long patientId, final Date visitDate) {
		final String filter = "personId==pid" + ((visitDate == null) ? "" : " && visitDate==pdate");
		final String params = "String pid" + ((visitDate == null) ? "" : ", java.util.Date pdate");
		final Object[] vals = new Object[visitDate == null ? 1 : 2];
		vals[0] = patientId;
		if (visitDate != null) {
			vals[1] = visitDate;
		}
		final Collection<PatientDiagnosis> diagnosis = this.jdoTemplate.find(PatientDiagnosis.class, filter, params,
				vals, "visitDate desc");
		if (diagnosis != null && diagnosis.size() > 0) {
			return diagnosis.iterator().next();
		}
		return null;
	}

	@Override
	public PatientDiagnosis createPatientDiagnosis(final PatientDiagnosis patDiagnosis) {
		return this.jdoTemplate.makePersistent(patDiagnosis);
	}
}
