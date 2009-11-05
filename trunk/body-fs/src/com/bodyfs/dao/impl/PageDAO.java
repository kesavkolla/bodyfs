/*
 * $Id$
 */
package com.bodyfs.dao.impl;

import java.util.ArrayList;
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
import com.bodyfs.dao.IPageDAO;
import com.bodyfs.model.Page;

@Repository(value = "pageDAO")
public class PageDAO implements IPageDAO {
	private static final Log LOGGER = LogFactory.getLog(PersonController.class);
	private transient JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Override
	public Page getById(String id) {
		LOGGER.debug("Getting page object for id: " + id);
		return jdoTemplate.getObjectById(Page.class, id);
	}

	@Override
	public Collection<Page> getAll() {
		return this.jdoTemplate.execute(new JdoCallback<Collection<Page>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Collection<Page> doInJdo(PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Page.class);
				Collection<Page> lst = (Collection<Page>) query.execute();
				lst.size();
				return lst;
			}

		});
	}

	@PostConstruct
	public void init() {
		Collection<Page> pages = getAll();
		if (pages == null || pages.size() <= 0) {
			pages = new ArrayList<Page>();
		}
		if (pages.size() <= 0) {
			pages.add(new Page("npi", "NPI Form", "/WEB-INF/views/npi.zul"));
			pages.add(new Page("npi1", "NPI Form", "/WEB-INF/views/npi1.zul"));
			pages.add(new Page("npi2", "NPI Form", "/WEB-INF/views/npi2.zul"));
			pages.add(new Page("npi3", "NPI Form", "/WEB-INF/views/npi3.zul"));
			jdoTemplate.makePersistentAll(pages);
		}
	}
}
