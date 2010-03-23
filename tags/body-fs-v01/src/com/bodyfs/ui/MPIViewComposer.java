/* $Id$ */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.api.Window;

import com.bodyfs.dao.IMPIDao;
import com.bodyfs.model.MPIData;
import com.bodyfs.ui.util.CommonUtils;

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

	/**
	 * This method converts the given MPI object to JSON object
	 * 
	 * @param mpiData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject convertMPI(final MPIData mpiData) {
		final JSONObject jsonObj = new JSONObject();
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
		jsonObj.put("high", mpiData.getHigh());
		jsonObj.put("low", mpiData.getLow());
		jsonObj.put("average", mpiData.getAverage());
		return jsonObj;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		final Long id = CommonUtils.getPatientId();
		if (id != null) {
			final MPIData mpi = mpiDao.getDataByDate(id, null);
			if (mpi != null) {
				comp.getPage().setAttribute("mpi", mpi);
				comp.getPage().setAttribute("mpidata", convertMPI(mpi));
			} else {
				comp.getPage().setAttribute("mpidata", "{}");
			}
			comp.getPage().setAttribute("patid", id);
		}
		super.doAfterCompose(comp);
	}

	/**
	 * 
	 * @return Returns the array of mpi dates
	 */
	public Date[] getExamDates() {
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		final Long patid = CommonUtils.getPatientId();
		final Collection<Date> dates = mpiDao.getExamDates(patid);
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
		this.execution.sendRedirect("/pages/patient/mpi.zul?id=" + mpi.getPersonId());
	}

	/**
	 * This method returns the mpi data that corresponds to the given patient id
	 * and given date
	 * 
	 * @param patid
	 * @param date
	 * @return
	 */
	public String getMPIData(final long patid, final Date date) {
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		final MPIData mpiData = mpiDao.getDataByDate(patid, date);
		if (mpiData == null) {
			return "";
		}
		return convertMPI(mpiData).toJSONString();
	}

	/**
	 * 
	 * @param comp
	 */
	public void drawChart(Component comp) {
		final Date date = (Date) comp.getAttribute("date");
		final long patid = Long.parseLong(comp.getAttribute("patid").toString());
		final String data = this.getMPIData(patid, date);
		if (data.length() > 0) {
			Clients.evalJavaScript("DrawChart(" + data + ")");
		}
	}

	@SuppressWarnings("unchecked")
	public void compareCharts(final org.zkoss.zul.api.Toolbar dateBar) {
		final List<Date> dates = new ArrayList<Date>();
		// Get all the child components of the toolbar
		for (final Component comp : (List<Component>) dateBar.getChildren()) {
			// skip if it's not the checkbox
			if (!(comp instanceof Checkbox)) {
				continue;
			}
			// skip if the checkbox is not selected
			final Checkbox chkbox = (Checkbox) comp;
			if (!chkbox.isChecked()) {
				continue;
			}
			// checkbox name has the long time convert it to date
			dates.add(new Date(Long.parseLong(chkbox.getName())));
		}
		if (dates.size() < 2) {
			alert("Select at least 2 dates to do chart comparison");
			return;
		}
		if (dates.size() > 3) {
			alert("Can not compare more than 3 charts");
		}
		// Get the patietn id from the toolbar's custom attribute
		final Long patid = Long.parseLong(dateBar.getAttribute("patid").toString());
		final JSONArray arr = new JSONArray();
		// Get the MPIData for all the selected dates
		final IMPIDao mpiDao = (IMPIDao) SpringUtil.getBean("MPIDao");
		for (final Date date : dates) {
			final MPIData mpiData = mpiDao.getDataByDate(patid, date);
			if (mpiData != null) {
				final JSONObject obj = convertMPI(mpiData);
				obj.put("examDateF", sdf.format(mpiData.getExamDate()));
				arr.add(obj);
			}
		}
		if (arr.size() <= 0) {
			alert("Error occurred in retrieving MPI data");
			return;
		}
		Clients.evalJavaScript("DrawCompare(" + arr.toJSONString() + ")");
	}
}
