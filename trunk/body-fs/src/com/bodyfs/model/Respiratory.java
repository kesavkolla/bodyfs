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
public class Respiratory implements Serializable {

	private static final long serialVersionUID = 5771390944426377242L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private boolean difficultybreathingwhen;

	@Persistent
	private boolean lyingdown;

	@Persistent
	private boolean shortnessofbreath;

	@Persistent
	private boolean tightchest;

	@Persistent
	private boolean asthamawheezing;

	@Persistent
	private boolean difficultinhalation;

	@Persistent
	private boolean cough;

	@Persistent
	private String wetorDry;

	@Persistent
	private String thickorthin;

	@Persistent
	private boolean colorofphlegm;

	@Persistent
	private boolean coughingupblood;

	@Persistent
	private boolean pneumonia;

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

	public final boolean isDifficultybreathingwhen() {
		return difficultybreathingwhen;
	}

	public final void setDifficultybreathingwhen(boolean difficultybreathingwhen) {
		this.difficultybreathingwhen = difficultybreathingwhen;
	}

	public final boolean isLyingdown() {
		return lyingdown;
	}

	public final void setLyingdown(boolean lyingdown) {
		this.lyingdown = lyingdown;
	}

	public final boolean isShortnessofbreath() {
		return shortnessofbreath;
	}

	public final void setShortnessofbreath(boolean shortnessofbreath) {
		this.shortnessofbreath = shortnessofbreath;
	}

	public final boolean isTightchest() {
		return tightchest;
	}

	public final void setTightchest(boolean tightchest) {
		this.tightchest = tightchest;
	}

	public final boolean isAsthamawheezing() {
		return asthamawheezing;
	}

	public final void setAsthamawheezing(boolean asthamawheezing) {
		this.asthamawheezing = asthamawheezing;
	}

	public final boolean isDifficultinhalation() {
		return difficultinhalation;
	}

	public final void setDifficultinhalation(boolean difficultinhalation) {
		this.difficultinhalation = difficultinhalation;
	}

	public final boolean isCough() {
		return cough;
	}

	public final void setCough(boolean cough) {
		this.cough = cough;
	}

	public final String getWetorDry() {
		return wetorDry;
	}

	public final void setWetorDry(String wetorDry) {
		this.wetorDry = wetorDry;
	}

	public final String getThickorthin() {
		return thickorthin;
	}

	public final void setThickorthin(String thickorthin) {
		this.thickorthin = thickorthin;
	}

	public final boolean isColorofphlegm() {
		return colorofphlegm;
	}

	public final void setColorofphlegm(boolean colorofphlegm) {
		this.colorofphlegm = colorofphlegm;
	}

	public final boolean isCoughingupblood() {
		return coughingupblood;
	}

	public final void setCoughingupblood(boolean coughingupblood) {
		this.coughingupblood = coughingupblood;
	}

	public final boolean isPneumonia() {
		return pneumonia;
	}

	public final void setPneumonia(boolean pneumonia) {
		this.pneumonia = pneumonia;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
