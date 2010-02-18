/* $Id$ */
package com.bodyfs.dao.impl;

import java.io.Serializable;
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
import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;

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

	@Override
	public void createHerb(final Herb herb) {

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
	}

	@Override
	public Herb getHerbById(final Long id) {
		return this.jdoTemplate.getObjectById(Herb.class, id);
	}

	@Override
	public HerbFormula addFormula(final HerbFormula forumula) {
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
	public void deleteFormulaById(final Long id) {
		final HerbFormula formula = jdoTemplate.getObjectById(HerbFormula.class, id);
		if (formula != null) {
			jdoTemplate.deletePersistent(formula);
		}
	}

	@Override
	public void createDiagnosis(final Diagnosis diagnosis) {
		this.jdoTemplate.makePersistent(diagnosis);
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

	@Override
	public Collection<Diagnosis> getDiagnoses() {
		// Get all the diagnoses and loop through each one to make sure all the
		// formulas are correct. It might happen that after creating the
		// diagnosis the herbformula might have deleted. We need to do an
		// integrity check before returning the Diagnosis objects
		final Collection<Diagnosis> diagnoses = this.jdoTemplate.find(Diagnosis.class);
		for (final Diagnosis diagnosis : diagnoses) {
			final List<Long> formulaids = diagnosis.getFormulas();
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
			this.createDiagnosis(diagnosis);
		}
		return diagnoses;
	}

	@Override
	public void deleteDiagnosisById(Long id) {
		final Diagnosis diagnosis = this.jdoTemplate.getObjectById(Diagnosis.class, id);
		if (diagnosis != null) {
			this.jdoTemplate.deletePersistent(diagnosis);
		}
	}
}
