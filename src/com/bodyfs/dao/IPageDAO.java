/*
 * $Id$
 */
package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.Page;

public interface IPageDAO {
	public static final String PAGES_DOCUMENT = "pagesdocument";

	public Page getById(final String id);

	public Collection<Page> getAll();
}
