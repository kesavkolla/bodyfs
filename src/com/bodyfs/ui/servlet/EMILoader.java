/* $Id$ */
package com.bodyfs.ui.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;

import com.bodyfs.dao.IMPIDao;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.MPIData;
import com.bodyfs.model.Person;

/**
 * This servelet loads the posted EMI data into database
 * 
 * @author Kesav Kumar Kolla
 * 
 */
@SuppressWarnings("serial")
public class EMILoader extends HttpServlet {
	WebApplicationContext ctx = null;
	final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

	@Override
	public void init() throws ServletException {
		super.init();
		ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
			IOException {
		// Do the usual validations
		final String data = req.getParameter("emidata");
		if (data == null || data.length() < 10) {
			throw new ServletException("emidata is required");
		}
		final String patdata = req.getParameter("patdata");
		if (patdata == null || patdata.length() < 10) {
			throw new ServletException("patdata is require");
		}
		// create the JSONParser this will be used to parse the input JSON data
		final JSONParser parser = new JSONParser();
		try {
			// Get the patient if patient is not found throw error
			final Person patient = getPatient(parser, patdata);
			if (patient == null) {
				throw new Exception("Can not find the person with the provided details");
			}
			// Save the mpidata
			saveEMI(parser, patient, data);
			resp.getWriter().println("Successfully saved MPI data");
		} catch (final Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * This method retrieves the patient from the supplied patient data
	 * 
	 * @param parser
	 * @param patdata
	 * @return
	 * @throws Exception
	 */
	private Person getPatient(final JSONParser parser, final String patdata) throws Exception {
		final JSONObject person = (JSONObject) parser.parse(patdata);
		if (person == null) {
			throw new Exception("Can not parse the patient data: " + patdata);
		}
		final IPersonDAO personDAO = ctx.getBean(IPersonDAO.class);
		return personDAO.getByName((String) person.get("FirstName"), (String) person.get("LastName"), (String) person
				.get("MiddleName"));
	}

	/**
	 * This method loops through all the EMI data and persists the data
	 * 
	 * @param parser
	 * @param patient
	 * @param data
	 * @throws Exception
	 */
	private void saveEMI(final JSONParser parser, final Person patient, final String data) throws Exception {
		final IMPIDao mpiDAO = ctx.getBean(IMPIDao.class);
		final JSONObject dataObj = (JSONObject) parser.parse(data);
		if (dataObj == null) {
			throw new Exception("Can not parse the EMI data");
		}
		final JSONArray rows = (JSONArray) dataObj.get("rows");
		for (int i = 0, len = rows.size(); i < len; i++) {
			final JSONObject row = (JSONObject) rows.get(i);
			final Date examDate = sdf.parse(row.get("ExamDate") + " " + row.get("ExamTime"));
			// Check whether this examdate is already uploaded or not
			if (mpiDAO.getDataByDate(patient.getId(), examDate) != null) {
				// Skip this data
				continue;
			}

			// create new object and populate all the data
			final MPIData emiobj = new MPIData();
			emiobj.setPersonId(patient.getId());

			if (row.get("LU1") != null) {
				emiobj.setLU1(((Double) row.get("LU1")).floatValue());
			}
			if (row.get("LU2") != null) {
				emiobj.setLU2(((Double) row.get("LU2")).floatValue());
			}
			if (row.get("LU3") != null) {
				emiobj.setLU3(((Double) row.get("LU3")).floatValue());
			}
			if (row.get("P1") != null) {
				emiobj.setP1(((Double) row.get("P1")).floatValue());
			}
			if (row.get("P2") != null) {
				emiobj.setP2(((Double) row.get("P2")).floatValue());
			}
			if (row.get("P3") != null) {
				emiobj.setP3(((Double) row.get("P3")).floatValue());
			}
			if (row.get("HT1") != null) {
				emiobj.setHT1(((Double) row.get("HT1")).floatValue());
			}
			if (row.get("HT2") != null) {
				emiobj.setHT2(((Double) row.get("HT2")).floatValue());
			}
			if (row.get("HT3") != null) {
				emiobj.setHT3(((Double) row.get("HT3")).floatValue());
			}
			if (row.get("SI1") != null) {
				emiobj.setSI1(((Double) row.get("SI1")).floatValue());
			}
			if (row.get("SI2") != null) {
				emiobj.setSI2(((Double) row.get("SI2")).floatValue());
			}
			if (row.get("SI3") != null) {
				emiobj.setSI3(((Double) row.get("SI3")).floatValue());
			}
			if (row.get("TH1") != null) {
				emiobj.setTH1(((Double) row.get("TH1")).floatValue());
			}
			if (row.get("TH2") != null) {
				emiobj.setTH2(((Double) row.get("TH2")).floatValue());
			}
			if (row.get("TH3") != null) {
				emiobj.setTH3(((Double) row.get("TH3")).floatValue());
			}
			if (row.get("LI1") != null) {
				emiobj.setLI1(((Double) row.get("LI1")).floatValue());
			}
			if (row.get("LI2") != null) {
				emiobj.setLI2(((Double) row.get("LI2")).floatValue());
			}
			if (row.get("LI3") != null) {
				emiobj.setLI3(((Double) row.get("LI3")).floatValue());
			}
			if (row.get("SP1") != null) {
				emiobj.setSP1(((Double) row.get("SP1")).floatValue());
			}
			if (row.get("SP2") != null) {
				emiobj.setSP2(((Double) row.get("SP2")).floatValue());
			}
			if (row.get("SP3") != null) {
				emiobj.setSP3(((Double) row.get("SP3")).floatValue());
			}
			if (row.get("LV1") != null) {
				emiobj.setLV1(((Double) row.get("LV1")).floatValue());
			}
			if (row.get("LV2") != null) {
				emiobj.setLV2(((Double) row.get("LV2")).floatValue());
			}
			if (row.get("LV3") != null) {
				emiobj.setLV3(((Double) row.get("LV3")).floatValue());
			}
			if (row.get("KI1") != null) {
				emiobj.setKI1(((Double) row.get("KI1")).floatValue());
			}
			if (row.get("KI2") != null) {
				emiobj.setKI2(((Double) row.get("KI2")).floatValue());
			}
			if (row.get("KI3") != null) {
				emiobj.setKI3(((Double) row.get("KI3")).floatValue());
			}
			if (row.get("BL1") != null) {
				emiobj.setBL1(((Double) row.get("BL1")).floatValue());
			}
			if (row.get("BL2") != null) {
				emiobj.setBL2(((Double) row.get("BL2")).floatValue());
			}
			if (row.get("BL3") != null) {
				emiobj.setBL3(((Double) row.get("BL3")).floatValue());
			}
			if (row.get("GB1") != null) {
				emiobj.setGB1(((Double) row.get("GB1")).floatValue());
			}
			if (row.get("GB2") != null) {
				emiobj.setGB2(((Double) row.get("GB2")).floatValue());
			}
			if (row.get("GB3") != null) {
				emiobj.setGB3(((Double) row.get("GB3")).floatValue());
			}
			if (row.get("ST1") != null) {
				emiobj.setST1(((Double) row.get("ST1")).floatValue());
			}
			if (row.get("ST2") != null) {
				emiobj.setST2(((Double) row.get("ST2")).floatValue());
			}
			if (row.get("ST3") != null) {
				emiobj.setST3(((Double) row.get("ST3")).floatValue());
			}
			if (row.get("high") != null) {
				emiobj.setHigh(((Double) row.get("high")).floatValue());
			}
			if (row.get("low") != null) {
				emiobj.setLow(((Double) row.get("low")).floatValue());
			}
			if (row.get("average") != null) {
				emiobj.setAverage(((Double) row.get("average")).floatValue());
			}
			if (row.get("notes") != null) {
				emiobj.setNotes((String) row.get("notes"));
			}
			if (row.get("ExamDate") != null) {
				emiobj.setExamDate(sdf.parse(row.get("ExamDate") + " " + row.get("ExamTime")));
			}

			// persist the data
			mpiDAO.addMPIData(emiobj);
		}
	}
}
