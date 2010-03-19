/* $Id: paymentDAO.java 169 2010-02-19 06:22:14Z kesavkolla $ */
package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.model.payments.CustomerPayments;
import com.bodyfs.model.payments.MasterService;

/**
 * @author kesav
 */
@Repository(value = "paymentDAO")
public class PaymentDAO implements IPaymentDAO, Serializable {

	private static final long serialVersionUID = -2181026768481723335L;
	private static final Log LOGGER = LogFactory.getLog(PaymentDAO.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Override
	public void createPayment(final CustomerPayments payment) {
		final CustomerPayments payment1 = this.jdoTemplate.makePersistent(payment);
		LOGGER.debug("Created payment with id: " + payment1);
	}

	public void addMasterService(final MasterService service) {
		final MasterService payment1 = this.jdoTemplate.makePersistent(service);
		LOGGER.debug("Created herb with id: " + payment1);
	}

	public Collection<MasterService> getMasterServicesList() {
		return this.jdoTemplate.detachCopyAll(this.jdoTemplate.find(MasterService.class, "hidden==false"));
	}

	@Override
	public Collection<MasterService> getAllServices() {
		return this.jdoTemplate.detachCopyAll(this.jdoTemplate.find(MasterService.class));
	}

	public MasterService getMasterServicebyId(final String ServiceId) {
		try {
			final MasterService service = this.jdoTemplate.getObjectById(MasterService.class, ServiceId);
			return service;
		} catch (final Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkService(String serviceName) {
		final Map<String, Object> vals = new HashMap<String, Object>();
		vals.put("pname", serviceName.toLowerCase());
		Collection<Long> results = (Collection<Long>) jdoTemplate.find("SELECT id FROM "
				+ MasterService.class.getName() + " WHERE lowername==pname PARAMETERS String pname ORDER BY id DESC",
				vals);
		if (results == null || results.size() <= 0) {
			return false;
		}
		return results.size() > 0;
	}

	@Override
	public void deleteService(MasterService service) {
		this.jdoTemplate.deletePersistent(service);
	}

}
