/*
 * $Id: NPIComposer.java 107 2009-12-19 17:48:18Z amitagrawal84 $
 */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.Cardiovascular;
import com.bodyfs.model.Diet;
import com.bodyfs.model.ENT;
import com.bodyfs.model.FamilyMedHistory;
import com.bodyfs.model.Gastrointestinal;
import com.bodyfs.model.GeneralInfo;
import com.bodyfs.model.GeneralSymptoms;
import com.bodyfs.model.Genitourinary;
import com.bodyfs.model.Gynecology;
import com.bodyfs.model.HealthInsurance;
import com.bodyfs.model.Lifestyle;
import com.bodyfs.model.Musculoskeletal;
import com.bodyfs.model.Neuropsychological;
import com.bodyfs.model.PastMedicalHistory;
import com.bodyfs.model.Person;
import com.bodyfs.model.Respiratory;
import com.bodyfs.model.SkinHair;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
public class NPIViewComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -4039933079355260867L;
	private static Log LOGGER = LogFactory.getLog(NPIViewComposer.class);

	public static final String SESSION_PERSON = "session.person";
	public static final String SESSION_GENERALINFO = "session.ginfo";

	public static final String SESSION_FAMILYMEDICALHISTORY = "session.fmh";
	public static final String SESSION_PASTMEDICALHISTORY = "session.pmh";
	public static final String SESSION_PATIENTDIET = "session.yd";
	public static final String SESSION_LIFESTYLE = "session.yls";
	public static final String SESSION_GENERALSYMPTOMS = "session.gs";
	public static final String SESSION_ENT = "session.ent";
	public static final String SESSION_RESPIRATORY = "session.rp";
	public static final String SESSION_CARDIO = "session.cv";
	public static final String SESSION_GASTRO = "session.gi";
	public static final String SESSION_MUSCULO = "session.ms";
	public static final String SESSION_SKINHAIR = "session.sh";
	public static final String SESSION_NEURO = "session.np";
	public static final String SESSION_GENITOURINARY = "session.gen";
	public static final String SESSION_GYNAE = "session.gy";
	public static final String SESSION_HEALTHINSURANCE = "session.hi";

	private Include npiinclude;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.setupPerson();
	}

	public void onBookmarkChange(final BookmarkEvent event) {
		final String pageid = event.getBookmark();
		if (CommonUtils.getIsAdminUser()) {
			if (pageid != null && !pageid.equals("")) {
				npiinclude.setSrc("/pages/usermgmt/" + pageid + ".zul");
			} else {
				npiinclude.setSrc("/pages/usermgmt/npiview.zul");
			}
		} else {
			if (pageid != null && !pageid.equals("")) {
				npiinclude.setSrc("/pages/user/" + pageid + ".zul");
			} else {
				npiinclude.setSrc("/pages/user/npiview.zul");
			}
		}
	}

	private void setupPerson() {
		LOGGER.debug("Setting up the person");
		Long patid = null;

		try {
			patid = CommonUtils.getPatientId();
		} catch (NumberFormatException ex) {
			patid = null;
		}
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");

		Person person = null;
		GeneralInfo ginfo = null;
		FamilyMedHistory fmh = null;
		PastMedicalHistory pmh = null;
		Diet yd = null;
		Lifestyle yls = null;
		GeneralSymptoms gs = null;
		Respiratory rp = null;
		ENT ent = null;
		Cardiovascular cv = null;
		Gastrointestinal gi = null;
		Musculoskeletal ms = null;
		SkinHair sh = null;
		Neuropsychological np = null;
		Genitourinary gen = null;
		Gynecology gy = null;
		HealthInsurance hi = null;
		person = personDAO.getPerson(patid);
		ginfo = personDAO.getGeneralInfo(patid);
		fmh = personDAO.getFamilyMedicalHistory(patid);
		pmh = personDAO.getPastMedicalHistory(patid);
		yd = personDAO.getDiet(patid);
		yls = personDAO.getLifestyle(patid);
		gs = personDAO.getGeneralSymptoms(patid);
		ent = personDAO.getENT(patid);
		rp = personDAO.getRespiratory(patid);
		cv = personDAO.getCardiovascular(patid);
		gi = personDAO.getGastrointestinal(patid);
		ms = personDAO.getMusculoskeletal(patid);
		sh = personDAO.getSkinHair(patid);
		np = personDAO.getNeuropsychological(patid);
		gen = personDAO.getGenitourinary(patid);
		gy = personDAO.getGynecology(patid);
		hi = personDAO.getHealthInsurance(patid);
		page.setAttribute("person", person);
		page.setAttribute("ginfo", ginfo);
		page.setAttribute("fmh", fmh);
		page.setAttribute("pmh", pmh);
		page.setAttribute("yd", yd);
		page.setAttribute("yls", yls);
		page.setAttribute("gs", gs);
		page.setAttribute("ent", ent);
		page.setAttribute("rp", rp);
		page.setAttribute("cv", cv);
		page.setAttribute("gi", gi);
		page.setAttribute("ms", ms);
		page.setAttribute("sh", sh);
		page.setAttribute("np", np);
		page.setAttribute("gen", gen);
		page.setAttribute("gy", gy);
		page.setAttribute("hi", hi);

	}
}
