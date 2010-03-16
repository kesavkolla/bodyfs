/* $Id: paymentDAO.java 169 2010-02-19 06:22:14Z kesavkolla $ */
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.datanucleus.store.appengine.query.JDOCursorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.ICounterDAO;
import com.bodyfs.dao.IPaymentsDAO;
import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.Person;
import com.bodyfs.model.payments.CustomerPayments;
import com.bodyfs.model.payments.MasterServices;
import com.google.appengine.api.datastore.Cursor;

/**
 * @author kesav
 */
@Repository(value = "paymentDAO")
public class PaymentsDAO implements IPaymentsDAO, Serializable {

	private static final long serialVersionUID = -2181026768481723335L;
	private static final Log LOGGER = LogFactory.getLog(PaymentsDAO.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Autowired
	private ICounterDAO counterbDAO;

	@Override
	public void createPayment(final CustomerPayments payment) {
		final CustomerPayments payment1 = this.jdoTemplate.makePersistent(payment);
		LOGGER.debug("Created herb with id: " + payment1);
	}
	
	public void addMasterService(final MasterServices service){
		final MasterServices payment1 = this.jdoTemplate.makePersistent(service);
		LOGGER.debug("Created herb with id: " + payment1);
	}
	
	public Collection<MasterServices> getMasterServicesList(){
		return this.jdoTemplate.detachCopyAll(this.jdoTemplate.find(MasterServices.class));
	}
	
	public MasterServices getMasterServicebyId(String ServiceId){
		try {
			final MasterServices service = this.jdoTemplate.getObjectById(MasterServices.class, ServiceId);
			return service;
		} catch (final Exception e) {
			return null;
		}
	}
	
}
