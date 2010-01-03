package com.bodyfs.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public class PrescriptionComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -6226037505168911819L;

	@Override
	public ComponentInfo doBeforeCompose(final Page page, final Component parent, final ComponentInfo compInfo) {
		if (execution.getParameter("") == null) {
			execution.sendRedirect("/pages/common/error.zul");
			return compInfo;
		}
		return super.doBeforeCompose(page, parent, compInfo);
	}

}
