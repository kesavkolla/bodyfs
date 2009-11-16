package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.GeneralInfo;
import com.bodyfs.model.Person;

public interface IPersonDAO {
	public Person getPerson(Long id);

	public Collection<Person> getAll();

	public Person createPerson(final Person person);

	public void deletePerson(final Long id);

	public void deleteAll();

	public void createGeneralInfo(final GeneralInfo ginfo);
}
