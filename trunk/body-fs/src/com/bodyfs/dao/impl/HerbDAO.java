/* $Id$ */
package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.datanucleus.store.appengine.query.JDOCursorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.ICounterDAO;
import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;
import com.google.appengine.api.datastore.Cursor;

/**
 * 
 * @author kesav
 * 
 */
@Repository(value = "herbDAO")
public class HerbDAO implements IHerbDAO, Serializable {

	private static final long serialVersionUID = -2181026768481723335L;
	private static final Log LOGGER = LogFactory.getLog(HerbDAO.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Autowired
	private ICounterDAO counterbDAO;

	@Override
	public void createHerb(final Herb herb) {
		if (herb.getId() == null) {
			this.counterbDAO.increment(ICounterDAO.HERB_COUNT);
		}
		final Herb herb1 = this.jdoTemplate.makePersistent(herb);
		LOGGER.debug("Created herb with id: " + herb1);
	}

	@Override
	public void deleteAllHerbs() {
		this.jdoTemplate.execute(new JdoCallback<Long>() {
			@Override
			public Long doInJdo(PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Herb.class);
				return query.deletePersistentAll();
			}
		});
		this.counterbDAO.resetCount(ICounterDAO.HERB_COUNT, 0);
	}

	@Override
	public Herb getHerbById(final Long id) {
		return this.jdoTemplate.getObjectById(Herb.class, id);
	}

	@Override
	public HerbFormula addFormula(final HerbFormula forumula) {
		// It's a new one so update the counter
		if (forumula.getId() == null) {
			this.counterbDAO.increment(ICounterDAO.HERBFORUMLA_COUNTER);
		}
		return this.jdoTemplate.makePersistent(forumula);
	}

	@Override
	public Collection<HerbFormula> getFormulas() {
		return this.jdoTemplate.find(HerbFormula.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Long> getFormulasIds(final List<Long> formulaids) {
		return this.jdoTemplate.execute(new JdoCallback<Collection<Long>>() {
			public Collection<Long> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery("select id from " + HerbFormula.class.getName()
						+ " where :p.contains(id)");
				return (Collection<Long>) query.execute(formulaids);
			};
		}, true);
	}

	@Override
	public Collection<HerbFormula> getFormulas(final List<Long> formulaids) {
		return this.jdoTemplate.execute(new JdoCallback<Collection<HerbFormula>>() {
			@SuppressWarnings("unchecked")
			public Collection<HerbFormula> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm
						.newQuery("select from " + HerbFormula.class.getName() + " where :p.contains(id)");
				return (Collection<HerbFormula>) query.execute(formulaids);
			};
		}, true);
	}

	@Override
	public void deleteFormulaById(final Long id) {
		final HerbFormula formula = jdoTemplate.getObjectById(HerbFormula.class, id);
		if (formula != null) {
			jdoTemplate.deletePersistent(formula);
			this.counterbDAO.decrement(ICounterDAO.HERBFORUMLA_COUNTER);
		}
	}

	@Override
	public void createDiagnosis(final Diagnosis diagnosis) {
		if (diagnosis.getId() == null) {
			this.jdoTemplate.makePersistent(diagnosis);
		}
		this.counterbDAO.increment(ICounterDAO.DIAGNOSIS_COUNTER);
	}

	@Override
	public boolean checkDiagnosisName(final String name) {
		final Collection<Diagnosis> results = this.jdoTemplate.find(Diagnosis.class, "lowername==pname",
				"String pname", name.toLowerCase());
		if (results == null || results.size() <= 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Diagnosis> getDiagnoses() {
		int count = this.countDiagnosis();
		if (count == 0) {
			return Collections.EMPTY_LIST;
		}
		// Get all the diagnoses and loop through each one to make sure all the
		// formulas are correct. It might happen that after creating the
		// diagnosis the herbformula might have deleted. We need to do an
		// integrity check before returning the Diagnosis objects
		final List<Diagnosis> retVal = new ArrayList<Diagnosis>(count);
		this.jdoTemplate.execute(new JdoCallback<Collection<Diagnosis>>() {
			@Override
			public Collection<Diagnosis> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Diagnosis.class);
				query.setRange(0, 1000);
				String cursorStr = null;
				List<Diagnosis> results = null;
				loop: for (;;) {
					if (cursorStr == null) {
						results = (List<Diagnosis>) query.execute();
						final Cursor cursor = JDOCursorHelper.getCursor(results);
						cursorStr = cursor.toWebSafeString();
					} else {
						final Cursor cursor = Cursor.fromWebSafeString(cursorStr);
						final Map<String, Object> extensionMap = new HashMap<String, Object>();
						extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
						query.setExtensions(extensionMap);
						results = (List<Diagnosis>) query.execute();
						final Cursor cursor1 = JDOCursorHelper.getCursor(results);
						cursorStr = cursor1.toWebSafeString();
					}
					if (results != null && results.size() > 0) {
						retVal.addAll(results);
					} else {
						break loop;
					}
				}
				return null;
			}
		});
		for (final Diagnosis diagnosis : retVal) {
			final List<Long> formulaids = (List<Long>) getFormulasIds(diagnosis.getFormulas());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Diagnosis Formula ids: " + diagnosis.getFormulas());
				LOGGER.debug("Returned from database: " + formulaids);
			}
			if (formulaids.equals(diagnosis.getFormulas())) {
				// No modification required
				continue;
			}
			// Save the new Diagnosis in the database
			diagnosis.setFormulas(formulaids);
			createDiagnosis(diagnosis);
		}

		return retVal;
	}

	@Override
	public void deleteDiagnosisById(final Long id) {
		final Diagnosis diagnosis = this.jdoTemplate.getObjectById(Diagnosis.class, id);
		if (diagnosis != null) {
			this.jdoTemplate.deletePersistent(diagnosis);
			this.counterbDAO.decrement(ICounterDAO.DIAGNOSIS_COUNTER);
		}
	}

	@Override
	public int countDiagnosis() {
		return this.counterbDAO.getCount(ICounterDAO.DIAGNOSIS_COUNTER);
	}
}
