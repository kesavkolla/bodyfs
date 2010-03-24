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
 * @author Kesav Kumar Kolla
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class HerbFormula implements Serializable {
	private static final long serialVersionUID = 6300003259517005440L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String lowername;

	@Persistent
	private String description;

	@Persistent()
	private List<Long> herbs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (name != null) {
			this.lowername = name.toLowerCase();
		}
	}

	public String getLowername() {
		return lowername;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getHerbs() {
		return herbs;
	}

	public void setHerbs(List<Long> herbs) {
		this.herbs = herbs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HerbFormula [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (name != null)
			builder.append("name=").append(name);
		if (herbs != null)
			builder.append("herbs=").append(herbs).append(", ");
		builder.append("]");
		return builder.toString();
	}

}
