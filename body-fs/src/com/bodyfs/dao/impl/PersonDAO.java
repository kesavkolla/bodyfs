/*
 * $Id$
 */
package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
import com.bodyfs.model.Lifestyle;
import com.bodyfs.model.Musculoskeletal;
import com.bodyfs.model.Neuropsychological;
import com.bodyfs.model.PastMedicalHistory;
import com.bodyfs.model.Person;
import com.bodyfs.model.QuickPatient;
import com.bodyfs.model.Respiratory;
import com.bodyfs.model.SkinHair;

@Repository(value = "personDAO")
public class PersonDAO implements IPersonDAO, Serializable {

	private static final long serialVersionUID = 8672328220267294005L;
	private static final Log LOGGER = LogFactory.getLog(PersonController.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

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
	public Collection<Person> getAll() {
		return this.jdoTemplate.detachCopyAll(this.jdoTemplate.find(Person.class));
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
	public void createPastMedicalHistory(final PastMedicalHistory pmh) {
		if (pmh.isDirty()) {
			this.jdoTemplate.makePersistent(pmh);
		}
	}

	@Override
	public void createPatientDiet(final Diet yd) {
		if (yd.isDirty()) {
			this.jdoTemplate.makePersistent(yd);
		}
	}

	@Override
	public void createLifeStyle(final Lifestyle yls) {
		if (yls.isDirty()) {
			this.jdoTemplate.makePersistent(yls);
		}
	}

	@Override
	public void createGeneralSymptoms(final GeneralSymptoms gs) {
		if (gs.isDirty()) {
			this.jdoTemplate.makePersistent(gs);
		}
	}

	@Override
	public void createENT(final ENT ent) {
		if (ent.isDirty()) {
			this.jdoTemplate.makePersistent(ent);
		}
	}

	@Override
	public void createRespiratory(final Respiratory rp) {
		if (rp.isDirty()) {
			this.jdoTemplate.makePersistent(rp);
		}
	}

	@Override
	public void createCardiovascular(final Cardiovascular cv) {
		if (cv.isDirty()) {
			this.jdoTemplate.makePersistent(cv);
		}
	}

	@Override
	public void createGastrointestinal(final Gastrointestinal gi) {
		if (gi.isDirty()) {
			this.jdoTemplate.makePersistent(gi);
		}
	}

	@Override
	public void createMusculoskeletal(final Musculoskeletal ms) {
		if (ms.isDirty()) {
			this.jdoTemplate.makePersistent(ms);
		}
	}

	@Override
	public void createSkinHair(final SkinHair sh) {
		if (sh.isDirty()) {
			this.jdoTemplate.makePersistent(sh);
		}
	}

	@Override
	public void createNeuropsychological(final Neuropsychological np) {
		if (np.isDirty()) {
			this.jdoTemplate.makePersistent(np);
		}
	}

	@Override
	public void createGenitourinary(final Genitourinary gen) {
		if (gen.isDirty()) {
			this.jdoTemplate.makePersistent(gen);
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
	public List<QuickPatient> getQuickList() {
		return jdoTemplate.execute(new JdoCallback<List<QuickPatient>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<QuickPatient> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(QuickPatient.class);
				query.setOrdering("date DESC");
				query.setRange(0L, 5L);
				final List<QuickPatient> qlist = (List<QuickPatient>) query.execute();
				final List<QuickPatient> copylist = new ArrayList<QuickPatient>(qlist);
				return copylist;
			}
		}, true);
	}

	@Override
	public void createQuickPatient(final QuickPatient qp) {
		this.jdoTemplate.makePersistent(qp);
	}

}
