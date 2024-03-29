/* $Id: paymentDAO.java 169 2010-02-19 06:22:14Z kesavkolla $ */
package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.IPaymentDAO;
import com.bodyfs.model.payments.CustomerPayments;
import com.bodyfs.model.payments.MasterService;
import com.bodyfs.model.payments.PatientPaymentPlan;
import com.bodyfs.model.payments.PatientService;

/**
 * @author kesav
 */
@Repository(value = "paymentDAO")
public class PaymentDAO implements IPaymentDAO, Serializable {

	private static final long serialVersionUID = -2181026768481723335L;
	private static final Log LOGGER = LogFactory.getLog(PaymentDAO.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());
	private static final String ALL_SERVICE_CACHE = IPaymentDAO.class.getName() + ".AllMasterServices";
	private static final String MASTER_SERVICE_CACHE = IPaymentDAO.class.getName() + ".MasterServices";

	private Cache cache;

	public void setCache(final Cache cache) {
		this.cache = cache;
	}

	@Override
	public void createPayment(final CustomerPayments payment) {
		final CustomerPayments payment1 = this.jdoTemplate.makePersistent(payment);
		LOGGER.debug("Created payment with id: " + payment1);
	}

	public void addMasterService(final MasterService service) {
		final MasterService payment1 = this.jdoTemplate.makePersistent(service);
		if (cache.containsKey(ALL_SERVICE_CACHE)) {
			cache.remove(ALL_SERVICE_CACHE);
		}
		if (cache.containsKey(MASTER_SERVICE_CACHE)) {
			cache.remove(MASTER_SERVICE_CACHE);
		}
		LOGGER.debug("Created herb with id: " + payment1);
	}

