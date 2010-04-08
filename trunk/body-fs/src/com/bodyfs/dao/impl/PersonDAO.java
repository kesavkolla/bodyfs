/*
 * $Id$
 */
package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.controllers.PersonController;
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
import com.bodyfs.model.PersonType;
import com.bodyfs.model.QuickPatient;
import com.bodyfs.model.Respiratory;
import com.bodyfs.model.SkinHair;
import com.bodyfs.ui.QuickPatientListComposer;

@Repository(value = "personDAO")
public class PersonDAO implements IPersonDAO, Serializable {

	private static final long serialVersionUID = 8672328220267294005L;
	private static final Log LOGGER = LogFactory.getLog(PersonController.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	private Cache cache;

	public void setCache(final Cache cache) {
		this.cache = cache;
	}

	@Override
	public Person createPerson(final Person person) {
		return this.jdoTemplate.makePersistent(person);
	}

	@Override
	public void deleteAll() {
		this.jdoTemplate.execute(new JdoCallback<Long>() {
			@Override
			public Long doInJdo(PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Person.class);
				return query.deletePersistentAll();
			}

		});
	}

	@Override
	public void deletePerson(Long id) {
		this.jdoTemplate.deletePersistent(this.jdoTemplate.getObjectById(Person.class, id));
	}

	@Override
	public Person getPerson(final Long id) {
		return this.jdoTemplate.getObjectById(Person.class, id);
	}

	@Override
	public Person getPerson(final Long id, final boolean cache) {
		if (!cache) {
			return getPerson(id);
		}
		final Person cperson = (Person) this.cache.get(CACHED_PERSON);
		if (cperson == null || !cperson.getId().equals(id)) {
			final Person nperson = getPerson(id);
			this.cache.put(CACHED_PERSON, nperson);
			return nperson;
		}
		return cperson;
	}

	@Override
	public Collection<Person> getAll() {
		return this.jdoTemplate.detachCopyAll(this.jdoTemplate.find(Person.class));
	}

	@Override
	public Collection<Person> getAllCustomers() {
		return this.jdoTemplate.detachCopyAll(this.jdoTemplate.find(Person.class, "personType != '"
				+ PersonType.EMPLOYEE + "'"));
	}

