/*
 * $Id: NPIComposer.java 107 2009-12-19 17:48:18Z amitagrawal84 $
 */
package com.bodyfs.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.dao.impl.PersonDAO;
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
import com.bodyfs.model.PatientDiagnosis;
import com.bodyfs.model.Person;
import com.bodyfs.model.PersonType;
import com.bodyfs.model.Respiratory;
import com.bodyfs.model.SkinHair;
import com.bodyfs.ui.util.CommonUtils;

/**
 * 
 * @author kesav
 * 
 */
@SuppressWarnings("unchecked")
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

	public void onNext(final ForwardEvent event) {
		final String pageId = event.getData().toString();
		desktop.setBookmark(pageId);
		if(CommonUtils.getIsAdminUser())
			npiinclude.setSrc("/pages/usermgmt/" + pageId + ".zul");
		else 
			npiinclude.setSrc("/pages/user/" + pageId + ".zul");
	}

	public void onBookmarkChange(final BookmarkEvent event) {
		final String pageid = event.getBookmark();
		if(CommonUtils.getIsAdminUser()) {
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

	public void onSaveNPI(final ForwardEvent event) {
		final Person person = (Person) sessionScope.get(SESSION_PERSON);
		cleanSession();
		if(CommonUtils.getIsAdminUser()) {
			execution.sendRedirect("/pages/patient/patientview.zul?id="+person.getId());
		} else {
			execution.sendRedirect("index.zul");
		}
		
	}

	public void cleanSession() {
		sessionScope.remove(SESSION_GENERALINFO);
		sessionScope.remove(SESSION_PERSON);
		sessionScope.remove(SESSION_FAMILYMEDICALHISTORY);
		sessionScope.remove(SESSION_PASTMEDICALHISTORY);
		sessionScope.remove(SESSION_PATIENTDIET);
		sessionScope.remove(SESSION_LIFESTYLE);
		sessionScope.remove(SESSION_GENERALSYMPTOMS);
		sessionScope.remove(SESSION_ENT);
		sessionScope.remove(SESSION_RESPIRATORY);
		sessionScope.remove(SESSION_CARDIO);
		sessionScope.remove(SESSION_GASTRO);
		sessionScope.remove(SESSION_MUSCULO);
		sessionScope.remove(SESSION_SKINHAIR);
		sessionScope.remove(SESSION_NEURO);
		sessionScope.remove(SESSION_GENITOURINARY);
		sessionScope.remove(SESSION_GYNAE);
		sessionScope.remove(SESSION_HEALTHINSURANCE);
		
	}

	public void onCancel(final ForwardEvent event) {
		final Person person = (Person) sessionScope.get(SESSION_PERSON);
		cleanSession();
		if(CommonUtils.getIsAdminUser()) {
			execution.sendRedirect("/pages/patient/patientview.zul?id="+person.getId());
		} else {
			execution.sendRedirect("index.zul");
		}
	}

	private void setupPerson() {
		Long patid = null;
		
		try {
		patid = CommonUtils.getPatientId();
		} catch (NumberFormatException ex ){
			patid = null;
		}
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		
		
		Person person = null;
		GeneralInfo ginfo = null;
		FamilyMedHistory fmh = null;
		PastMedicalHistory pmh = null;
		Diet yd =null;
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
	
		if (sessionScope.get(SESSION_PERSON) == null) {
			if(patid ==  null) {
				
				person = new Person();
				ginfo = new GeneralInfo();
				fmh = new FamilyMedHistory();
				pmh = new PastMedicalHistory();
				yd = new Diet();
				yls = new Lifestyle();
				gs = new GeneralSymptoms();
				ent = new ENT();
				rp = new Respiratory();
				cv = new Cardiovascular();
				gi = new Gastrointestinal();
				ms = new Musculoskeletal();
				sh = new SkinHair();
				np = new Neuropsychological();
				gen = new Genitourinary();
				gy = new Gynecology();
				hi = new HealthInsurance();
			} else {
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
			}
			sessionScope.put(SESSION_PERSON, person);
			sessionScope.put(SESSION_GENERALINFO, ginfo);
			sessionScope.put(SESSION_FAMILYMEDICALHISTORY, fmh);
			sessionScope.put(SESSION_PASTMEDICALHISTORY,pmh);
			sessionScope.put(SESSION_PATIENTDIET,yd);
			sessionScope.put(SESSION_LIFESTYLE,yls);
			sessionScope.put(SESSION_GENERALSYMPTOMS,gs);
			sessionScope.put(SESSION_ENT,ent);
			sessionScope.put(SESSION_RESPIRATORY,rp);
			sessionScope.put(SESSION_CARDIO,cv);
			sessionScope.put(SESSION_GASTRO,gi);
			sessionScope.put(SESSION_MUSCULO, ms);
			sessionScope.put(SESSION_SKINHAIR,sh);
			sessionScope.put(SESSION_NEURO,np);
			sessionScope.put(SESSION_GENITOURINARY, gen);
			sessionScope.put(SESSION_GYNAE,gy);
			sessionScope.put(SESSION_HEALTHINSURANCE,hi);
			
		} else {
			person = (Person) sessionScope.get(SESSION_PERSON);
			ginfo = (GeneralInfo) sessionScope.get(SESSION_GENERALINFO);
			
			fmh = (FamilyMedHistory)sessionScope.get(SESSION_FAMILYMEDICALHISTORY);
			pmh = (PastMedicalHistory)sessionScope.get(SESSION_PASTMEDICALHISTORY);
			yd = (Diet)sessionScope.get(SESSION_PATIENTDIET);
			yls = (Lifestyle)sessionScope.get(SESSION_LIFESTYLE);
			gs = (GeneralSymptoms)sessionScope.get(SESSION_GENERALSYMPTOMS);
			ent = (ENT)sessionScope.get(SESSION_ENT);
			rp = (Respiratory)sessionScope.get(SESSION_RESPIRATORY);
			cv = (Cardiovascular)sessionScope.get(SESSION_CARDIO);
			gi = (Gastrointestinal)sessionScope.get(SESSION_GASTRO);
			ms = (Musculoskeletal)sessionScope.get(SESSION_MUSCULO);
			sh = (SkinHair)sessionScope.get(SESSION_SKINHAIR);
			np = (Neuropsychological)sessionScope.get(SESSION_NEURO);
			gen = (Genitourinary)sessionScope.get(SESSION_GENITOURINARY);
			gy = (Gynecology)sessionScope.get(SESSION_GYNAE);
			hi = (HealthInsurance) sessionScope.get(SESSION_HEALTHINSURANCE);
		}
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
