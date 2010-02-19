/* $Id$*/
package com.bodyfs.dao.impl;

import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.ICounterDAO;
import com.bodyfs.model.BodyfsCounters;

/**
 * 
 * @author Kesav Kumar Kolla
 * 
 */
@Repository(value = "counterDAO")
public class CounterDAO implements ICounterDAO {
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Override
	public int decrement(final String name) {
		final BodyfsCounters counter = jdoTemplate.getObjectById(BodyfsCounters.class, name);
		if (counter == null) {
			return 0;
		}

		int curcount = counter.getCount();
		if (curcount <= 0) {
			return 0;
		}
		counter.setCount(counter.getCount() - 1);
		jdoTemplate.makePersistent(counter);
		return counter.getCount();
	}

	@Override
	public int getCount(final String name) {
		try {
			final BodyfsCounters counter = jdoTemplate.getObjectById(BodyfsCounters.class, name);
			if (counter == null) {
				return 0;
			}
			return counter.getCount();
		} catch (final Exception e) {
			return 0;
		}
	}

	@Override
	public int increment(final String name) {
		BodyfsCounters counter = jdoTemplate.getObjectById(BodyfsCounters.class, name);
		if (counter == null) {
			counter = new BodyfsCounters();
			counter.setName(name);
			counter.setCount(0);
		}
		counter.setCount(counter.getCount() + 1);
		jdoTemplate.makePersistent(counter);

		return counter.getCount();
	}

	@Override
	public void createCount(final String name, int initVal) {
		final BodyfsCounters counter = new BodyfsCounters();
		counter.setName(name);
		counter.setCount(initVal);
		this.jdoTemplate.makePersistent(counter);
	}

	@Override
	public void resetCount(String name, int count) {
		final BodyfsCounters counter = jdoTemplate.getObjectById(BodyfsCounters.class, name);
		if (counter != null) {
			counter.setCount(count < 0 ? 0 : count);
			jdoTemplate.makePersistent(counter);
		}
	}

}