	@Override
	public Collection<Person> getAllCustomersInfo() {
		try {
			final Collection<Person> results = this.jdoTemplate.find(Person.class, "personType == "
					+ PersonType.EMPLOYEE);
			if (results.size() <= 0) {
				return null;
			}
			return results;
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createGeneralInfo(final GeneralInfo ginfo) {
		if (ginfo.isDirty()) {
			this.jdoTemplate.makePersistent(ginfo);
		}
	}

	@Override
	public void createFamilyMedicalHistory(final FamilyMedHistory fmh) {
		if (fmh.isDirty()) {
			this.jdoTemplate.makePersistent(fmh);
		}
	}

	@Override
	public FamilyMedHistory getFamilyMedicalHistory(final Long personId) {
		try {
			final Collection<FamilyMedHistory> results = this.jdoTemplate.find(FamilyMedHistory.class, "personId =="
					+ personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createPastMedicalHistory(final PastMedicalHistory pmh) {
		if (pmh.isDirty()) {
			this.jdoTemplate.makePersistent(pmh);
		}
	}

	@Override
	public PastMedicalHistory getPastMedicalHistory(final Long personId) {
		try {
			final Collection<PastMedicalHistory> results = this.jdoTemplate.find(PastMedicalHistory.class,
					"personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createPatientDiet(final Diet yd) {
		if (yd.isDirty()) {
			this.jdoTemplate.makePersistent(yd);
		}
	}

	@Override
	public Diet getDiet(final Long personId) {
		try {
			final Collection<Diet> results = this.jdoTemplate.find(Diet.class, "personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createLifeStyle(final Lifestyle yls) {
		if (yls.isDirty()) {
			this.jdoTemplate.makePersistent(yls);
		}
	}

	@Override
	public Lifestyle getLifestyle(final Long personId) {
		try {
			final Collection<Lifestyle> results = this.jdoTemplate.find(Lifestyle.class, "personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createGeneralSymptoms(final GeneralSymptoms gs) {
		if (gs.isDirty()) {
			this.jdoTemplate.makePersistent(gs);
		}
	}

	@Override
	public GeneralSymptoms getGeneralSymptoms(final Long personId) {
		try {
			final Collection<GeneralSymptoms> results = this.jdoTemplate.find(GeneralSymptoms.class, "personId =="
					+ personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createENT(final ENT ent) {
		if (ent.isDirty()) {
			this.jdoTemplate.makePersistent(ent);
		}
	}

	@Override
	public ENT getENT(final Long personId) {
		try {
			final Collection<ENT> results = this.jdoTemplate.find(ENT.class, "personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createRespiratory(final Respiratory rp) {
		if (rp.isDirty()) {
			this.jdoTemplate.makePersistent(rp);
		}
	}

	@Override
	public Respiratory getRespiratory(final Long personId) {
		try {
			final Collection<Respiratory> results = this.jdoTemplate.find(Respiratory.class, "personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createCardiovascular(final Cardiovascular cv) {
		if (cv.isDirty()) {
			this.jdoTemplate.makePersistent(cv);
		}
	}

	@Override
	public Cardiovascular getCardiovascular(final Long personId) {
		try {
			final Collection<Cardiovascular> results = this.jdoTemplate.find(Cardiovascular.class, "personId =="
					+ personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createHealthInsurance(final HealthInsurance hi) {
		if (hi.isDirty()) {
			this.jdoTemplate.makePersistent(hi);
		}
	}

	@Override
	public HealthInsurance getHealthInsurance(final Long personId) {
		try {
			final Collection<HealthInsurance> results = this.jdoTemplate.find(HealthInsurance.class, "personId =="
					+ personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createGastrointestinal(final Gastrointestinal gi) {
		if (gi.isDirty()) {
			this.jdoTemplate.makePersistent(gi);
		}
	}

	@Override
	public Gastrointestinal getGastrointestinal(final Long personId) {
		try {
			final Collection<Gastrointestinal> results = this.jdoTemplate.find(Gastrointestinal.class, "personId =="
					+ personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createMusculoskeletal(final Musculoskeletal ms) {
		if (ms.isDirty()) {
			this.jdoTemplate.makePersistent(ms);
		}
	}

	@Override
	public Musculoskeletal getMusculoskeletal(final Long personId) {
		try {
			final Collection<Musculoskeletal> results = this.jdoTemplate.find(Musculoskeletal.class, "personId =="
					+ personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createSkinHair(final SkinHair sh) {
		if (sh.isDirty()) {
			this.jdoTemplate.makePersistent(sh);
		}
	}

	@Override
	public SkinHair getSkinHair(final Long personId) {
		try {
			final Collection<SkinHair> results = this.jdoTemplate.find(SkinHair.class, "personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createNeuropsychological(final Neuropsychological np) {
		if (np.isDirty()) {
			this.jdoTemplate.makePersistent(np);
		}
	}

	@Override
	public Neuropsychological getNeuropsychological(final Long personId) {
		try {
			final Collection<Neuropsychological> results = this.jdoTemplate.find(Neuropsychological.class,
					"personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createGenitourinary(final Genitourinary gen) {
		if (gen.isDirty()) {
			this.jdoTemplate.makePersistent(gen);
		}
	}

	@Override
	public Genitourinary getGenitourinary(final Long personId) {
		try {
			final Collection<Genitourinary> results = this.jdoTemplate.find(Genitourinary.class, "personId =="
					+ personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public Gynecology getGynecology(final Long personId) {
		try {
			final Collection<Gynecology> results = this.jdoTemplate.find(Gynecology.class, "personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void createGynecology(final Gynecology gy) {
		if (gy.isDirty()) {
			this.jdoTemplate.makePersistent(gy);
		}
	}

	@Override
	public GeneralInfo getGeneralInfo(final Long personId) {
		try {
			final Collection<GeneralInfo> results = this.jdoTemplate.find(GeneralInfo.class, "personId ==" + personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public Person getByEmail(final String email) {
		try {
			final Collection<Person> results = this.jdoTemplate.find(Person.class, "email==pemail", "String pemail",
					email);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Person getByName(String firstname, String lastname, String initial) {
		final String filter = "lastName==plastName && firstName==pfirstName"
				+ ((initial == null) ? "" : " && initial==pinitial");
		final String params = "String plastName, String pfirstName" + ((initial == null) ? "" : ", String pinitial");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("filter: " + filter + "\tparams:" + params);
		}
		final Object[] vals = new Object[initial == null ? 2 : 3];
		vals[0] = lastname;
		vals[1] = firstname;

		if (initial != null) {
			vals[2] = initial;
		}
		try {
			final Collection<Person> persons = this.jdoTemplate.find(Person.class, filter, params, vals,
					"id descending");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(persons);
			}
			if (persons != null && persons.size() > 0) {
				return persons.iterator().next();
			}
		} catch (final Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public Collection<QuickPatient> getQuickList() {
		return jdoTemplate.detachCopyAll(jdoTemplate.find(QuickPatient.class, null, "date DESC"));
	}

	@Override
	public void deleteQuickPatient(final Long id) {
		try {
			final QuickPatient qp = jdoTemplate.getObjectById(QuickPatient.class, id);
			if (qp != null) {
				jdoTemplate.deletePersistent(qp);
				cache.remove(QuickPatientListComposer.QUICK_PATIENT_LIST);
			}
		} catch (final Exception e) {
		}
	}

	@Override
	public void createQuickPatient(final QuickPatient qp) {
		this.jdoTemplate.makePersistent(qp);
	}

}
