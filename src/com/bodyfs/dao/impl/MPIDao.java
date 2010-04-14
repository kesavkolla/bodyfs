/* $Id */
package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.IMPIDao;
import com.bodyfs.model.MPIData;

/**
 * 
 * @author kesav
 * 
 */
@Repository(value = "MPIDao")
@SuppressWarnings("unused")
public class MPIDao implements IMPIDao, Serializable {

	private static final long serialVersionUID = 6676627217684586323L;
	private static final Log LOGGER = LogFactory.getLog(MPIDao.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Override
	public void addMPIData(final MPIData data) {
		this.jdoTemplate.makePersistent(data);
	}

	@SuppressWarnings("unchecked")
	public Collection<Date> getExamDates(final Long id) {
		if (id == null || id <= 0) {
			return Collections.EMPTY_LIST;
		}
		return jdoTemplate.executeFind(new JdoCallback<Collection<Date>>() {
			@Override
			public Collection<Date> doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(MPIData.class);
				query.setFilter("personId==pid");
				query.declareParameters("Long pid");
				query.setResultClass(String.class);
				query.setOrdering("examDate desc");
				query.setResult("examDate");
				return (Collection<Date>) query.execute(id);
			}
		});
	}

	@Override
	public MPIData getDataByDate(final Long id, final Date examDate) {
		if (id == null || id <= 0) {
			return null;
		}
		return this.jdoTemplate.execute(new JdoCallback<MPIData>() {
			@Override
			public MPIData doInJdo(final PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(MPIData.class);
				query.setUnique(true);
				query.setOrdering("examDate desc");
				query.setRange(0L, 1L);
				query.setFilter("personId==pid" + ((examDate != null) ? "&& examDate==pdate" : ""));
				query.declareParameters(Long.class.getName() + " pid"
						+ (examDate != null ? ", java.util.Date pdate" : ""));
				if (examDate != null) {
					return (MPIData) query.execute(id, examDate);
				} else {
					return (MPIData) query.execute(id);
				}
			}
		});
	}
}
