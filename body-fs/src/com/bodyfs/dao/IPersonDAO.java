package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.Person;

public interface IPersonDAO {
	public Person getPerson(Long id);

	public Collection<Person> getAll();

	public void createPerson(final Person person);

	public void deletePerson(final Long id);

	public void deleteAll();

}
