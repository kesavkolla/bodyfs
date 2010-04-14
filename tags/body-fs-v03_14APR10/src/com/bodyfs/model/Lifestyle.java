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
public class Lifestyle implements Serializable {

	private static final long serialVersionUID = -4595401654650029294L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Boolean alcohol;

	@Persistent
	private Boolean tobacco;

	@Persistent
	private Boolean marijuana;

	@Persistent
	private Boolean drugs;

	@Persistent
	private Boolean stress;

	@Persistent
	private Boolean occupationalhazards;

	@Persistent
	private String exercise1Type;

	@Persistent
	private String freq1;

	@Persistent
	private String exercise2Type;

	@Persistent
	private String freq2;

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

	public final Boolean isAlcohol() {
		return alcohol;
	}

	public final void setAlcohol(Boolean alcohol) {
		this.alcohol = alcohol;
	}

	public final Boolean isTobacco() {
		return tobacco;
	}

	public final void setTobacco(Boolean tobacco) {
		this.tobacco = tobacco;
	}

	public final Boolean isMarijuana() {
		return marijuana;
	}

	public final void setMarijuana(Boolean marijuana) {
		this.marijuana = marijuana;
	}

	public final Boolean isDrugs() {
		return drugs;
	}

	public final void setDrugs(Boolean drugs) {
		this.drugs = drugs;
	}

	public final Boolean isStress() {
		return stress;
	}

	public final void setStress(Boolean stress) {
		this.stress = stress;
	}

	public final Boolean isOccupationalhazards() {
		return occupationalhazards;
	}

	public final void setOccupationalhazards(Boolean occupationalhazards) {
		this.occupationalhazards = occupationalhazards;
	}

	public final String getExercise1Type() {
		return exercise1Type;
	}

	public final void setExercise1Type(String exercise1Type) {
		this.exercise1Type = exercise1Type;
	}

	public final String getFreq1() {
		return freq1;
	}

	public final void setFreq1(String freq1) {
		this.freq1 = freq1;
	}

	public final String getExercise2Type() {
		return exercise2Type;
	}

	public final void setExercise2Type(String exercise2Type) {
		this.exercise2Type = exercise2Type;
	}

	public final String getFreq2() {
		return freq2;
	}

	public final void setFreq2(String freq2) {
		this.freq2 = freq2;
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
		if (alcohol != null) {
			return true;
		}
		if (drugs != null) {
			return true;
		}
		if (exercise1Type != null) {
			return true;
		}
		if (exercise2Type != null) {
			return true;
		}
		if (freq1 != null) {
			return true;
		}
		if (freq2 != null) {
			return true;
		}
		if (marijuana != null) {
			return true;
		}
		if (occupationalhazards != null) {
			return true;
		}
		if (stress != null) {
			return true;
		}
		if (tobacco != null) {
			return true;
		}
		return false;
	}

}
