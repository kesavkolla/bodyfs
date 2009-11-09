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
public class SkinHair implements Serializable {

	private static final long serialVersionUID = -7978389308995256450L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Boolean rashes;

	@Persistent
	private Boolean hives;

	@Persistent
	private Boolean ulcerations;

	@Persistent
	private Boolean eczema;

	@Persistent
	private Boolean psoriasis;

	@Persistent
	private Boolean acne;

	@Persistent
	private Boolean dandruff;

	@Persistent
	private Boolean itching;

	@Persistent
	private Boolean hairloss;

	@Persistent
	private Boolean skintexture;

	@Persistent
	private Boolean fungalinfections;

	@Persistent
	private String other;

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

	public final Boolean isRashes() {
		return rashes;
	}

	public final void setRashes(Boolean rashes) {
		this.rashes = rashes;
	}

	public final Boolean isHives() {
		return hives;
	}

	public final void setHives(Boolean hives) {
		this.hives = hives;
	}

	public final Boolean isUlcerations() {
		return ulcerations;
	}

	public final void setUlcerations(Boolean ulcerations) {
		this.ulcerations = ulcerations;
	}

	public final Boolean isEczema() {
		return eczema;
	}

	public final void setEczema(Boolean eczema) {
		this.eczema = eczema;
	}

	public final Boolean isPsoriasis() {
		return psoriasis;
	}

	public final void setPsoriasis(Boolean psoriasis) {
		this.psoriasis = psoriasis;
	}

	public final Boolean isAcne() {
		return acne;
	}

	public final void setAcne(Boolean acne) {
		this.acne = acne;
	}

	public final Boolean isDandruff() {
		return dandruff;
	}

	public final void setDandruff(Boolean dandruff) {
		this.dandruff = dandruff;
	}

	public final Boolean isItching() {
		return itching;
	}

	public final void setItching(Boolean itching) {
		this.itching = itching;
	}

	public final Boolean isHairloss() {
		return hairloss;
	}

	public final void setHairloss(Boolean hairloss) {
		this.hairloss = hairloss;
	}

	public final Boolean isSkintexture() {
		return skintexture;
	}

	public final void setSkintexture(Boolean skintexture) {
		this.skintexture = skintexture;
	}

	public final Boolean isFungalinfections() {
		return fungalinfections;
	}

	public final void setFungalinfections(Boolean fungalinfections) {
		this.fungalinfections = fungalinfections;
	}

	public final String getOther() {
		return other;
	}

	public final void setOther(String other) {
		this.other = other;
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
		if (acne != null) {
			return true;
		}
		if (dandruff != null) {
			return true;
		}
		if (eczema != null) {
			return true;
		}
		if (fungalinfections != null) {
			return true;
		}
		if (hairloss != null) {
			return true;
		}
		if (hives != null) {
			return true;
		}
		if (itching != null) {
			return true;
		}
		if (other != null) {
			return true;
		}
		if (psoriasis != null) {
			return true;
		}
		if (rashes != null) {
			return true;
		}
		if (skintexture != null) {
			return true;
		}
		if (ulcerations != null) {
			return true;
		}
		return false;
	}

}
