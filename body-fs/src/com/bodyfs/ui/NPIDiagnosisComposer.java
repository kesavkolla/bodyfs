/* $Id: DiagnosisComposer.java 338 2010-04-09 06:12:50Z kesavkolla $ */
package com.bodyfs.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;

import com.bodyfs.Constants;
import com.bodyfs.dao.IPatientVisitDAO;
import com.bodyfs.model.PersonType;
import com.bodyfs.model.npi.NPIPatientDiagnosis;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class NPIDiagnosisComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 6365381701330204030L;
	private transient static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	static {
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
	}
	private static Log LOGGER = LogFactory.getLog(NPIDiagnosisComposer.class);

	@Override
	public ComponentInfo doBeforeCompose(final Page page, final Component parent, final ComponentInfo compInfo) {
		final Session session = Sessions.getCurrent(false);
		final Execution execution = Executions.getCurrent();
		// Do the sanity checks
		// If the id is not present in the URL send to customer search
		final Long patid = CommonUtils.getPatientId();
		if (patid == null) {
			if (((PersonType) session.getAttribute(Constants.SESSION_PERSON_TYPE)) == PersonType.EMPLOYEE) {
				execution.sendRedirect("/pages/usermgmt/customersearch.zul");
				return null;
			} else {
				execution.sendRedirect("/pages/user/index.zul");
				return null;
			}
		}
		// If there are no patient visits send to sign-in page
		page.setAttribute("patid", patid);

		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		NPIPatientDiagnosis diagnosis = visitDAO.getPatientNPIDiagnosisByDate((Long) page.getAttribute("patid"));
		if (diagnosis == null) {
			diagnosis = new NPIPatientDiagnosis();
			diagnosis.setPersonId((Long) page.getAttribute("patid"));
			diagnosis.setVisitDate(new Date());
		}
		page.setAttribute("diagnosis", diagnosis);
		final Textbox txtjsondata = (Textbox) Path.getComponent(this.page, "jsondata");
		if (diagnosis.getDiagnosisData() != null) {
			txtjsondata.setText(diagnosis.getDiagnosisData());
		}
	}

	/**
	 * Save the data to the corresponding PatientDiagnsotics object
	 * 
	 * @param event
	 */
	public void onSave(final ForwardEvent event) {
		final Textbox txtjsondata = (Textbox) Path.getComponent(this.page, "jsondata");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(txtjsondata.getValue());
		}
		final NPIPatientDiagnosis diagnosis = (NPIPatientDiagnosis) page.getAttribute("diagnosis");
		diagnosis.setDiagnosisData(txtjsondata.getValue());
		final IPatientVisitDAO visitDAO = (IPatientVisitDAO) SpringUtil.getBean("patientVisitDAO");
		visitDAO.createNPIPatientDiagnosis(diagnosis);
		Clients.evalJavaScript("navigate('" + event.getData() + "')");
	}
}
