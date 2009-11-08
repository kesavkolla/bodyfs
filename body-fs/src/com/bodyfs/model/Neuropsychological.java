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
public class Neuropsychological implements Serializable {

	private static final long serialVersionUID = 7953900822422878805L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private boolean seizures;

	@Persistent
	private boolean numbness;

	@Persistent
	private boolean tics;

	@Persistent
	private boolean poormemory;

	@Persistent
	private boolean depression;

	@Persistent
	private boolean anxiety;

	@Persistent
	private boolean irritability;

	@Persistent
	private boolean easilystressed;

	@Persistent
	private boolean abusesurvivor;

	@Persistent
	private boolean suicide;

	@Persistent
	private boolean seeingatherapist;

	@Persistent
	private String Other;

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

	public final boolean isSeizures() {
		return seizures;
	}

	public final void setSeizures(boolean seizures) {
		this.seizures = seizures;
	}

	public final boolean isNumbness() {
		return numbness;
	}

	public final void setNumbness(boolean numbness) {
		this.numbness = numbness;
	}

	public final boolean isTics() {
		return tics;
	}

	public final void setTics(boolean tics) {
		this.tics = tics;
	}

	public final boolean isPoormemory() {
		return poormemory;
	}

	public final void setPoormemory(boolean poormemory) {
		this.poormemory = poormemory;
	}

	public final boolean isDepression() {
		return depression;
	}

	public final void setDepression(boolean depression) {
		this.depression = depression;
	}

	public final boolean isAnxiety() {
		return anxiety;
	}

	public final void setAnxiety(boolean anxiety) {
		this.anxiety = anxiety;
	}

	public final boolean isIrritability() {
		return irritability;
	}

	public final void setIrritability(boolean irritability) {
		this.irritability = irritability;
	}

	public final boolean isEasilystressed() {
		return easilystressed;
	}

	public final void setEasilystressed(boolean easilystressed) {
		this.easilystressed = easilystressed;
	}

	public final boolean isAbusesurvivor() {
		return abusesurvivor;
	}

	public final void setAbusesurvivor(boolean abusesurvivor) {
		this.abusesurvivor = abusesurvivor;
	}

	public final boolean isSuicide() {
		return suicide;
	}

	public final void setSuicide(boolean suicide) {
		this.suicide = suicide;
	}

	public final boolean isSeeingatherapist() {
		return seeingatherapist;
	}

	public final void setSeeingatherapist(boolean seeingatherapist) {
		this.seeingatherapist = seeingatherapist;
	}

	public final String getOther() {
		return Other;
	}

	public final void setOther(String other) {
		Other = other;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
