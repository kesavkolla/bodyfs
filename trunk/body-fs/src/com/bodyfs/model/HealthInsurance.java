/*
 * $Id$
 */
package com.bodyfs.model;

import java.io.Serializable;
import java.util.Date;

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
public class HealthInsurance implements Serializable {

	private static final long serialVersionUID = 3271541712647076578L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long Id;

	@Persistent
	private Long personId;

	@Persistent
	private String insuranceName;

	@Persistent
	private String policy;

	@Persistent
	private String address;

	@Persistent
	private String phone;

	@Persistent
	private Date createDate = new Date(System.currentTimeMillis());

	public HealthInsurance() {
	}

	public HealthInsurance(final Long personId, final String insuranceName, final String policy, final String address,
			final String phone) {
		this.personId = personId;
		this.insuranceName = insuranceName;
		this.policy = policy;
		this.address = address;
		this.phone = phone;
	}

	public final Long getId() {
		return Id;
	}

	public final void setId(final Long id) {
		Id = id;
	}

	public final Long getPersonId() {
		return personId;
	}

	public final void setPersonId(final Long personId) {
		this.personId = personId;
	}

	public final String getInsuranceName() {
		return insuranceName;
	}

	public final void setInsuranceName(final String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public final String getPolicy() {
		return policy;
	}

	public final void setPolicy(final String policy) {
		this.policy = policy;
	}

	public final String getAddress() {
		return address;
	}

	public final void setAddress(final String address) {
		this.address = address;
	}

	public final String getPhone() {
		return phone;
	}

	public final void setPhone(final String phone) {
		this.phone = phone;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

}
