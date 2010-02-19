/* $Id$ */
package com.bodyfs.dao;

import java.util.Collection;
import java.util.List;

import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;

/**
 * 
 * @author kesav
 * 
 */
public interface IHerbDAO {

	/**
	 * Persists the her
	 * 
	 * @param herb
	 */
	public void createHerb(final Herb herb);

	/**
	 * Delete all the herbs
	 */
	public void deleteAllHerbs();

	/**
	 * 
	 * @param id
	 * @return Herb object corresponds the id
	 */
	public Herb getHerbById(final Long id);

	/**
	 * Persists the given formula
	 * 
	 * @param forumula
	 * @return
	 */
	public HerbFormula addFormula(final HerbFormula forumula);

	/**
	 * 
	 * @return Retrieves all the formulas as a list
	 */
	public Collection<HerbFormula> getFormulas();

	/**
	 * 
	 * @param formulaids
	 * @return formula objects for the given list of ids
	 */
	public Collection<HerbFormula> getFormulas(final List<Long> formulaids);

	/**
	 * 
	 * @return List of herbformulas for the given list of ids
	 */
	public Collection<Long> getFormulasIds(final List<Long> formulaids);

	/**
	 * Deletes the formula by given id
	 * 
	 * @param id
	 */
	public void deleteFormulaById(final Long id);

	/**
	 * Persists the given diagnosis object
	 * 
	 * @param diagnosis
	 */
	public void createDiagnosis(final Diagnosis diagnosis);

	/**
	 * Checkes whether the given diagnosis name is already exists or not
	 * 
	 * @param name
	 * @return true if exists false otherwise
	 */
	public boolean checkDiagnosisName(final String name);

	/**
	 * 
	 * @return Retrieves all the diagnoses
	 */
	public List<Diagnosis> getDiagnoses();

	/**
	 * Deletes the given diagnosis by the
	 * 
	 * @param id
	 */
	public void deleteDiagnosisById(final Long id);

	/**
	 * 
	 * @return number of diagnoses in the database
	 */
	public int countDiagnosis();
}
