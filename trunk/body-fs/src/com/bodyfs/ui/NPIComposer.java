package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Include;

import com.bodyfs.model.Person;

public class NPIComposer extends GenericAutowireComposer {

	private static final long serialVersionUID = -4039933079355260867L;
	private static Log LOGGER = LogFactory.getLog(MainWindowComposer.class);
	final Person person = new Person();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		LOGGER.error("Inside NPI composer");
		LOGGER.error("Page: " + comp.getPage());
		this.page.setAttribute("person", person);
	}

	public String getMinDOB() {
		return sdf.format(Calendar.getInstance().getTime());
	}

	public void onNext(final ForwardEvent event) {
		final String pageId = event.getPage().getId();
		final Include xcontents = (Include) Path.getComponent("//index/xcontents");
		System.out.println("Page Id: " + pageId);
		if (pageId.equals("npi")) {
			xcontents.setSrc("/WEB-INF/views/npi1.zul");
			return;
		}
		if (pageId.equals("npi1")) {
			xcontents.setSrc("/WEB-INF/views/npi2.zul");
			return;
		}
		if (pageId.equals("npi2")) {
			xcontents.setSrc("/WEB-INF/views/npi3.zul");
			return;
		}
	}

	public void onPrev(final ForwardEvent event) {
		final String pageId = event.getPage().getId();
		System.out.println("OnPrev: PageId " + pageId);
		final Include xcontents = (Include) Path.getComponent("//index/xcontents");
		System.out.println(xcontents);
		if (pageId.equals("npi3")) {
			xcontents.setSrc("/WEB-INF/views/npi2.zul");
			return;
		}
		if (pageId.equals("npi2")) {
			xcontents.setSrc("/WEB-INF/views/npi1.zul");
			return;
		}
		if (pageId.equals("npi1")) {
			xcontents.setSrc("/WEB-INF/views/npi.zul");
			return;
		}
	}

	public void saveNPI(final ForwardEvent event) {

	}
}
