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
	private boolean poorappetite;

	@Persistent
	private boolean heavyappetite;

	@Persistent
	private boolean cooldrinks;

	@Persistent
	private boolean hotdrinks;

	@Persistent
	private boolean weight;

	@Persistent
	private boolean poorsleep;

	@Persistent
	private boolean heavysleep;

	@Persistent
	private boolean disturbedsleep;

	@Persistent
	private boolean fatigue;

	@Persistent
	private boolean lackofStrength;

	@Persistent
	private boolean bodilyheaviness;

	@Persistent
	private boolean coldhandsorfeet;

	@Persistent
	private boolean poorcirculation;

	@Persistent
	private boolean shortnessofbreath;

	@Persistent
	private boolean fever;

	@Persistent
	private boolean chills;

	@Persistent
	private boolean nightsweat;

	@Persistent
	private boolean sweateasily;

	@Persistent
	private boolean musclecramps;

	@Persistent
	private boolean vertigo;

	@Persistent
	private boolean bleed;

	@Persistent
	private boolean peculartaste;

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

	public final boolean isPoorappetite() {
		return poorappetite;
	}

	public final void setPoorappetite(boolean poorappetite) {
		this.poorappetite = poorappetite;
	}

	public final boolean isHeavyappetite() {
		return heavyappetite;
	}

	public final void setHeavyappetite(boolean heavyappetite) {
		this.heavyappetite = heavyappetite;
	}

	public final boolean isCooldrinks() {
		return cooldrinks;
	}

	public final void setCooldrinks(boolean cooldrinks) {
		this.cooldrinks = cooldrinks;
	}

	public final boolean isHotdrinks() {
		return hotdrinks;
	}

	public final void setHotdrinks(boolean hotdrinks) {
		this.hotdrinks = hotdrinks;
	}

	public final boolean isWeight() {
		return weight;
	}

	public final void setWeight(boolean weight) {
		this.weight = weight;
	}

	public final boolean isPoorsleep() {
		return poorsleep;
	}

	public final void setPoorsleep(boolean poorsleep) {
		this.poorsleep = poorsleep;
	}

	public final boolean isHeavysleep() {
		return heavysleep;
	}

	public final void setHeavysleep(boolean heavysleep) {
		this.heavysleep = heavysleep;
	}

	public final boolean isDisturbedsleep() {
		return disturbedsleep;
	}

	public final void setDisturbedsleep(boolean disturbedsleep) {
		this.disturbedsleep = disturbedsleep;
	}

	public final boolean isFatigue() {
		return fatigue;
	}

	public final void setFatigue(boolean fatigue) {
		this.fatigue = fatigue;
	}

	public final boolean isLackofStrength() {
		return lackofStrength;
	}

	public final void setLackofStrength(boolean lackofStrength) {
		this.lackofStrength = lackofStrength;
	}

	public final boolean isBodilyheaviness() {
		return bodilyheaviness;
	}

	public final void setBodilyheaviness(boolean bodilyheaviness) {
		this.bodilyheaviness = bodilyheaviness;
	}

	public final boolean isColdhandsorfeet() {
		return coldhandsorfeet;
	}

	public final void setColdhandsorfeet(boolean coldhandsorfeet) {
		this.coldhandsorfeet = coldhandsorfeet;
	}

	public final boolean isPoorcirculation() {
		return poorcirculation;
	}

	public final void setPoorcirculation(boolean poorcirculation) {
		this.poorcirculation = poorcirculation;
	}

	public final boolean isShortnessofbreath() {
		return shortnessofbreath;
	}

	public final void setShortnessofbreath(boolean shortnessofbreath) {
		this.shortnessofbreath = shortnessofbreath;
	}

	public final boolean isFever() {
		return fever;
	}

	public final void setFever(boolean fever) {
		this.fever = fever;
	}

	public final boolean isChills() {
		return chills;
	}

	public final void setChills(boolean chills) {
		this.chills = chills;
	}

	public final boolean isNightsweat() {
		return nightsweat;
	}

	public final void setNightsweat(boolean nightsweat) {
		this.nightsweat = nightsweat;
	}

	public final boolean isSweateasily() {
		return sweateasily;
	}

	public final void setSweateasily(boolean sweateasily) {
		this.sweateasily = sweateasily;
	}

	public final boolean isMusclecramps() {
		return musclecramps;
	}

	public final void setMusclecramps(boolean musclecramps) {
		this.musclecramps = musclecramps;
	}

	public final boolean isVertigo() {
		return vertigo;
	}

	public final void setVertigo(boolean vertigo) {
		this.vertigo = vertigo;
	}

	public final boolean isBleed() {
		return bleed;
	}

	public final void setBleed(boolean bleed) {
		this.bleed = bleed;
	}

	public final boolean isPeculartaste() {
		return peculartaste;
	}

	public final void setPeculartaste(boolean peculartaste) {
		this.peculartaste = peculartaste;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
