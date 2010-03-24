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

	public static final String DIAGNOSES_CACHE = IHerbDAO.class.getName() + ".Diagnoses";
	public static final String FORMULAS_CACHE = IHerbDAO.class.getName() + ".Formulas";
	public static final String HERBS_CACHE = IHerbDAO.class.getName() + ".Herbs";

	/**
	 * Persists the her
	 * 
	 * @param herb
	 */
	public void createHerb(final Herb herb);

	/**
	 * Checkes whether the given herb name is already exists or not
	 * 
	 * @param name
	 * @return true if exists false otherwise
	 */
	public boolean checkHerbName(final String name);

	/**
	 * 
	 * @param name
	 *            herb name
	 * @return id of the herb corresponding to the given name
	 */
	public long getHerbIdByName(final String name);

	/**
	 * Deletes the herb by given id
	 * 
	 * @param id
	 */
	public void deleteHerbById(final Long id);

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
	 * 
	 * @return retrieves all the herbs from the data store
	 */
	public List<Herb> getHerbs();

	/**
	 * 
	 * @param herbIds
	 * @return retrieves all the herbs corresponds to the given herbids
	 */
	public Collection<Herb> getHerbs(final List<Long> herbIds);

	/**
	 * 
	 * @param herbIds
	 * @return List of herbids for the given list of ids
	 */
	public Collection<Long> getHerbIds(final List<Long> herbIds);

	/**
	 * 
	 * @return number of herbs in the database
	 */
	public int countHerbs();

	/**
	 * 
	 * @return Retrieves all the formulas as a list
	 */
	public List<HerbFormula> getFormulas();

	/**
	 * 
	 * @param formulaids
	 * @return formula objects for the given list of ids
	 */
	public Collection<HerbFormula> getFormulas(final List<Long> formulaids);

	/**
	 * 
	 * @param formulaid
	 * @return retrieves the herbformula object corresponds to the given id
	 */
	public HerbFormula getFormulaById(final Long formulaid);

	/**
	 * 
	 * @return List of herbformulas for the given list of ids
	 */
	public Collection<Long> getFormulasIds(final List<Long> formulaids);

	/**
	 * Persists the given formula object
	 * 
	 * @param diagnosis
	 */
	public void createFormula(final HerbFormula formula);

	/**
	 * Checkes whether the given formula name is already exists or not
	 * 
	 * @param name
	 * @return true if exists false otherwise
	 */
	public boolean checkFormulaName(final String name);

	/**
	 * 
	 * @param name
	 *            formula name
	 * @return id of the HerbFormula that corresponds to the given name
	 */
	public long getFormulaIdByName(final String name);

	/**
	 * Deletes the formula by given id
	 * 
	 * @param id
	 */
	public void deleteFormulaById(final Long id);

	/**
	 * 
	 * @return number of diagnoses in the database
	 */
	public int countFormulas();

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
