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
	private Boolean highbloodpressure;

	@Persistent
	private Boolean bloodclots;

	@Persistent
	private Boolean lowbloodpressure;

	@Persistent
	private Boolean fainting;

	@Persistent
	private Boolean chestpain;

	@Persistent
	private Boolean difficultybreathing;

	@Persistent
	private Boolean tachycardia;

	@Persistent
	private Boolean heartpalpitations;

	@Persistent
	private Boolean phlebitis;

	@Persistent
	private Boolean irregularheartbeat;

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

	public final Boolean getHighbloodpressure() {
		return highbloodpressure;
	}

	public final void setHighbloodpressure(Boolean highbloodpressure) {
		this.highbloodpressure = highbloodpressure;
	}

	public final Boolean getBloodclots() {
		return bloodclots;
	}

	public final void setBloodclots(Boolean bloodclots) {
		this.bloodclots = bloodclots;
	}

	public final Boolean getLowbloodpressure() {
		return lowbloodpressure;
	}

	public final void setLowbloodpressure(Boolean lowbloodpressure) {
		this.lowbloodpressure = lowbloodpressure;
	}

	public final Boolean getFainting() {
		return fainting;
	}

	public final void setFainting(Boolean fainting) {
		this.fainting = fainting;
	}

	public final Boolean getChestpain() {
		return chestpain;
	}

	public final void setChestpain(Boolean chestpain) {
		this.chestpain = chestpain;
	}

	public final Boolean getDifficultybreathing() {
		return difficultybreathing;
	}

	public final void setDifficultybreathing(Boolean difficultybreathing) {
		this.difficultybreathing = difficultybreathing;
	}

	public final Boolean getTachycardia() {
		return tachycardia;
	}

	public final void setTachycardia(Boolean tachycardia) {
		this.tachycardia = tachycardia;
	}

	public final Boolean getHeartpalpitations() {
		return heartpalpitations;
	}

	public final void setHeartpalpitations(Boolean heartpalpitations) {
		this.heartpalpitations = heartpalpitations;
	}

	public final Boolean getPhlebitis() {
		return phlebitis;
	}

	public final void setPhlebitis(Boolean phlebitis) {
		this.phlebitis = phlebitis;
	}

	public final Boolean getIrregularheartbeat() {
		return irregularheartbeat;
	}

	public final void setIrregularheartbeat(Boolean irregularheartbeat) {
		this.irregularheartbeat = irregularheartbeat;
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
		if (bloodclots != null) {
			return true;
		}
		if (chestpain != null) {
			return true;
		}
		if (difficultybreathing != null) {
			return true;
		}
		if (fainting != null) {
			return true;
		}
		if (heartpalpitations != null) {
			return true;
		}
		if (highbloodpressure != null) {
			return true;
		}
		if (irregularheartbeat != null) {
			return true;
		}
		if (lowbloodpressure != null) {
			return true;
		}
		if (phlebitis != null) {
			return true;
		}
		if (tachycardia != null) {
			return true;
		}
		return false;
	}

}
