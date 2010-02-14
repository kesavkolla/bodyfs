/* $Id$ */
package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;
import com.bodyfs.model.HerbPanel;

/**
 * 
 * @author kesav
 * 
 */
public interface IHerbDAO {

	public void addHerb(final Herb herb);

	public void deleteAll();

	public Herb getHerbById(final Long id);

	public void createPanel(final HerbPanel panel);

	/**
	 * Persists the given formula
	 * 
	 * @param forumula
	 * @return
	 */
	public HerbFormula addFormula(final HerbFormula forumula);

	/**
	 * 
	 * @return Retrieves all the formulas as a list
	 */
	public Collection<HerbFormula> getFormulas();
}
