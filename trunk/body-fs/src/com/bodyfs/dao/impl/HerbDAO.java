/* $Id$ */
package com.bodyfs.dao.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.model.Herb;

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
}
