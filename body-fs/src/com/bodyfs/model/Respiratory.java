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
	private Boolean difficultybreathingwhen;

	@Persistent
	private Boolean lyingdown;

	@Persistent
	private Boolean shortnessofbreath;

	@Persistent
	private Boolean tightchest;

	@Persistent
	private Boolean asthamawheezing;

	@Persistent
	private Boolean difficultinhalation;

	@Persistent
	private Boolean cough;

	@Persistent
	private String wetorDry;

	@Persistent
	private String thickorthin;

	@Persistent
	private Boolean colorofphlegm;

	@Persistent
	private Boolean coughingupblood;

	@Persistent
	private Boolean pneumonia;

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

	public final Boolean isDifficultybreathingwhen() {
		return difficultybreathingwhen;
	}

	public final void setDifficultybreathingwhen(Boolean difficultybreathingwhen) {
		this.difficultybreathingwhen = difficultybreathingwhen;
	}

	public final Boolean isLyingdown() {
		return lyingdown;
	}

	public final void setLyingdown(Boolean lyingdown) {
		this.lyingdown = lyingdown;
	}

	public final Boolean isShortnessofbreath() {
		return shortnessofbreath;
	}

	public final void setShortnessofbreath(Boolean shortnessofbreath) {
		this.shortnessofbreath = shortnessofbreath;
	}

	public final Boolean isTightchest() {
		return tightchest;
	}

	public final void setTightchest(Boolean tightchest) {
		this.tightchest = tightchest;
	}

	public final Boolean isAsthamawheezing() {
		return asthamawheezing;
	}

	public final void setAsthamawheezing(Boolean asthamawheezing) {
		this.asthamawheezing = asthamawheezing;
	}

	public final Boolean isDifficultinhalation() {
		return difficultinhalation;
	}

	public final void setDifficultinhalation(Boolean difficultinhalation) {
		this.difficultinhalation = difficultinhalation;
	}

	public final Boolean isCough() {
		return cough;
	}

	public final void setCough(Boolean cough) {
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

	public final Boolean isColorofphlegm() {
		return colorofphlegm;
	}

	public final void setColorofphlegm(Boolean colorofphlegm) {
		this.colorofphlegm = colorofphlegm;
	}

	public final Boolean isCoughingupblood() {
		return coughingupblood;
	}

	public final void setCoughingupblood(Boolean coughingupblood) {
		this.coughingupblood = coughingupblood;
	}

	public final Boolean isPneumonia() {
		return pneumonia;
	}

	public final void setPneumonia(Boolean pneumonia) {
		this.pneumonia = pneumonia;
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
		if (asthamawheezing != null) {
			return true;
		}
		if (colorofphlegm != null) {
			return true;
		}
		if (cough != null) {
			return true;
		}
		if (coughingupblood != null) {
			return true;
		}
		if (difficultinhalation != null) {
			return true;
		}
		if (difficultybreathingwhen != null) {
			return true;
		}
		if (lyingdown != null) {
			return true;
		}
		if (pneumonia != null) {
			return true;
		}
		if (shortnessofbreath != null) {
			return true;
		}
		if (thickorthin != null) {
			return true;
		}
		if (tightchest != null) {
			return true;
		}
		if (wetorDry != null) {
			return true;
		}
		return false;
	}

}
