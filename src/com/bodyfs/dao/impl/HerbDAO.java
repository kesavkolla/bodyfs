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

import net.sf.jsr107cache.Cache;

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

	public void setCounterbDAO(final ICounterDAO counterbDAO) {
		this.counterbDAO = counterbDAO;
	}

	private Cache cache;

	public void setCache(final Cache cache) {
		this.cache = cache;
	}

	@Override
	public void createHerb(final Herb herb) {
		if (herb.getId() == null) {
			this.counterbDAO.increment(ICounterDAO.HERB_COUNT);
		}
		final Herb herb1 = this.jdoTemplate.makePersistent(herb);
		LOGGER.debug("Created herb with id: " + herb1);
		this.cache.remove(IHerbDAO.HERBS_CACHE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkHerbName(final String name) {
		final Map<String, Object> vals = new HashMap<String, Object>();
		vals.put("pname", name.toLowerCase());
		Collection<Long> results = (Collection<Long>) jdoTemplate.find("SELECT id FROM " + Herb.class.getName()
				+ " WHERE lowername==pname PARAMETERS String pname ORDER BY id DESC", vals);
		if (results == null || results.size() <= 0) {
			return false;
		}
		return results.size() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getHerbIdByName(String name) {
		final Map<String, Object> vals = new HashMap<String, Object>();
		vals.put("pname", name.toLowerCase());
		Collection<Long> results = (Collection<Long>) jdoTemplate.find("SELECT id FROM " + Herb.class.getName()
				+ " WHERE lowername==pname PARAMETERS String pname ORDER BY id DESC", vals);
		if (results == null || results.size() <= 0) {
			return -1L;
		}
		return results.iterator().next();
	}

	@Override
	public void deleteHerbById(final Long id) {
		Herb herb = null;
		try {
			herb = jdoTemplate.getObjectById(Herb.class, id);
		} catch (final Exception e) {
			return;
		}

		if (herb != null) {
			jdoTemplate.deletePersistent(herb);
			this.counterbDAO.decrement(ICounterDAO.HERB_COUNT);
			this.cache.remove(IHerbDAO.HERBS_CACHE);
		}
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
		this.cache.remove(IHerbDAO.HERBS_CACHE);
	}

	@Override
	public Herb getHerbById(final Long id) {
		return this.jdoTemplate.getObjectById(Herb.class, id);
	}

	@Override
	public Collection<Long> getHerbIds(final List<Long> herbIds) {
		return this.jdoTemplate.execute(new JdoCallback<Collection<Long>>() {
			@SuppressWarnings("unchecked")
			public Collection<Long> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery("select id from " + Herb.class.getName() + " where :p.contains(id)");
				return (Collection<Long>) query.execute(herbIds);
			};
		}, true);
	}

	@Override
	public Collection<Herb> getHerbs(final List<Long> herbIds) {
		return this.jdoTemplate.execute(new JdoCallback<Collection<Herb>>() {
			@SuppressWarnings("unchecked")
			public Collection<Herb> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery("select from " + Herb.class.getName() + " where :p.contains(id)");
				return (Collection<Herb>) query.execute(herbIds);
			};
		}, true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Herb> getHerbs() {
		// Get the cached data if cachedata exists and it's size matches with
		// the count then return the cache instance of the data
		final List<Herb> cacheData = (List<Herb>) this.cache.get(IHerbDAO.HERBS_CACHE);
		int count = this.countHerbs();
		if (cacheData != null && count == cacheData.size()) {
			return cacheData;
		}

		// If there are no diagnosis then return empty list
		if (count == 0) {
			final List<Herb> retVal = Collections.EMPTY_LIST;
			this.cache.put(IHerbDAO.HERBS_CACHE, retVal);
			return retVal;
		}
		// Get all the formulas
		final List<Herb> retVal = new ArrayList<Herb>(count);
		this.jdoTemplate.execute(new JdoCallback<List<Herb>>() {
			@Override
			public List<Herb> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Herb.class);
				query.setRange(0, 1000);
				query.setOrdering("lowername");
				String cursorStr = null;
				List<Herb> results = null;
				loop: for (;;) {
					if (cursorStr == null) {
						results = (List<Herb>) query.execute();
						final Cursor cursor = JDOCursorHelper.getCursor(results);
						cursorStr = cursor.toWebSafeString();
					} else {
						final Cursor cursor = Cursor.fromWebSafeString(cursorStr);
						final Map<String, Object> extensionMap = new HashMap<String, Object>();
						extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
						query.setExtensions(extensionMap);
						results = (List<Herb>) query.execute();
						final Cursor cursor1 = JDOCursorHelper.getCursor(results);
						cursorStr = cursor1.toWebSafeString();
					}
					if (results != null && results.size() > 0) {
						retVal.addAll(jdoTemplate.detachCopyAll(results));
					} else {
						break loop;
					}
				}
				return null;
			}
		});
		// Store the diagnoses in the cache
		this.cache.put(IHerbDAO.HERBS_CACHE, retVal);
		return retVal;
	}

	@Override
	public int countHerbs() {
		return counterbDAO.getCount(ICounterDAO.HERB_COUNT);
	}

	@Override
	public void createFormula(final HerbFormula forumula) {
		// It's a new one so update the counter
		if (forumula.getId() == null) {
			this.counterbDAO.increment(ICounterDAO.HERBFORUMLA_COUNTER);
		}
		this.jdoTemplate.makePersistent(forumula);
		this.cache.remove(IHerbDAO.FORMULAS_CACHE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkFormulaName(final String name) {
		final Map<String, Object> vals = new HashMap<String, Object>();
		vals.put("pname", name.toLowerCase());
		Collection<Long> results = (Collection<Long>) jdoTemplate.find("SELECT id FROM " + HerbFormula.class.getName()
				+ " WHERE lowername==pname PARAMETERS String pname ORDER BY id DESC", vals);
		if (results == null || results.size() <= 0) {
			return false;
		}
		return results.size() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getFormulaIdByName(String name) {
		final Map<String, Object> vals = new HashMap<String, Object>();
		vals.put("pname", name.toLowerCase());
		Collection<Long> results = (Collection<Long>) jdoTemplate.find("SELECT id FROM " + HerbFormula.class.getName()
				+ " WHERE lowername==pname PARAMETERS String pname ORDER BY id DESC", vals);
		if (results == null || results.size() <= 0) {
			return -1;
		}
		return results.iterator().next();
	}

	@Override
	public int countFormulas() {
		return counterbDAO.getCount(ICounterDAO.HERBFORUMLA_COUNTER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HerbFormula> getFormulas() {
		// Get the cached data if cachedata exists and it's size matches with
		// the count then return the cache instance of the data
		final List<HerbFormula> cacheData = (List<HerbFormula>) this.cache.get(IHerbDAO.FORMULAS_CACHE);
		int count = this.countFormulas();
		if (cacheData != null && count == cacheData.size()) {
			return cacheData;
		}

		// If there are no diagnosis then return empty list
		if (count == 0) {
			final List<HerbFormula> retVal = Collections.EMPTY_LIST;
			this.cache.put(IHerbDAO.FORMULAS_CACHE, retVal);
			return retVal;
		}
		// Get all the formulas and loop through each one to make sure all the
		// herbs are correct. It might happen that after creating the
		// formula the herbs might have deleted. We need to do an
		// integrity check before returning the formulas objects
		final List<HerbFormula> retVal = new ArrayList<HerbFormula>(count);
		this.jdoTemplate.execute(new JdoCallback<Collection<HerbFormula>>() {
			@Override
			public Collection<HerbFormula> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(HerbFormula.class);
				query.setOrdering("lowername");
				query.setRange(0, 1000);
				String cursorStr = null;
				List<HerbFormula> results = null;
				loop: for (;;) {
					if (cursorStr == null) {
						results = (List<HerbFormula>) query.execute();
						final Cursor cursor = JDOCursorHelper.getCursor(results);
						cursorStr = cursor.toWebSafeString();
					} else {
						final Cursor cursor = Cursor.fromWebSafeString(cursorStr);
						final Map<String, Object> extensionMap = new HashMap<String, Object>();
						extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
						query.setExtensions(extensionMap);
						results = (List<HerbFormula>) query.execute();
						final Cursor cursor1 = JDOCursorHelper.getCursor(results);
						cursorStr = cursor1.toWebSafeString();
					}
					if (results != null && results.size() > 0) {
						retVal.addAll(jdoTemplate.detachCopyAll(results));
					} else {
						break loop;
					}
				}
				return null;
			}
		});

		for (final HerbFormula formula : retVal) {
			final List<Long> herbids = (List<Long>) getHerbIds(formula.getHerbs());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Formula Herb ids: " + formula.getHerbs());
				LOGGER.debug("Returned from database: " + herbids);
			}
			if (herbids.equals(formula.getHerbs())) {
				// No modification required
				continue;
			}
			// Save the new Diagnosis in the database

			formula.setHerbs(new ArrayList<Long>(herbids));
			createFormula(formula);
		}
		// Store the diagnoses in the cache
		this.cache.put(IHerbDAO.FORMULAS_CACHE, retVal);
		return retVal;
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
	public HerbFormula getFormulaById(final Long formulaid) {
		return this.jdoTemplate.getObjectById(HerbFormula.class, formulaid);
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
		HerbFormula formula = null;
		try {
			formula = jdoTemplate.getObjectById(HerbFormula.class, id);
		} catch (final Exception e) {
			return;
		}
		if (formula != null) {
			jdoTemplate.deletePersistent(formula);
			this.counterbDAO.decrement(ICounterDAO.HERBFORUMLA_COUNTER);
			this.cache.remove(IHerbDAO.FORMULAS_CACHE);
		}
	}

	@Override
	public void createDiagnosis(final Diagnosis diagnosis) {
		if (diagnosis.getId() == null) {
			this.counterbDAO.increment(ICounterDAO.DIAGNOSIS_COUNTER);
		}
		this.jdoTemplate.makePersistent(diagnosis);
		this.cache.remove(IHerbDAO.DIAGNOSES_CACHE);
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
		// Get the cached data if cachedata exists and it's size matches with
		// the count then return the cache instance of the data
		final List<Diagnosis> cacheData = (List<Diagnosis>) this.cache.get(IHerbDAO.DIAGNOSES_CACHE);
		int count = this.countDiagnosis();
		if (cacheData != null && count == cacheData.size()) {
			return cacheData;
		}

		// If there are no diagnosis then return empty list
		if (count == 0) {
			final List<Diagnosis> retVal = Collections.EMPTY_LIST;
			this.cache.put(IHerbDAO.DIAGNOSES_CACHE, retVal);
			return retVal;
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
				query.setOrdering("lowername");
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
						retVal.addAll(jdoTemplate.detachCopyAll(results));
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
			diagnosis.setFormulas(new ArrayList<Long>(formulaids));
			createDiagnosis(diagnosis);
		}
		// Store the diagnoses in the cache
		this.cache.put(IHerbDAO.DIAGNOSES_CACHE, retVal);
		return retVal;
	}

	@Override
	public void deleteDiagnosisById(final Long id) {
		Diagnosis diagnosis = null;
		try {
			diagnosis = this.jdoTemplate.getObjectById(Diagnosis.class, id);
		} catch (final Exception e) {
			return;
		}
		if (diagnosis != null) {
			this.jdoTemplate.deletePersistent(diagnosis);
			this.counterbDAO.decrement(ICounterDAO.DIAGNOSIS_COUNTER);
			this.cache.remove(IHerbDAO.DIAGNOSES_CACHE);
		}
	}

	@Override
	public int countDiagnosis() {
		return this.counterbDAO.getCount(ICounterDAO.DIAGNOSIS_COUNTER);
	}
}
