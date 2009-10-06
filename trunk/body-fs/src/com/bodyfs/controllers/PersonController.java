package com.bodyfs.controllers;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private IPersonDAO personDAO;

	private static final Log LOGGER = LogFactory.getLog(PersonController.class);

	@RequestMapping(value = "/{id}")
	@ModelAttribute("model")
	public Person getPerson(final @PathVariable Long id) {
		return personDAO.getPerson(id);
	}

	@PostConstruct
	public void initPersons() {
		if (personDAO.getAll().size() > 0) {
			LOGGER.error(personDAO.getAll());
			return;
		}
		Person p1 = new Person();
		p1.setFirstName("Kesav Kumar");
		p1.setLastName("Kolla");
		personDAO.createPerson(p1);
		LOGGER.error("Created person1:" + p1.getId());
		Person p2 = new Person();
		p2.setFirstName("Shivani");
		p2.setLastName("Kolla");
		personDAO.createPerson(p2);
		Person p3 = new Person();
		p3.setFirstName("Shreya");
		p3.setLastName("Kolla");
		personDAO.createPerson(p3);
		LOGGER.error(personDAO.getAll());
	}

}
