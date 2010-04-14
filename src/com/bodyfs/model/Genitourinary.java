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
public class Genitourinary implements Serializable {

	private static final long serialVersionUID = -4695673071234056693L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Boolean painonurination;

	@Persistent
	private Boolean frequenturination;

	@Persistent
	private Boolean urgenturination;

	@Persistent
	private Boolean bloodinurine;

	@Persistent
	private Boolean unabletoholdurinne;

	@Persistent
	private Boolean incompleteurination;

	@Persistent
	private Boolean venerealdisease;

	@Persistent
	private Boolean bedwetting;

	@Persistent
	private Boolean waketourinate;

	@Persistent
	private Boolean increasedlibido;

	@Persistent
	private Boolean decreasedlibido;

	@Persistent
	private Boolean kidneystone;

	@Persistent
	private Boolean impotence;

	@Persistent
	private Boolean prematureejaculation;

	@Persistent
	private Boolean nocturnalemission;

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

	public final Boolean getPainonurination() {
		return painonurination;
	}

	public final void setPainonurination(Boolean painonurination) {
		this.painonurination = painonurination;
	}

	public final Boolean getFrequenturination() {
		return frequenturination;
	}

	public final void setFrequenturination(Boolean frequenturination) {
		this.frequenturination = frequenturination;
	}

	public final Boolean getUrgenturination() {
		return urgenturination;
	}

	public final void setUrgenturination(Boolean urgenturination) {
		this.urgenturination = urgenturination;
	}

	public final Boolean getBloodinurine() {
		return bloodinurine;
	}

	public final void setBloodinurine(Boolean bloodinurine) {
		this.bloodinurine = bloodinurine;
	}

	public final Boolean getUnabletoholdurinne() {
		return unabletoholdurinne;
	}

	public final void setUnabletoholdurinne(Boolean unabletoholdurinne) {
		this.unabletoholdurinne = unabletoholdurinne;
	}

	public final Boolean getIncompleteurination() {
		return incompleteurination;
	}

	public final void setIncompleteurination(Boolean incompleteurination) {
		this.incompleteurination = incompleteurination;
	}

	public final Boolean getVenerealdisease() {
		return venerealdisease;
	}

	public final void setVenerealdisease(Boolean venerealdisease) {
		this.venerealdisease = venerealdisease;
	}

	public final Boolean getBedwetting() {
		return bedwetting;
	}

	public final void setBedwetting(Boolean bedwetting) {
		this.bedwetting = bedwetting;
	}

	public final Boolean getWaketourinate() {
		return waketourinate;
	}

	public final void setWaketourinate(Boolean waketourinate) {
		this.waketourinate = waketourinate;
	}

	public final Boolean getIncreasedlibido() {
		return increasedlibido;
	}

	public final void setIncreasedlibido(Boolean increasedlibido) {
		this.increasedlibido = increasedlibido;
	}

	public final Boolean getDecreasedlibido() {
		return decreasedlibido;
	}

	public final void setDecreasedlibido(Boolean decreasedlibido) {
		this.decreasedlibido = decreasedlibido;
	}

	public final Boolean getKidneystone() {
		return kidneystone;
	}

	public final void setKidneystone(Boolean kidneystone) {
		this.kidneystone = kidneystone;
	}

	public final Boolean getImpotence() {
		return impotence;
	}

	public final void setImpotence(Boolean impotence) {
		this.impotence = impotence;
	}

	public final Boolean getPrematureejaculation() {
		return prematureejaculation;
	}

	public final void setPrematureejaculation(Boolean prematureejaculation) {
		this.prematureejaculation = prematureejaculation;
	}

	public final Boolean getNocturnalemission() {
		return nocturnalemission;
	}

	public final void setNocturnalemission(Boolean nocturnalemission) {
		this.nocturnalemission = nocturnalemission;
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
		if (bedwetting != null) {
			return true;
		}
		if (bloodinurine != null) {
			return true;
		}
		if (decreasedlibido != null) {
			return true;
		}
		if (frequenturination != null) {
			return true;
		}
		if (impotence != null) {
			return true;
		}
		if (incompleteurination != null) {
			return true;
		}
		if (increasedlibido != null) {
			return true;
		}
		if (kidneystone != null) {
			return true;
		}
		if (nocturnalemission != null) {
			return true;
		}
		if (painonurination != null) {
			return true;
		}
		if (prematureejaculation != null) {
			return true;
		}
		if (unabletoholdurinne != null) {
			return true;
		}
		if (urgenturination != null) {
			return true;
		}
		if (venerealdisease != null) {
			return true;
		}
		if (waketourinate != null) {
			return true;
		}
		return false;
	}
}
