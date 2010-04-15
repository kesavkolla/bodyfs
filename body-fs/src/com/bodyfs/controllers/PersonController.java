/*
 * $Id$
 */
package com.bodyfs.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Person;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired()
	private IPersonDAO personDAO;

	public void setPersonDAO(IPersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	private static final Log LOGGER = LogFactory.getLog(PersonController.class);

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody
	Person getPerson(final @PathVariable Long id) {
		try {
			LOGGER.error("id");
			final Person p = personDAO.getPerson(id);
			LOGGER.error(p);
			return p;
		} catch (final Exception e) {
			LOGGER.error(e);
		}
		return null;
	}
}
