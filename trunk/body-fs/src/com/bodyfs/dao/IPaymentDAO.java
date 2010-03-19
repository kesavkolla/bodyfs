/* $Id$ */
package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.payments.CustomerPayments;
import com.bodyfs.model.payments.MasterService;

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

	/*
	 * Payments.
	 */

	// Payment breakDowns

	/*
	 * Total Services details for customer.
	 */

}
