/* $Id$ */
package com.bodyfs.dao;

import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;

/**
 * This DAO is responsible for maintaining all table counts
 * 
 * @author Kesav Kumar Kolla
 * 
 */
public interface ICounterDAO {
	public static final String HERBFORUMLA_COUNTER = HerbFormula.class.getName();
	public static final String DIAGNOSIS_COUNTER = Diagnosis.class.getName();
	public static final String HERB_COUNT = Herb.class.getName();

	/**
	 * Creates a new table counter with the given name and with the initial
	 * value
	 * 
	 * @param name
	 * @param initVal
	 */
	public void createCount(final String name, int initVal);

	/**
	 * 
	 * @param name
	 *            counter name
	 * @return counter value
	 */
	public int getCount(final String name);

	/**
	 * increments the value of the counter for the given name
	 * 
	 * @param name
	 */
	public int increment(final String name);

	/**
	 * decrements the value of the counter for the given name
	 * 
	 * @param name
	 */
	public int decrement(final String name);

	/**
	 * Resets the counter to the given value
	 * 
	 * @param count
	 */
	public void resetCount(final String name, final int count);

}
