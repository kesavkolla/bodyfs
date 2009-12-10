/* $Id$ */
package com.bodyfs.dao;

import com.bodyfs.model.Herb;

/**
 * 
 * @author kesav
 * 
 */
public interface IHerbDAO {

	public void addHerb(final Herb herb);
	public void deleteAll();
}
