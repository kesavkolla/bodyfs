/* $Id$ */
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
import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.PatientDiagnosis;
import com.bodyfs.model.PatientPrescription;
import com.bodyfs.model.PatientTreatment;
import com.bodyfs.model.PatientVisit;
import com.bodyfs.model.npi.NPIGoals;
import com.bodyfs.model.npi.NPIPatientDiagnosis;

/**
 * 
 * @author kesav
 * 
 */
@Repository(value = "patientVisitDAO")
public class PatientVisitDAO implements IPatientVisitDAO, Serializable {

	private static final long serialVersionUID = -5022100019051536692L;
	private static final Log LOGGER = LogFactory.getLog(PatientVisitDAO.class);
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
	public PatientVisit getPatientVisitByDate(final Long patientId, final Date visitDate) {
		final String filter = "personId==pid" + ((visitDate == null) ? "" : " && visitDate==pdate");
		final String params = "String pid" + ((visitDate == null) ? "" : ", java.util.Date pdate");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("filter: " + filter + "\tparams:" + params);
		}
		final Object[] vals = new Object[visitDate == null ? 1 : 2];
		vals[0] = patientId;
		if (visitDate != null) {
			vals[1] = visitDate;
		}
		final Collection<PatientVisit> visits = this.jdoTemplate.find(PatientVisit.class, filter, params, vals,
				"visitDate descending");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(visits);
		}
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
		return jdoTemplate.find("SELECT visitDate FROM " + PatientVisit.class.getName()
				+ " WHERE personId==pid PARAMETERS Long pid  ORDER BY visitDate DESC", vals);
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("filter: " + filter + "\tparams:" + params);
		}
		final Object[] vals = new Object[visitDate == null ? 1 : 2];
		vals[0] = patientId;
		if (visitDate != null) {
			vals[1] = visitDate;
		}
		final Collection<PatientDiagnosis> diagnosis = this.jdoTemplate.find(PatientDiagnosis.class, filter, params,
				vals, "visitDate descending");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(diagnosis);
		}
		if (diagnosis != null && diagnosis.size() > 0) {
			return diagnosis.iterator().next();
		}
		return null;
	}

	@Override
	public NPIPatientDiagnosis getPatientNPIDiagnosisByDate(final Long patientId) {
		try {
			return jdoTemplate.find(NPIPatientDiagnosis.class, "personId == pid", "Long pid", patientId).iterator()
					.next();
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public PatientDiagnosis createPatientDiagnosis(final PatientDiagnosis patDiagnosis) {
		return this.jdoTemplate.makePersistent(patDiagnosis);
	}

	@Override
	public NPIPatientDiagnosis createNPIPatientDiagnosis(final NPIPatientDiagnosis patDiagnosis) {
		return this.jdoTemplate.makePersistent(patDiagnosis);
	}

	@Override
	public PatientTreatment createPatientTreatment(final PatientTreatment patTreatment) {
		return this.jdoTemplate.makePersistent(patTreatment);
	}

	@Override
	public PatientTreatment getPatientTreatmentByDate(final Long patientId, final Date visitDate) {
		final String filter = "personId==pid" + ((visitDate == null) ? "" : " && visitDate==pdate");
		final String params = "String pid" + ((visitDate == null) ? "" : ", java.util.Date pdate");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("filter: " + filter + "\tparams:" + params);
		}
		final Object[] vals = new Object[visitDate == null ? 1 : 2];
		vals[0] = patientId;
		if (visitDate != null) {
			vals[1] = visitDate;
		}
		final Collection<PatientTreatment> treatments = this.jdoTemplate.find(PatientTreatment.class, filter, params,
				vals, "visitDate descending");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(treatments);
		}
		if (treatments != null && treatments.size() > 0) {
			return treatments.iterator().next();
		}
		return null;
	}

	@Override
	public PatientPrescription createPatientPrescription(final PatientPrescription prescription) {
		return this.jdoTemplate.makePersistent(prescription);
	}

	@Override
	public PatientPrescription getPatientPrescriptionByDate(final Long patientId, final Date visitDate) {
		final String filter = "personId==pid" + ((visitDate == null) ? "" : " && visitDate==pdate");
		final String params = "Long pid" + ((visitDate == null) ? "" : ", java.util.Date pdate");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("filter: " + filter + "\tparams:" + params);
		}
		final Object[] vals = new Object[visitDate == null ? 1 : 2];
		vals[0] = patientId;
		if (visitDate != null) {
			vals[1] = visitDate;
		}
		final Collection<PatientPrescription> prescriptions = this.jdoTemplate.find(PatientPrescription.class, filter,
				params, vals, "visitDate descending");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(prescriptions);
		}
		if (prescriptions != null && prescriptions.size() > 0) {
			return prescriptions.iterator().next();
		}
		return null;
	}

	@Override
	public NPIGoals createNPIGoals(final NPIGoals goals) {
		return jdoTemplate.makePersistent(goals);
	}

	@Override
	public NPIGoals getNNPIGoalsByPatientId(final Long patid) {
		try {
			return jdoTemplate.find(NPIGoals.class, "personId == pid", "Long pid", patid).iterator().next();
		} catch (final Exception e) {
			return null;
		}
	}
}
