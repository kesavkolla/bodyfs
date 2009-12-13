/*
 * $Id$
 */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.GeneralInfo;
import com.bodyfs.model.Person;
import com.bodyfs.model.PersonType;

/**
 * 
 * @author kesav
 * 
 */
@SuppressWarnings("unchecked")
public class NPIComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -4039933079355260867L;
	private static Log LOGGER = LogFactory.getLog(NPIComposer.class);

	public static final String SESSION_PERSON = "session.person";
	public static final String SESSION_GENERALINFO = "session.ginfo";

	private Include npiinclude;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.setupPerson();
	}

	public void onNext(final ForwardEvent event) {
		final String pageId = event.getData().toString();
		desktop.setBookmark(pageId);
		npiinclude.setSrc("/pages/usermgmt/" + pageId + ".zul");
	}

	public void onBookmarkChange(final BookmarkEvent event) {
		final String pageid = event.getBookmark();
		if (pageid != null && !pageid.equals("")) {
			npiinclude.setSrc("/pages/usermgmt/" + pageid + ".zul");
		} else {
			npiinclude.setSrc("/pages/usermgmt/npi1.zul");
		}
	}

	public void onSaveNPI(final ForwardEvent event) {
		final Person person = (Person) sessionScope.get(SESSION_PERSON);
		if (person == null) {
			cleanSession();
			return;
		}
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		person.setPersonType(PersonType.PRE_USER);
		personDAO.createPerson(person);
		final GeneralInfo ginfo = (GeneralInfo) sessionScope.get(SESSION_GENERALINFO);
		ginfo.setPersonId(person.getId());
		personDAO.createGeneralInfo(ginfo);
		LOGGER.error("Person saved with Id:" + person.getId());
		cleanSession();
		execution.sendRedirect("/pages/usermgmt/customersearch.zul");
	}

	public void cleanSession() {
		sessionScope.remove(SESSION_GENERALINFO);
		sessionScope.remove(SESSION_PERSON);
	}

	public void onCancel(final ForwardEvent event) {
		cleanSession();
		execution.sendRedirect("/pages/usermgmt/customersearch.zul");
	}

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
		page.setAttribute("person", person);
		page.setAttribute("ginfo", ginfo);
	}
}
