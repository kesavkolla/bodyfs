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
	private Boolean seizures;

	@Persistent
	private Boolean numbness;

	@Persistent
	private Boolean tics;

	@Persistent
	private Boolean poormemory;

	@Persistent
	private Boolean depression;

	@Persistent
	private Boolean anxiety;

	@Persistent
	private Boolean irritability;

	@Persistent
	private Boolean easilystressed;

	@Persistent
	private Boolean abusesurvivor;

	@Persistent
	private Boolean suicide;

	@Persistent
	private Boolean seeingatherapist;

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

	public final Boolean getSeizures() {
		return seizures;
	}

	public final void setSeizures(Boolean seizures) {
		this.seizures = seizures;
	}

	public final Boolean getNumbness() {
		return numbness;
	}

	public final void setNumbness(Boolean numbness) {
		this.numbness = numbness;
	}

	public final Boolean getTics() {
		return tics;
	}

	public final void setTics(Boolean tics) {
		this.tics = tics;
	}

	public final Boolean getPoormemory() {
		return poormemory;
	}

	public final void setPoormemory(Boolean poormemory) {
		this.poormemory = poormemory;
	}

	public final Boolean getDepression() {
		return depression;
	}

	public final void setDepression(Boolean depression) {
		this.depression = depression;
	}

	public final Boolean getAnxiety() {
		return anxiety;
	}

	public final void setAnxiety(Boolean anxiety) {
		this.anxiety = anxiety;
	}

	public final Boolean getIrritability() {
		return irritability;
	}

	public final void setIrritability(Boolean irritability) {
		this.irritability = irritability;
	}

	public final Boolean getEasilystressed() {
		return easilystressed;
	}

	public final void setEasilystressed(Boolean easilystressed) {
		this.easilystressed = easilystressed;
	}

	public final Boolean getAbusesurvivor() {
		return abusesurvivor;
	}

	public final void setAbusesurvivor(Boolean abusesurvivor) {
		this.abusesurvivor = abusesurvivor;
	}

	public final Boolean getSuicide() {
		return suicide;
	}

	public final void setSuicide(Boolean suicide) {
		this.suicide = suicide;
	}

	public final Boolean getSeeingatherapist() {
		return seeingatherapist;
	}

	public final void setSeeingatherapist(Boolean seeingatherapist) {
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

	/**
	 * This method will determine if any of the properties of this class is set
	 * or not Based on this value this object is either persisted or discarded
	 * 
	 * @return true if any one of the property is set false otherwise
	 */
	public boolean isDirty() {
		if (Other != null) {
			return true;
		}
		if (abusesurvivor != null) {
			return true;
		}
		if (anxiety != null) {
			return true;
		}
		if (depression != null) {
			return true;
		}
		if (easilystressed != null) {
			return true;
		}
		if (irritability != null) {
			return true;
		}
		if (numbness != null) {
			return true;
		}
		if (poormemory != null) {
			return true;
		}
		if (seeingatherapist != null) {
			return true;
		}
		if (seizures != null) {
			return true;
		}
		if (suicide != null) {
			return true;
		}
		if (tics != null) {
			return true;
		}
		return false;
	}

}
