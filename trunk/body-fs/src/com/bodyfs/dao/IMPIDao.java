/* $Id$ */
package com.bodyfs.dao;

import java.util.Collection;
import java.util.Date;

import com.bodyfs.model.MPIData;

/**
 * 
 * @author kesav
 * 
 */
public interface IMPIDao {
	/**
	 * This method returns the MPIData for the given user id and examdate
	 * 
	 * @param id
	 *            user id
	 * @param examDate
	 *            Date when the exam has taken.
	 * @return MPIData corresponds to the parameters. If date is null it returns
	 *         the latest data
	 */
	public MPIData getDataByDate(final Long id, final Date examDate);

	/**
	 * Saves new MPIData object
	 * 
	 * @param data
	 */
	public void addMPIData(final MPIData data);

	/**
	 * 
	 * @param id
	 *            user id
	 * @return all the examdates for the given user id
	 */
	public Collection<Date> getExamDates(final Long id);

	/**
	 * This method returns the first MPIData of the patient
	 * 
	 * @param patid
	 * @return
	 */
	public MPIData getFirstMPI(final Long patid);
}
