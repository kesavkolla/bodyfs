/* $Id$ */
package com.bodyfs.dao;

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

	public HerbFormula createFormula(final HerbFormula forumula);
}
