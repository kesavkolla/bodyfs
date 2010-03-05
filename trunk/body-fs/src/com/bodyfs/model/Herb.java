/* $Id$ */
package com.bodyfs.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class Herb implements Serializable {

	private static final long serialVersionUID = -2740917335487828692L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String lowername;

	@Persistent
	private String description;

	public final Long getId() {
		return id;
	}

	public final void setId(final Long id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String Name) {
		this.name = Name;
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Herb [");
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (name != null)
			builder.append("name=").append(name);
		builder.append("]");
		return builder.toString();
	}

}
