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
	final Person person = new Person();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private IPageDAO pageDAO;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (pageDAO == null) {
			pageDAO = (IPageDAO) SpringUtil.getBean("pageDAO");
		}
		this.page.setAttribute("person", person);
	}

	public String getMinDOB() {
		return sdf.format(Calendar.getInstance().getTime());
	}

	public void onNext(final ForwardEvent event) {
		final String pageId = event.getData().toString();
		final Include xcontents = (Include) Path.getComponent("//index/xcontents");
		LOGGER.debug("Page Id: " + pageId);
		System.out.println("Page Id: " + pageId);
		final Page page = pageDAO.getById(pageId);
		LOGGER.debug("Navigating to:" + page.getPath());
		xcontents.setSrc(page.getPath());
	}

	public void onSaveNPI(final ForwardEvent event) {

	}
}
