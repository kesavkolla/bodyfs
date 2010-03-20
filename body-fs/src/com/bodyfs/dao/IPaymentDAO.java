/* $Id$ */
package com.bodyfs.dao;

import java.util.Collection;
import java.util.Date;

import com.bodyfs.model.payments.CustomerPayments;
import com.bodyfs.model.payments.MasterService;
import com.bodyfs.model.payments.PatientPaymentPlan;

/**
 * 
 * @author amit
 * 
 */
public interface IPaymentDAO {

	public void createPayment(final CustomerPayments payment);

	/**
	 * Creates a new service
	 * 
	 * @param service
	 */
	public void addMasterService(final MasterService service);

	/**
	 * Retrieves visible services
	 * 
	 * @return
	 */
	public Collection<MasterService> getMasterServicesList();

	/**
	 * Retrieves Service by it's id
	 * 
	 * @param ServiceId
	 * @return
	 */
	public MasterService getMasterServicebyId(String ServiceId);

	/**
	 * Checks wheter the given service name is already exists or not
	 * 
	 * @param serviceName
	 * @return
	 */
	public boolean checkService(final String serviceName);

	/**
	 * Deletes the given service
	 * 
	 * @param service
	 */
	public void deleteService(final MasterService service);

	/**
	 * Retrieves all the list of services including hidden
	 * 
	 * @return
	 */
	public Collection<MasterService> getAllServices();

	/**
	 * Retrieves the service by the given name
	 * 
	 * @param name
	 * @return
	 */
	public MasterService getServiceByName(final String name);

	/**
	 * Retrieves all the plans that have been made for a given patient
	 * 
	 * @param patientId
	 * @return
	 */
	public Collection<PatientPaymentPlan> getAllPlans(final Long patientId);

	/**
	 * Retrieves the plan for the patient for the given date
	 * 
	 * @param patientId
	 * @param paymentDate
	 * @return
	 */
	public PatientPaymentPlan getPlanByDate(final Long patientId, final Date paymentDate);

	/**
	 * Creates a new plan and persists in the database
	 * 
	 * @param plan
	 */
	public void createPaymentPlan(final PatientPaymentPlan plan);
}
