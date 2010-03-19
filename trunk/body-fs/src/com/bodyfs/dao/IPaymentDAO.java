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

	/*
	 * Master Service
	 */

	public void addMasterService(final MasterService service);

	public Collection<MasterService> getMasterServicesList();

	public MasterService getMasterServicebyId(String ServiceId);

	public boolean checkService(final String serviceName);

	public void deleteService(final MasterService service);

	public Collection<MasterService> getAllServices();

	/*
	 * Payments.
	 */

	// Payment breakDowns

	/*
	 * Total Services details for customer.
	 */

}
