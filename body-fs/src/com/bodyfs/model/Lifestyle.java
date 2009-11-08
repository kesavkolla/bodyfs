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
	private boolean alcohol;

	@Persistent
	private boolean tobacco;

	@Persistent
	private boolean marijuana;

	@Persistent
	private boolean drugs;

	@Persistent
	private boolean stress;

	@Persistent
	private boolean occupationalhazards;

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

	public final boolean isAlcohol() {
		return alcohol;
	}

	public final void setAlcohol(boolean alcohol) {
		this.alcohol = alcohol;
	}

	public final boolean isTobacco() {
		return tobacco;
	}

	public final void setTobacco(boolean tobacco) {
		this.tobacco = tobacco;
	}

	public final boolean isMarijuana() {
		return marijuana;
	}

	public final void setMarijuana(boolean marijuana) {
		this.marijuana = marijuana;
	}

	public final boolean isDrugs() {
		return drugs;
	}

	public final void setDrugs(boolean drugs) {
		this.drugs = drugs;
	}

	public final boolean isStress() {
		return stress;
	}

	public final void setStress(boolean stress) {
		this.stress = stress;
	}

	public final boolean isOccupationalhazards() {
		return occupationalhazards;
	}

	public final void setOccupationalhazards(boolean occupationalhazards) {
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
}
