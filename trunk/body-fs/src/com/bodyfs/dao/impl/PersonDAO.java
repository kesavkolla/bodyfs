/*
 * $Id$
 */
package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.controllers.PersonController;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;

@Repository(value = "personDAO")
public class PersonDAO implements IPersonDAO, Serializable {

	private static final long serialVersionUID = 8672328220267294005L;
	private static final Log LOGGER = LogFactory.getLog(PersonController.class);
	private transient JdoTemplate jdoTemplate = null;

	@PostConstruct
	public void init() {
		this.jdoTemplate = new JdoTemplate(PMF.get());
		LOGGER.debug("Post construct: " + this.jdoTemplate);
	}

	@Override
	public void createPerson(Person person) {
		this.jdoTemplate.makePersistent(person);
	}

	@Override
	public void deleteAll() {
		this.jdoTemplate.execute(new JdoCallback<Long>() {
			@Override
			public Long doInJdo(PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Person.class);
				return query.deletePersistentAll();
			}

		});
	}

	@Override
	public void deletePerson(Long id) {
		this.jdoTemplate.execute(new JdoCallback<Long>() {
			@Override
			public Long doInJdo(PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Person.class);
				return query.deletePersistentAll();
			}

		});
	}

	@Override
	public Person getPerson(final Long id) {
		return this.jdoTemplate.getObjectById(Person.class, id);
	}

	@Override
	public Collection<Person> getAll() {
		return this.jdoTemplate.execute(new JdoCallback<Collection<Person>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Collection<Person> doInJdo(PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Person.class);
				Collection<Person> lst = (Collection<Person>) query.execute();
				lst.size();
				return lst;
			}

		});

	}

}
