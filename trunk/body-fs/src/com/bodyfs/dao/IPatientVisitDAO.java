/* $Id$ */
package com.bodyfs.dao;

import java.util.Collection;
import java.util.Date;

import com.bodyfs.model.PatientDiagnosis;
import com.bodyfs.model.PatientTreatment;
import com.bodyfs.model.PatientVisit;

/**
 * This interface provides methods that are rleated to patient visit
 * 
 * @author kesav
 * 
 */
public interface IPatientVisitDAO {
	/**
	 * Creates a new patient visit
	 * 
	 * @param visit
	 * @return
	 */
	public PatientVisit createPatientVisit(final PatientVisit visit);

	/**
	 * 
	 * @param personId
	 * @return return all the patientvisits for a given patient
	 */
	public Collection<PatientVisit> getPatientVisits(final Long personId);

	/**
	 * 
	 * @param personId
	 * @return return all the visit dates for a given patient in chronological
	 *         order
	 */
	public Collection<Date> getPatientVisitDates(final Long personId);

	/**
	 * 
	 * @param personId
	 * @param visitDate
	 * @return PatientVisit corresponds to the person id and the given date
	 */
	public PatientVisit getPatientVisitByDate(final Long personId, final Date visitDate);

	/**
	 * 
	 * @param patid
	 * @return count the number of patient visits
	 */
	public int countPatientVisits(final Long patid);

	/**
	 * 
	 * @param patientId
	 * @param visitDate
	 * @return Patient Diagnosis object corresponds to the given patient id and
	 *         visitdate
	 */
	public PatientDiagnosis getPatientDiagnosisByDate(final Long patientId, final Date visitDate);

	/**
	 * Persists a new patient diagnosis object
	 * 
	 * @param patDiagnosis
	 * @return
	 */
	public PatientDiagnosis createPatientDiagnosis(final PatientDiagnosis patDiagnosis);

	/**
	 * Persists the given PatientTreatment object
	 * 
	 * @param patTreatment
	 * @return
	 */
	public PatientTreatment createPatientTreatment(final PatientTreatment patTreatment);

	/**
	 * 
	 * @param patientId
	 * @param visitDate
	 * @return Patient Treatment object that corresponds to the given patient id
	 *         and visit date
	 */
	public PatientTreatment getPatientTreatmentByDate(final Long patientId, final Date visitDate);

}
