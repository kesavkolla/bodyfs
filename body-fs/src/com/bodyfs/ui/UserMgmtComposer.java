package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;

import com.bodyfs.dao.IPageDAO;
import com.bodyfs.model.Page;

public class UserMgmtComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -8219699825466400269L;

	private static Log LOGGER = LogFactory.getLog(UserMgmtComposer.class);
	private IPageDAO pageDAO = (IPageDAO) SpringUtil.getBean("pageDAO");

	private Include usermgmtinclude;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	private void navigatePage(final String pageid, boolean bookmark) {
		if (pageid == null || pageid.equals("")) {
			return;
		}
		final Page page = pageDAO.getById(pageid);
		if (page == null) {
			return;
		}
		LOGGER.debug("Navigating to : " + page.getPath());
		if (page.getTitle() != null) {
			this.page.setTitle(page.getTitle());
		}
		usermgmtinclude.setSrc(page.getPath());
		if (bookmark) {
			this.desktop.setBookmark(page.getId());
		}
	}

	public void onHandleClick(final ForwardEvent event) {
		final String pageid = (String) event.getData();
		if (pageid.equals("npi")) {
			NPIComposer.cleanSession(sessionScope);
		}
		navigatePage(pageid, true);
	}
}
