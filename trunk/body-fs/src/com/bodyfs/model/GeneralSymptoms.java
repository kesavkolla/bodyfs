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
public class GeneralSymptoms implements Serializable {

	private static final long serialVersionUID = -3199229724491001804L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Boolean poorappetite;

	@Persistent
	private Boolean heavyappetite;

	@Persistent
	private Boolean cooldrinks;

	@Persistent
	private Boolean hotdrinks;

	@Persistent
	private Boolean weight;

	@Persistent
	private Boolean poorsleep;

	@Persistent
	private Boolean heavysleep;

	@Persistent
	private Boolean disturbedsleep;

	@Persistent
	private Boolean fatigue;

	@Persistent
	private Boolean lackofStrength;

	@Persistent
	private Boolean bodilyheaviness;

	@Persistent
	private Boolean coldhandsorfeet;

	@Persistent
	private Boolean poorcirculation;

	@Persistent
	private Boolean shortnessofbreath;

	@Persistent
	private Boolean fever;

	@Persistent
	private Boolean chills;

	@Persistent
	private Boolean nightsweat;

	@Persistent
	private Boolean sweateasily;

	@Persistent
	private Boolean musclecramps;

	@Persistent
	private Boolean vertigo;

	@Persistent
	private Boolean bleed;

	@Persistent
	private Boolean peculartaste;

	@Persistent
	private Date createDate;

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

	public final Boolean isPoorappetite() {
		return poorappetite;
	}

	public final void setPoorappetite(Boolean poorappetite) {
		this.poorappetite = poorappetite;
	}

	public final Boolean isHeavyappetite() {
		return heavyappetite;
	}

	public final void setHeavyappetite(Boolean heavyappetite) {
		this.heavyappetite = heavyappetite;
	}

	public final Boolean isCooldrinks() {
		return cooldrinks;
	}

	public final void setCooldrinks(Boolean cooldrinks) {
		this.cooldrinks = cooldrinks;
	}

	public final Boolean isHotdrinks() {
		return hotdrinks;
	}

	public final void setHotdrinks(Boolean hotdrinks) {
		this.hotdrinks = hotdrinks;
	}

	public final Boolean isWeight() {
		return weight;
	}

	public final void setWeight(Boolean weight) {
		this.weight = weight;
	}

	public final Boolean isPoorsleep() {
		return poorsleep;
	}

	public final void setPoorsleep(Boolean poorsleep) {
		this.poorsleep = poorsleep;
	}

	public final Boolean isHeavysleep() {
		return heavysleep;
	}

	public final void setHeavysleep(Boolean heavysleep) {
		this.heavysleep = heavysleep;
	}

	public final Boolean isDisturbedsleep() {
		return disturbedsleep;
	}

	public final void setDisturbedsleep(Boolean disturbedsleep) {
		this.disturbedsleep = disturbedsleep;
	}

	public final Boolean isFatigue() {
		return fatigue;
	}

	public final void setFatigue(Boolean fatigue) {
		this.fatigue = fatigue;
	}

	public final Boolean isLackofStrength() {
		return lackofStrength;
	}

	public final void setLackofStrength(Boolean lackofStrength) {
		this.lackofStrength = lackofStrength;
	}

	public final Boolean isBodilyheaviness() {
		return bodilyheaviness;
	}

	public final void setBodilyheaviness(Boolean bodilyheaviness) {
		this.bodilyheaviness = bodilyheaviness;
	}

	public final Boolean isColdhandsorfeet() {
		return coldhandsorfeet;
	}

	public final void setColdhandsorfeet(Boolean coldhandsorfeet) {
		this.coldhandsorfeet = coldhandsorfeet;
	}

	public final Boolean isPoorcirculation() {
		return poorcirculation;
	}

	public final void setPoorcirculation(Boolean poorcirculation) {
		this.poorcirculation = poorcirculation;
	}

	public final Boolean isShortnessofbreath() {
		return shortnessofbreath;
	}

	public final void setShortnessofbreath(Boolean shortnessofbreath) {
		this.shortnessofbreath = shortnessofbreath;
	}

	public final Boolean isFever() {
		return fever;
	}

	public final void setFever(Boolean fever) {
		this.fever = fever;
	}

	public final Boolean isChills() {
		return chills;
	}

	public final void setChills(Boolean chills) {
		this.chills = chills;
	}

	public final Boolean isNightsweat() {
		return nightsweat;
	}

	public final void setNightsweat(Boolean nightsweat) {
		this.nightsweat = nightsweat;
	}

	public final Boolean isSweateasily() {
		return sweateasily;
	}

	public final void setSweateasily(Boolean sweateasily) {
		this.sweateasily = sweateasily;
	}

	public final Boolean isMusclecramps() {
		return musclecramps;
	}

	public final void setMusclecramps(Boolean musclecramps) {
		this.musclecramps = musclecramps;
	}

	public final Boolean isVertigo() {
		return vertigo;
	}

	public final void setVertigo(Boolean vertigo) {
		this.vertigo = vertigo;
	}

	public final Boolean isBleed() {
		return bleed;
	}

	public final void setBleed(Boolean bleed) {
		this.bleed = bleed;
	}

	public final Boolean isPeculartaste() {
		return peculartaste;
	}

	public final void setPeculartaste(Boolean peculartaste) {
		this.peculartaste = peculartaste;
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
		if (bleed != null) {
			return true;
		}
		if (bodilyheaviness != null) {
			return true;
		}
		if (chills != null) {
			return true;
		}
		if (coldhandsorfeet != null) {
			return true;
		}
		if (cooldrinks != null) {
			return true;
		}
		if (createDate != null) {
			return true;
		}
		if (disturbedsleep != null) {
			return true;
		}
		if (fatigue != null) {
			return true;
		}
		if (fever != null) {
			return true;
		}
		if (heavyappetite != null) {
			return true;
		}
		if (heavysleep != null) {
			return true;
		}
		if (hotdrinks != null) {
			return true;
		}
		if (lackofStrength != null) {
			return true;
		}
		if (musclecramps != null) {
			return true;
		}
		if (nightsweat != null) {
			return true;
		}
		if (peculartaste != null) {
			return true;
		}
		if (poorappetite != null) {
			return true;
		}
		if (poorcirculation != null) {
			return true;
		}
		if (poorsleep != null) {
			return true;
		}
		if (shortnessofbreath != null) {
			return true;
		}
		if (sweateasily != null) {
			return true;
		}
		if (vertigo != null) {
			return true;
		}
		if (weight != null) {
			return true;
		}
		return false;
	}

}
