/*
 * $Id$
 */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import com.bodyfs.dao.IPageDAO;
import com.bodyfs.model.Page;

/**
 * 
 * @author kesav
 * 
 */
public class MainWindowComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 7255967269134815134L;
	private static Log LOGGER = LogFactory.getLog(MainWindowComposer.class);

	private IPageDAO pageDAO;
	private Include xcontents;
	private Label username;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		/*
		 * final OpenIdUser user = (OpenIdUser)
		 * this.requestScope.get(OpenIdUser.ATTR_NAME); final Map<String,
		 * String> userinfo = (Map<String, String>) user.getAttribute("info");
		 * if (userinfo != null) { username.setValue(userinfo.get("lastname") +
		 * ", " + userinfo.get("firstname")); }
		 */
		if (pageDAO == null) {
			pageDAO = (IPageDAO) SpringUtil.getBean("pageDAO");
		}
	}

	public void onHandleClick(final ForwardEvent event) {
		final String pageid = (String) event.getData();
		if (pageid == null || pageid.equals("")) {
			return;
		}
		final Page page = pageDAO.getById(pageid);
		if (page == null) {
			return;
		}

		xcontents.setSrc(page.getPath());
		if (page.getTitle() != null) {
			this.page.setTitle(page.getTitle());
		}
		this.desktop.setBookmark(page.getId());
	}

	public void onBookmarkChange$main(final BookmarkEvent event) {
		final String pageid = event.getBookmark();
		LOGGER.debug("Navigated to bookmark: " + pageid);
		if (pageid == null || pageid.equals("")) {
			return;
		}
		final Page page = pageDAO.getById(pageid);
		if (page == null) {
			return;
		}
		LOGGER.debug("Navigating to : " + page.getPath());
		if (page.getTarget() == null) {
			xcontents.setSrc(page.getPath());
		} else {
			final Include target = (Include) Path.getComponent(page.getTarget());
			/*
			 * if (target == null) { return; }
			 */
			target.setSrc(page.getPath());
		}

		return;
	}
}
