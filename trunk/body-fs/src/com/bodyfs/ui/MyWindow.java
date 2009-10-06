package com.bodyfs.ui;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericAutowireComposer;

import com.bodyfs.dao.IPersonDAO;

public class MyWindow extends GenericAutowireComposer implements Serializable {

	private IPersonDAO personDAO;
	private static final long serialVersionUID = -8461360026448034002L;

	private static Log LOGGER = LogFactory.getLog(MyWindow.class);

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		LOGGER.error(this.page);
		this.page.setAttribute("person", personDAO.getPerson(2L));
	}

}
