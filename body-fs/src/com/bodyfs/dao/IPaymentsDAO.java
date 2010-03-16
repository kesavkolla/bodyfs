/* $Id: IHerbDAO.java 169 2010-02-19 06:22:14Z kesavkolla $ */
package com.bodyfs.dao;

import java.util.Collection;
import java.util.List;

import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;
import com.bodyfs.model.payments.CustomerPayments;
import com.bodyfs.model.payments.MasterServices;

/**
 * 
 * @author amit
 * 
 */
public interface IPaymentsDAO {

	
	public void createPayment(final CustomerPayments payment);
	
	
	/*
	 * Master Service
	 */
	
	
	public void addMasterService(final MasterServices service);
	
	public Collection<MasterServices> getMasterServicesList();
	
	public MasterServices getMasterServicebyId(String ServiceId);
	
	
	/*
	 * Payments. 
	 */
	
	
	
	//Payment breakDowns
	
	
	
	
	
	/*
	 * Total Services details for customer.  
	 */
	
	
}
