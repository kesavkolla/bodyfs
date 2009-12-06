/* $Id$ */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.api.Window;

import com.bodyfs.dao.IMPIDao;
import com.bodyfs.model.MPIData;

/**
 * 
 * @author kesav
 * 
 */
public class MPIViewComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -5433027915228714749L;
	private static Log LOGGER = LogFactory.getLog(MPIViewComposer.class);
	private transient static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	public static String format(final Date date) {
		return sdf.format(date);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		System.out.println(comp.getClass());
		System.out.println(comp.getPage());
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		if (Executions.getCurrent().getParameterValues("id") != null) {
			final MPIData mpi = mpiDao.getDataByDate(Long
					.parseLong(Executions.getCurrent().getParameterValues("id")[0]), null);
			if (mpi != null) {
				System.out.println("Setting attribute: " + mpi.getExamDate());
				comp.setAttribute("mpi", mpi);
				comp.getPage().setAttribute("mpi", mpi);
			}
		}
		super.doAfterCompose(comp);
	}

	public Date[] getExamDates() {
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		final Collection<Date> dates = mpiDao.getExamDates(Long.parseLong(Executions.getCurrent().getParameterValues(
				"id")[0]));
		if (dates == null || dates.size() <= 0) {
			return new Date[0];
		}
		final Date[] retDt = new Date[dates.size()];
		return dates.toArray(retDt);
	}

	public void onShow(final ForwardEvent event) {
		LOGGER.debug("show the data model");
		final MPIData mpi = new MPIData();
		mpi.setLU2(200);
		mpi.setPersonId(Long.parseLong(event.getData().toString()));
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("mpi", mpi);
		final Window win = (Window) Executions.createComponents("/pages/patient/mpidata.zul", null, map);
		try {
			win.setAttribute("mpi", mpi);
			win.doModal();
		} catch (final Throwable th) {
			LOGGER.error(th);
		}
	}

	public void onSave(final Event event) {
		final Window win = (Window) event.getTarget();
		win.detach();
		if (event.getData() != null && event.getData().equals("cancel")) {
			return;
		}
		final MPIData mpi = (MPIData) win.getAttribute("mpi");
		mpi.setExamDate(Calendar.getInstance().getTime());
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		mpiDao.addMPIData(mpi);
		LOGGER.debug("Created MPI Data with Id: " + mpi.getId());
	}
}
