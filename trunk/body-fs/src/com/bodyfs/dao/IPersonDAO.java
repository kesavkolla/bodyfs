/* $Id */
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
import com.bodyfs.model.HealthInsurance;
import com.bodyfs.model.Lifestyle;
import com.bodyfs.model.Musculoskeletal;
import com.bodyfs.model.Neuropsychological;
import com.bodyfs.model.PastMedicalHistory;
import com.bodyfs.model.Person;
import com.bodyfs.model.QuickPatient;
import com.bodyfs.model.Respiratory;
import com.bodyfs.model.SkinHair;

/**
 * 
 * @author Kesav Kumar Kolla
 * 
 */
public interface IPersonDAO {

	public static final String CACHED_PERSON = IPersonDAO.class.getName() + ".person";

	/**
	 * 
	 * @param id
	 * @return Person object corresponds to the id
	 */
	public Person getPerson(Long id);

	/**
	 * Retrieves the person from cache if exists otherwise adds to the cache
	 * 
	 * @param id
	 * @param cache
	 * @return
	 */
	public Person getPerson(final Long id, final boolean cache);

	/**
	 * All person objects
	 * 
	 * @return
	 */
	public Collection<Person> getAll();

	public Collection<Person> getAllCustomersInfo();

	public Collection<Person> getAllCustomers();

	public Person createPerson(final Person person);

	/**
	 * This method searches all the patients by their email address
	 * 
	 * @param email person's email address
	 * @return person corresponds to the email null if not found
	 */
	public Person getByEmail(final String email);

	/**
	 * This method searchs all the patients by their name
	 * 
	 * @param firstname persons' firstname
	 * @param lastname person's lastname
	 * @param initial person's middle name
	 * @return person with the match null if not found
	 */
	public Person getByName(final String firstname, final String lastname, final String initial);

	/**
	 * 
	 * @return list of all patients in queue
	 */
	public Collection<QuickPatient> getQuickList();

	/**
	 * Creates a patient in the work queue
	 * 
	 * @param qp
	 */
	public void createQuickPatient(final QuickPatient qp);

	/**
	 * Deletes the patient from the queue
	 * 
	 * @param id
	 */
	public void deleteQuickPatient(final Long id);

	public void deletePerson(final Long id);

	public void deleteAll();

	public void createGeneralInfo(final GeneralInfo ginfo);

	public GeneralInfo getGeneralInfo(final Long personId);

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

	public Genitourinary getGenitourinary(final Long personId);

	public Gynecology getGynecology(final Long personId);

	public Neuropsychological getNeuropsychological(final Long personId);

	public void createGynecology(final Gynecology gy);

	public SkinHair getSkinHair(Long personId);

	public Musculoskeletal getMusculoskeletal(Long personId);

	public Gastrointestinal getGastrointestinal(Long personId);

	public Cardiovascular getCardiovascular(Long personId);

	public Respiratory getRespiratory(Long personId);

	public FamilyMedHistory getFamilyMedicalHistory(Long personId);

	public PastMedicalHistory getPastMedicalHistory(Long personId);

	public Diet getDiet(Long personId);

	public Lifestyle getLifestyle(Long personId);

	public GeneralSymptoms getGeneralSymptoms(Long personId);

	public ENT getENT(Long personId);

	public void createHealthInsurance(final HealthInsurance hi);

	public HealthInsurance getHealthInsurance(final Long personId);

}
