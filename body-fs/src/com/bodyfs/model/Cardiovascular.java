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
public class Cardiovascular implements Serializable {

	private static final long serialVersionUID = -5203666293334632603L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private boolean highbloodpressure;

	@Persistent
	private boolean bloodclots;

	@Persistent
	private boolean lowbloodpressure;

	@Persistent
	private boolean fainting;

	@Persistent
	private boolean chestpain;

	@Persistent
	private boolean difficultybreathing;

	@Persistent
	private boolean tachycardia;

	@Persistent
	private boolean heartpalpitations;

	@Persistent
	private boolean phlebitis;

	@Persistent
	private boolean irregularheartbeat;

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

	public final boolean isHighbloodpressure() {
		return highbloodpressure;
	}

	public final void setHighbloodpressure(boolean highbloodpressure) {
		this.highbloodpressure = highbloodpressure;
	}

	public final boolean isBloodclots() {
		return bloodclots;
	}

	public final void setBloodclots(boolean bloodclots) {
		this.bloodclots = bloodclots;
	}

	public final boolean isLowbloodpressure() {
		return lowbloodpressure;
	}

	public final void setLowbloodpressure(boolean lowbloodpressure) {
		this.lowbloodpressure = lowbloodpressure;
	}

	public final boolean isFainting() {
		return fainting;
	}

	public final void setFainting(boolean fainting) {
		this.fainting = fainting;
	}

	public final boolean isChestpain() {
		return chestpain;
	}

	public final void setChestpain(boolean chestpain) {
		this.chestpain = chestpain;
	}

	public final boolean isDifficultybreathing() {
		return difficultybreathing;
	}

	public final void setDifficultybreathing(boolean difficultybreathing) {
		this.difficultybreathing = difficultybreathing;
	}

	public final boolean isTachycardia() {
		return tachycardia;
	}

	public final void setTachycardia(boolean tachycardia) {
		this.tachycardia = tachycardia;
	}

	public final boolean isHeartpalpitations() {
		return heartpalpitations;
	}

	public final void setHeartpalpitations(boolean heartpalpitations) {
		this.heartpalpitations = heartpalpitations;
	}

	public final boolean isPhlebitis() {
		return phlebitis;
	}

	public final void setPhlebitis(boolean phlebitis) {
		this.phlebitis = phlebitis;
	}

	public final boolean isIrregularheartbeat() {
		return irregularheartbeat;
	}

	public final void setIrregularheartbeat(boolean irregularheartbeat) {
		this.irregularheartbeat = irregularheartbeat;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
