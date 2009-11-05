/*
 * $Id$
 */
package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.Page;

public interface IPageDAO {

	public Page getById(final String id);

	public Collection<Page> getAll();
}
