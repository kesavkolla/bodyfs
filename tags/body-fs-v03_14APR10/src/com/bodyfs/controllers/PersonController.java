/*
 * $Id$
 */
package com.bodyfs.controllers;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Gender;
import com.bodyfs.model.Person;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired()
	private IPersonDAO personDAO;

	private static final Log LOGGER = LogFactory.getLog(PersonController.class);

	@RequestMapping(value = "/{id}")
	@ModelAttribute("model")
	public Person getPerson(final @PathVariable Long id) {
		return personDAO.getPerson(id);
	}
	
	@RequestMapping(value = "/detail/{id}")
	public ModelAndView detail(final @PathVariable Long id) {
		LOGGER.debug("Entering details");
		final ModelAndView mv = new ModelAndView("person");

		mv.addObject("person", personDAO.getPerson(id));
		mv.addObject("testmsg", "This is a test message");
		return mv;
	}

	@PostConstruct
	public void initPersons() {
		if (personDAO.getAll().size() > 0) {
			LOGGER.debug(personDAO.getAll());
			return;
		}
		Person p1 = new Person();
		p1.setFirstName("Kesav Kumar");
		p1.setLastName("Kolla");
		p1.setGender(Gender.MALE);
		p1.setEmail("kesav@bodyfs.com");
		p1.setMobilePhone("5104024928");
		personDAO.createPerson(p1);
		LOGGER.debug("Created person1:" + p1.getId());
		Person p2 = new Person();
		p2.setFirstName("Amit");
		p2.setLastName("Agrawal");
		p2.setGender(Gender.MALE);
		p2.setEmail("amitagrawal84@bodyfs.com");
		p2.setMobilePhone("9886650530");
		personDAO.createPerson(p2);
		Person p3 = new Person();
		p3.setFirstName("NeelKamal");
		p3.setLastName("Agrawal");
		p3.setGender(Gender.MALE);
		p3.setEmail("neelkamal@gmail.com");
		p3.setMobilePhone("6504307843");
		personDAO.createPerson(p3);
		LOGGER.debug(personDAO.getAll());
	}

}
