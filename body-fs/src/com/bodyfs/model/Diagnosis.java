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
public class Diagnosis implements Serializable {
	private static final long serialVersionUID = 8055079755347748856L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String name;
	@Persistent
	private String lowername;
	@Persistent
	private String description;
	@Persistent
	private List<Long> formulas;

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
			this.setLowername(name.toLowerCase());
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getFormulas() {
		return formulas;
	}

	public void setFormulas(List<Long> formulas) {
		this.formulas = formulas;
	}

	public String getLowername() {
		return lowername;
	}

	public void setLowername(String lowername) {
		this.lowername = lowername;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Diagnosis [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (name != null)
			builder.append("name=").append(name);
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (formulas != null)
			builder.append("formulas=").append(formulas).append(", ");
		builder.append("]");
		return builder.toString();
	}

}
