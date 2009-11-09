/*
 * $Id$
 */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;

import com.bodyfs.dao.IPageDAO;
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
	private static Log LOGGER = LogFactory.getLog(MainWindowComposer.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private IPageDAO pageDAO = (IPageDAO) SpringUtil.getBean("pageDAO");

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.setupPerson();
		// this.page.setAttribute("person", person);
	}

	public String getMinDOB() {
		return sdf.format(Calendar.getInstance().getTime());
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

	}

	public void onCancel(final ForwardEvent event) {

	}

	@SuppressWarnings("unchecked")
	private void setupPerson() {
		Person person = null;
		GeneralInfo ginfo = null;
		if (sessionScope.get("session.person") == null) {
			person = new Person();
			ginfo = new GeneralInfo();
			sessionScope.put("session.person", person);
			this.page.setAttribute("person", person);
		} else {
			person = (Person) sessionScope.get("session.person");
			ginfo = (GeneralInfo) sessionScope.get("session.ginfo");
		}
		this.page.setAttribute("person", person);
		this.page.setAttribute("ginfo", ginfo);
	}
}
