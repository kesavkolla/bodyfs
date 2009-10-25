package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;

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

	public void onNext(ForwardEvent event) {
		LOGGER.error(event);
		LOGGER.error(person);
	}
}
