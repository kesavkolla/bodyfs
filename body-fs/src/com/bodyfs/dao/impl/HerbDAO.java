/* $Id$ */
package com.bodyfs.dao.impl;

import java.io.Serializable;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;
import com.bodyfs.model.HerbPanel;

/**
 * 
 * @author kesav
 * 
 */
@Repository(value = "herbDAO")
public class HerbDAO implements IHerbDAO, Serializable {

	private static final long serialVersionUID = -2181026768481723335L;
	private static final Log LOGGER = LogFactory.getLog(HerbDAO.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Override
	public void addHerb(final Herb herb) {

		final Herb herb1 = this.jdoTemplate.makePersistent(herb);
		LOGGER.debug("Created herb with id: " + herb1);
	}

	@Override
	public void deleteAll() {
		this.jdoTemplate.execute(new JdoCallback<Long>() {
			@Override
			public Long doInJdo(PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(Herb.class);
				return query.deletePersistentAll();
			}
		});
	}

	@Override
	public Herb getHerbById(final Long id) {
		return this.jdoTemplate.getObjectById(Herb.class, id);
	}

	@Override
	public void createPanel(final HerbPanel panel) {
		this.jdoTemplate.makePersistent(panel);
	}

	@Override
	public HerbFormula createFormula(final HerbFormula forumula) {
		return this.jdoTemplate.makePersistent(forumula);
	}

}