	@SuppressWarnings("unchecked")
	public Collection<MasterService> getMasterServicesList() {
		if (cache.containsKey(MASTER_SERVICE_CACHE)) {
			return (Collection<MasterService>) cache.get(MASTER_SERVICE_CACHE);
		}
		final Collection<MasterService> results = this.jdoTemplate.detachCopyAll(this.jdoTemplate.find(
				MasterService.class, "hidden==false"));
		if (results != null) {
			cache.put(MASTER_SERVICE_CACHE, results);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<MasterService> getAllServices() {
		if (cache.containsKey(ALL_SERVICE_CACHE)) {
			return (Collection<MasterService>) cache.get(ALL_SERVICE_CACHE);
		}
		final Collection<MasterService> results = this.jdoTemplate.detachCopyAll(this.jdoTemplate
				.find(MasterService.class));
		if (results != null) {
			cache.put(ALL_SERVICE_CACHE, results);
		}
		return results;
	}

	public MasterService getMasterServicebyId(final String ServiceId) {
		try {
			final MasterService service = this.jdoTemplate.getObjectById(MasterService.class, ServiceId);
			return service;
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public MasterService getServiceByName(String name) {
		if (name == null || name.trim().length() <= 0) {
			return null;
		}
		final Collection<MasterService> services = jdoTemplate.find(MasterService.class, "lowername==pname",
				"String pname", name.toLowerCase());
		if (services == null || services.size() <= 0) {
			return null;
		}
		return services.iterator().next();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkService(String serviceName) {
		try {
			final Map<String, Object> vals = new HashMap<String, Object>();
			vals.put("pname", serviceName.toLowerCase());
			Collection<Long> results = (Collection<Long>) jdoTemplate.find("SELECT id FROM "
					+ MasterService.class.getName()
					+ " WHERE lowername==pname PARAMETERS String pname ORDER BY id DESC", vals);
			if (results == null || results.size() <= 0) {
				return false;
			}
			return results.size() > 0;
		} catch (final Exception e) {
			return false;
		}
	}

	@Override
	public void deleteService(MasterService service) {
		this.jdoTemplate.deletePersistent(service);
		if (cache.containsKey(ALL_SERVICE_CACHE)) {
			cache.remove(ALL_SERVICE_CACHE);
		}
		if (cache.containsKey(MASTER_SERVICE_CACHE)) {
			cache.remove(MASTER_SERVICE_CACHE);
		}
	}

	@Override
	public void createPaymentPlan(final PatientPaymentPlan plan) {
		jdoTemplate.makePersistent(plan);
	}

	@Override
	public Collection<PatientPaymentPlan> getAllPlans(final Long patientId) {
		return jdoTemplate.find(PatientPaymentPlan.class, "personId ==" + patientId + " && archive == false",
				"paymentDate desc");
	}

	@Override
	public PatientPaymentPlan getPlayById(Long plaind) {
		try {
			return jdoTemplate.getObjectById(PatientPaymentPlan.class, plaind);
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public PatientPaymentPlan getPlanByDate(final Long patientId, final Date paymentDate) {
		final String filter = "personId==pid" + ((paymentDate == null) ? "" : " && paymentDate==pdate");
		final String params = "java.lang.Long pid" + ((paymentDate == null) ? "" : ", java.util.Date pdate");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("filter: " + filter + "\tparams:" + params);
		}
		final Object[] vals = new Object[paymentDate == null ? 1 : 2];
		vals[0] = patientId;
		if (paymentDate != null) {
			vals[1] = paymentDate;
		}
		final Collection<PatientPaymentPlan> plans = this.jdoTemplate.find(PatientPaymentPlan.class, filter, params,
				vals, "paymentDate descending");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(plans);
		}
		if (plans != null && plans.size() > 0) {
			final PatientPaymentPlan plan = plans.iterator().next();
			// eagerly loading
			plan.getPlanItems();
			return plan;
		}
		return null;
	}

	@Override
	public void makeFinal(final Long planid) {
		// Get all non active plans and make them archive
		final Collection<PatientPaymentPlan> plans = jdoTemplate.find(PatientPaymentPlan.class,
				"archive==false  && active==false ");
		for (final PatientPaymentPlan plan : plans) {
			if (plan.getId().equals(planid)) {
				plan.setActive(true);
				plan.setArchive(false);
			} else {
				plan.setArchive(true);
			}
			jdoTemplate.makePersistent(plan);
		}
	}

	@Override
	public void archivePlan(Long planid) {
		final PatientPaymentPlan plan = jdoTemplate.getObjectById(PatientPaymentPlan.class, planid);
		plan.setArchive(true);
		jdoTemplate.makePersistent(plan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Date> getPaymentPlanDates(final Long patientId) {
		try {
			final Map<String, Object> vals = new HashMap<String, Object>();
			vals.put("pid", patientId);
			return jdoTemplate.find("SELECT paymentDate FROM " + PatientPaymentPlan.class.getName()
					+ " WHERE personId==pid && archive == false PARAMETERS Long pid  ORDER BY paymentDate DESC", vals);
		} catch (final Exception e) {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public void createVisitServices(final List<PatientService> services, final Collection<MasterService> servicelist) {
		if (services == null || services.size() <= 0) {
			return;
		}
		this.jdoTemplate.execute(new JdoCallback<Long>() {
			public Long doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(PatientService.class);
				query.setFilter("personId == pid && visitDate == pdate && sids.contains(serviceId)");
				query.declareParameters("Long pid, java.util.Date pdate, java.util.List sids");
				final List<Long> serviceid = new ArrayList<Long>(servicelist.size());
				for (final MasterService svc : servicelist) {
					serviceid.add(svc.getId());
				}
				return query.deletePersistentAll(services.get(0).getPersonId(), services.get(0).getVisitDate(),
						serviceid);
			};
		}, true);
		jdoTemplate.makePersistentAll(services);
	}

	@Override
	public Collection<PatientService> getServicesByVisitDate(Long patientId, Date visitDate) {
		return jdoTemplate.find(PatientService.class, "personId == pid && visitDate == pdate",
				"java.lang.Long pid, java.util.Date pdate", patientId, visitDate);
	}

	@Override
	public Collection<PatientService> getServicesByDateRange(long patientId, final Date startDate, final Date endDate) {
		return jdoTemplate.find(PatientService.class, "personId == pid && visitDate >= pstart && visitDate <= pend",
				"java.lang.Long pid, java.util.Date pstart, java.util.Date pend", patientId, startDate,
				(endDate == null ? new Date() : endDate));
	}

}
