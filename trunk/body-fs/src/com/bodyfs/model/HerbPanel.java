/* $Id$ */
package com.bodyfs.model;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author kesav
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class HerbPanel implements Serializable {

	private static final long serialVersionUID = -6437624285977205874L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final List<Long> getHerbs() {
		return herbs;
	}

	public final void setHerbs(List<Long> herbs) {
		this.herbs = herbs;
	}

	public final List<Double> getPortions() {
		return portions;
	}

	public final void setPortions(List<Double> portions) {
		this.portions = portions;
	}

	@Persistent
	private String name;
	@Persistent
	private List<Long> herbs;
	private List<Double> portions;

}
