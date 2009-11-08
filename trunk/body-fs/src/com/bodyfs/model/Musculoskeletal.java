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
	private boolean neckpain;

	@Persistent
	private boolean musclepain;

	@Persistent
	private boolean upperbackpain;

	@Persistent
	private boolean lowbackpain;

	@Persistent
	private boolean jointpain;

	@Persistent
	private boolean ribpain;

	@Persistent
	private boolean limitedrangeofmotion;

	@Persistent
	private boolean limiteduse;

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

	public final boolean isNeckpain() {
		return neckpain;
	}

	public final void setNeckpain(boolean neckpain) {
		this.neckpain = neckpain;
	}

	public final boolean isMusclepain() {
		return musclepain;
	}

	public final void setMusclepain(boolean musclepain) {
		this.musclepain = musclepain;
	}

	public final boolean isUpperbackpain() {
		return upperbackpain;
	}

	public final void setUpperbackpain(boolean upperbackpain) {
		this.upperbackpain = upperbackpain;
	}

	public final boolean isLowbackpain() {
		return lowbackpain;
	}

	public final void setLowbackpain(boolean lowbackpain) {
		this.lowbackpain = lowbackpain;
	}

	public final boolean isJointpain() {
		return jointpain;
	}

	public final void setJointpain(boolean jointpain) {
		this.jointpain = jointpain;
	}

	public final boolean isRibpain() {
		return ribpain;
	}

	public final void setRibpain(boolean ribpain) {
		this.ribpain = ribpain;
	}

	public final boolean isLimitedrangeofmotion() {
		return limitedrangeofmotion;
	}

	public final void setLimitedrangeofmotion(boolean limitedrangeofmotion) {
		this.limitedrangeofmotion = limitedrangeofmotion;
	}

	public final boolean isLimiteduse() {
		return limiteduse;
	}

	public final void setLimiteduse(boolean limiteduse) {
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

}
