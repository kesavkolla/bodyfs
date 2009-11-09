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
public class Musculoskeletal implements Serializable {

	private static final long serialVersionUID = -6864781914641003332L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Boolean neckpain;

	@Persistent
	private Boolean musclepain;

	@Persistent
	private Boolean upperbackpain;

	@Persistent
	private Boolean lowbackpain;

	@Persistent
	private Boolean jointpain;

	@Persistent
	private Boolean ribpain;

	@Persistent
	private Boolean limitedrangeofmotion;

	@Persistent
	private Boolean limiteduse;

	@Persistent
	private String other;

	@Persistent
	private Date createDate = new Date(System.currentTimeMillis());

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getPersonId() {
		return personId;
	}

	public final void setPersonId(Long personId) {
		this.personId = personId;
	}

	public final Boolean isNeckpain() {
		return neckpain;
	}

	public final void setNeckpain(Boolean neckpain) {
		this.neckpain = neckpain;
	}

	public final Boolean isMusclepain() {
		return musclepain;
	}

	public final void setMusclepain(Boolean musclepain) {
		this.musclepain = musclepain;
	}

	public final Boolean isUpperbackpain() {
		return upperbackpain;
	}

	public final void setUpperbackpain(Boolean upperbackpain) {
		this.upperbackpain = upperbackpain;
	}

	public final Boolean isLowbackpain() {
		return lowbackpain;
	}

	public final void setLowbackpain(Boolean lowbackpain) {
		this.lowbackpain = lowbackpain;
	}

	public final Boolean isJointpain() {
		return jointpain;
	}

	public final void setJointpain(Boolean jointpain) {
		this.jointpain = jointpain;
	}

	public final Boolean isRibpain() {
		return ribpain;
	}

	public final void setRibpain(Boolean ribpain) {
		this.ribpain = ribpain;
	}

	public final Boolean isLimitedrangeofmotion() {
		return limitedrangeofmotion;
	}

	public final void setLimitedrangeofmotion(Boolean limitedrangeofmotion) {
		this.limitedrangeofmotion = limitedrangeofmotion;
	}

	public final Boolean isLimiteduse() {
		return limiteduse;
	}

	public final void setLimiteduse(Boolean limiteduse) {
		this.limiteduse = limiteduse;
	}

	public final String getOther() {
		return other;
	}

	public final void setOther(String other) {
		this.other = other;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * This method will determine if any of the properties of this class is set
	 * or not Based on this value this object is either persisted or discarded
	 * 
	 * @return true if any one of the property is set false otherwise
	 */
	public boolean isDirty() {
		if (jointpain != null) {
			return true;
		}
		if (limitedrangeofmotion != null) {
			return true;
		}
		if (limiteduse != null) {
			return true;
		}
		if (lowbackpain != null) {
			return true;
		}
		if (musclepain != null) {
			return true;
		}
		if (neckpain != null) {
			return true;
		}
		if (other != null) {
			return true;
		}
		if (ribpain != null) {
			return true;
		}
		if (upperbackpain != null) {
			return true;
		}
		return false;
	}

}
