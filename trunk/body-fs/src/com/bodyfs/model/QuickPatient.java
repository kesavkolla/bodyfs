package com.bodyfs.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.DATASTORE, detachable = "true")
public class QuickPatient implements Serializable {
	private static final long serialVersionUID = 7959914729380921531L;

	@PrimaryKey
	private Long id;

	@Persistent
	private Date date = new Date();

	@Persistent
	private String name;
	
	@Persistent
	private Boolean isNewPatient = false;
	
	

	/**
	 * @return the isNewPatient
	 */
	public Boolean getIsNewPatient() {
		return isNewPatient;
	}

	/**
	 * @param isNewPatient the isNewPatient to set
	 */
	public void setIsNewPatient(Boolean isNewPatient) {
		this.isNewPatient = isNewPatient;
	}

	@Persistent
	public Date getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuickPatient [date=").append(date).append(", id=").append(id).append(", name=").append(name)
				.append("]");
		return builder.toString();
	}

}
