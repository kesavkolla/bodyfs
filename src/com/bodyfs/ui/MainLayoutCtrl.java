package com.bodyfs.ui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.bodyfs.model.LoginInfo;

public class MainLayoutCtrl extends Borderlayout implements AfterCompose, Serializable {

	private static final long serialVersionUID = -6108326798661059775L;
	private static final Map<String, String> metaAttrs = new HashMap<String, String>();
	static {
		metaAttrs.put("name", "keywords");
		metaAttrs.put("content", "Body Feng Shui, Chinese Medicine, Belmont");
	}
	protected transient AnnotateDataBinder binder;
	protected transient Borderlayout main;

	@Override
	public void afterCompose() {
		processRecursive(this, this);
		final Component comp = Executions.createComponents("/pages/common/header.zul", this, null);
		final Label username = (Label) comp.getFellow("username");
		if (username != null) {
			final LoginInfo userInfo = (LoginInfo) this.getAttribute("LOGIN_CREDENTIALS", Page.SESSION_SCOPE);
			if (userInfo != null) {
				username.setValue(userInfo.getUserid());
			}
		}
		// final HeaderInfo metaInfo = new HeaderInfo("meta", metaAttrs);
		// ExecutionsCtrl.getCurrentCtrl().getCurrentPageDefinition().addHeaderInfo(metaInfo);
		Executions.createComponents("/pages/common/footer.zul", this, null);

		Components.wireVariables(this, this); // auto wire variables
		Components.addForwards(this, this); // auto forward
		binder = new AnnotateDataBinder(this);
		binder.loadAll();
	}

	/*
	 * Are there inner window components than wire these too.
	 */
	@SuppressWarnings("unchecked")
	private void processRecursive(final Component main, final Component child) {
		Components.wireVariables(main, child);
		Components.addForwards(main, this);
		List<Component> winList = (List<Component>) child.getChildren();
		for (Component window : winList) {
			if (window instanceof Window || window instanceof Borderlayout) {
				processRecursive(main, window);
			}
		}
	}

}
