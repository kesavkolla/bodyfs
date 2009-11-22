package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.GeneralInfo;
import com.bodyfs.model.PatientVisit;
import com.bodyfs.model.Person;

public interface IPersonDAO {
	public Person getPerson(Long id);

	public Collection<Person> getAll();

	public Person createPerson(final Person person);

	public Person getByEmail(final String email);

	public void deletePerson(final Long id);

	public void deleteAll();

	public void createGeneralInfo(final GeneralInfo ginfo);

	public PatientVisit createPatientVisit(final PatientVisit visit);

	public Collection<PatientVisit> GetPatientVisits(final Long personId);

	public int countPatientVisits(final Long patid);
}
