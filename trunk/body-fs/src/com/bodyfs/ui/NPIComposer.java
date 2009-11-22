/*
 * $Id$
 */
package com.bodyfs.ui;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;

import com.bodyfs.dao.IPageDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.GeneralInfo;
import com.bodyfs.model.Page;
import com.bodyfs.model.Person;

/**
 * 
 * @author kesav
 * 
 */
public class NPIComposer extends GenericAutowireComposer {

	private static final long serialVersionUID = -4039933079355260867L;
	private static Log LOGGER = LogFactory.getLog(NPIComposer.class);
	private IPageDAO pageDAO = (IPageDAO) SpringUtil.getBean("pageDAO");

	public static final String SESSION_PERSON = "session.person";
	public static final String SESSION_GENERALINFO = "session.ginfo";

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.setupPerson();
		// this.page.setAttribute("person", person);
	}

	public void onNext(final ForwardEvent event) {
		final String pageId = event.getData().toString();
		final Include xcontents = (Include) Path.getComponent("//usermgmt/usermgmtinclude");
		LOGGER.debug("Page Id: " + pageId);
		System.out.println("Page Id: " + pageId);
		final Page page = pageDAO.getById(pageId);
		LOGGER.debug("Navigating to:" + page.getPath());
		xcontents.setSrc(page.getPath());
		this.desktop.setBookmark(pageId);
	}

	public void onSaveNPI(final ForwardEvent event) {
		final Person person = (Person) sessionScope.get(SESSION_PERSON);
		if (person == null) {
			cleanSession(sessionScope);
			return;
		}
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		personDAO.createPerson(person);
		final GeneralInfo ginfo = (GeneralInfo) sessionScope.get(SESSION_GENERALINFO);
		ginfo.setPersonId(person.getId());
		personDAO.createGeneralInfo(ginfo);
		LOGGER.error("Person saved with Id:" + person.getId());
		cleanSession(sessionScope);
	}

	@SuppressWarnings("unchecked")
	public static void cleanSession(final Map sessionScope) {
		sessionScope.remove(SESSION_GENERALINFO);
		sessionScope.remove(SESSION_PERSON);
	}

	public void onCancel(final ForwardEvent event) {
		cleanSession(sessionScope);
	}

	@SuppressWarnings("unchecked")
	private void setupPerson() {
		Person person = null;
		GeneralInfo ginfo = null;
		if (sessionScope.get(SESSION_PERSON) == null) {
			person = new Person();
			ginfo = new GeneralInfo();
			sessionScope.put(SESSION_PERSON, person);
			sessionScope.put(SESSION_GENERALINFO, ginfo);
		} else {
			person = (Person) sessionScope.get(SESSION_PERSON);
			ginfo = (GeneralInfo) sessionScope.get(SESSION_GENERALINFO);
		}
		this.page.setAttribute("person", person);
		this.page.setAttribute("ginfo", ginfo);
	}
}
