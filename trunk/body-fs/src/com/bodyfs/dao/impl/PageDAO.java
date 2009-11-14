/*
 * $Id$
 */
package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.stereotype.Repository;

import com.bodyfs.controllers.PersonController;
import com.bodyfs.dao.IPageDAO;
import com.bodyfs.model.Page;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@Repository(value = "pageDAO")
public class PageDAO implements IPageDAO, Serializable {
	private static final long serialVersionUID = 8048078038056041156L;
	private static final Log LOGGER = LogFactory.getLog(PersonController.class);
	private static final MemcacheService cacheService = MemcacheServiceFactory.getMemcacheService();

	@Override
	public Page getById(String id) {
		LOGGER.debug("Getting page object for id: " + id);
		final Element pages = (Element) cacheService.get(IPageDAO.PAGES_DOCUMENT);
		if (pages == null) {
			return null;
		}
		final Element page = (Element) pages.selectSingleNode("page[@id='" + id + "']");
		if (page == null) {
			return null;
		}

		return new Page(page.attributeValue("id"), page.attributeValue("title"), page.attributeValue("path"), page
				.attributeValue("target"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Page> getAll() {
		if (cacheService.get(IPageDAO.PAGES_DOCUMENT) == null) {
			return Collections.EMPTY_LIST;
		}
		final List<Element> pages = (List<Element>) ((Element) cacheService.get(IPageDAO.PAGES_DOCUMENT))
				.elements("page");

		final List<Page> pagesList = new ArrayList<Page>(pages.size());
		for (final Element element : pages) {
			pagesList.add(new Page(element.attributeValue("id"), element.attributeValue("title"), element
					.attributeValue("path"), element.attributeValue("target")));
		}
		return pagesList;
	}

}
