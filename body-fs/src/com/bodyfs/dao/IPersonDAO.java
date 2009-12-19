package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.Cardiovascular;
import com.bodyfs.model.Diet;
import com.bodyfs.model.ENT;
import com.bodyfs.model.FamilyMedHistory;
import com.bodyfs.model.Gastrointestinal;
import com.bodyfs.model.GeneralInfo;
import com.bodyfs.model.GeneralSymptoms;
import com.bodyfs.model.Genitourinary;
import com.bodyfs.model.Gynecology;
import com.bodyfs.model.Lifestyle;
import com.bodyfs.model.Musculoskeletal;
import com.bodyfs.model.Neuropsychological;
import com.bodyfs.model.PastMedicalHistory;
import com.bodyfs.model.PatientVisit;
import com.bodyfs.model.Person;
import com.bodyfs.model.Respiratory;
import com.bodyfs.model.SkinHair;

public interface IPersonDAO {
	public Person getPerson(Long id);

	public Collection<Person> getAll();

	public Person createPerson(final Person person);

	public Person getByEmail(final String email);

	public void deletePerson(final Long id);

	public void deleteAll();

	public void createGeneralInfo(final GeneralInfo ginfo);
	
	public GeneralInfo getGeneralInfo(final Long personId);

	public PatientVisit createPatientVisit(final PatientVisit visit);

	public Collection<PatientVisit> GetPatientVisits(final Long personId);

	public int countPatientVisits(final Long patid);
	
	public void createFamilyMedicalHistory(final FamilyMedHistory fmh);
	
	public void createPastMedicalHistory(final PastMedicalHistory pmh);
	
	public void createPatientDiet(final Diet yd);
	
	public void createLifeStyle(final Lifestyle yls);
	
	public void createGeneralSymptoms(final GeneralSymptoms gs);
	
	public void createENT(final ENT ent);
	
	public void createRespiratory(final Respiratory rp);	
	
	public void createCardiovascular(final Cardiovascular cv);
	
	public void createGastrointestinal(final Gastrointestinal gi);
	
	public void createMusculoskeletal(final Musculoskeletal ms);
	
	public void createSkinHair(final SkinHair sh);
	
	public void createNeuropsychological(final Neuropsychological np);
	
	public void createGenitourinary(final Genitourinary gen);
	
	public void createGynecology(final Gynecology gy);
}
