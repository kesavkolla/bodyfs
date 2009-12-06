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
import org.zkoss.json.JSONObject;
import org.zkoss.json.JSONValue;
import org.zkoss.zk.au.out.AuInvoke;
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
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		System.out.println("Id: " + Executions.getCurrent().getParameter("id"));
		if (Executions.getCurrent().getParameterValues("id") != null) {
			final MPIData mpi = mpiDao.getDataByDate(Long
					.parseLong(Executions.getCurrent().getParameterValues("id")[0]), null);
			System.out.println("MPI: " + mpi);
			if (mpi != null) {
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
		System.out.println(dates);
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

	/**
	 * 
	 * @param event
	 */
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

	@SuppressWarnings("unchecked")
	public void onKesav(final Event event) {
		final Execution execution = Executions.getCurrent();
		if (execution.getParameter("data_0") == null) {
			return;
		}
		final JSONObject inpData = (JSONObject) JSONValue.parse(execution.getParameter("data_0"));
		if (!inpData.containsKey("patid")) {
			return;
		}
		if (!inpData.containsKey("examDate")) {
			return;
		}
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		final MPIData mpiData = mpiDao.getDataByDate(new Long((Integer) inpData.get("patid")), new Date((Long) inpData
				.get("examDate")));
		if (mpiData == null) {
			return;
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("personId", mpiData.getPersonId());
		jsonObj.put("examDate", mpiData.getExamDate().getTime());
		jsonObj.put("LU1", mpiData.getLU1());
		jsonObj.put("LU2", mpiData.getLU2());
		jsonObj.put("p1", mpiData.getP1());
		jsonObj.put("p2", mpiData.getP2());
		jsonObj.put("HT1", mpiData.getHT1());
		jsonObj.put("HT2", mpiData.getHT2());
		jsonObj.put("SI1", mpiData.getSI1());
		jsonObj.put("SI2", mpiData.getSI2());
		jsonObj.put("TH1", mpiData.getTH1());
		jsonObj.put("TH2", mpiData.getTH2());
		jsonObj.put("LI1", mpiData.getLI1());
		jsonObj.put("LI2", mpiData.getLI2());
		jsonObj.put("SP1", mpiData.getSP1());
		jsonObj.put("SP2", mpiData.getSP2());
		jsonObj.put("LV1", mpiData.getLV1());
		jsonObj.put("LV2", mpiData.getLV2());
		jsonObj.put("KI1", mpiData.getKI1());
		jsonObj.put("KI2", mpiData.getKI2());
		jsonObj.put("BL1", mpiData.getBL1());
		jsonObj.put("BL2", mpiData.getBL2());
		jsonObj.put("GB1", mpiData.getGB1());
		jsonObj.put("GB2", mpiData.getGB2());
		jsonObj.put("ST1", mpiData.getST1());
		jsonObj.put("ST2", mpiData.getST2());

		execution.addAuResponse("chartresp", new AuInvoke(event.getTarget(), "redrawChart", jsonObj));
	}
}
