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
	private boolean rashes;

	@Persistent
	private boolean hives;

	@Persistent
	private boolean ulcerations;

	@Persistent
	private boolean eczema;

	@Persistent
	private boolean psoriasis;

	@Persistent
	private boolean acne;

	@Persistent
	private boolean dandruff;

	@Persistent
	private boolean itching;

	@Persistent
	private boolean hairloss;

	@Persistent
	private boolean skintexture;

	@Persistent
	private boolean fungalinfections;

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

	public final boolean isRashes() {
		return rashes;
	}

	public final void setRashes(boolean rashes) {
		this.rashes = rashes;
	}

	public final boolean isHives() {
		return hives;
	}

	public final void setHives(boolean hives) {
		this.hives = hives;
	}

	public final boolean isUlcerations() {
		return ulcerations;
	}

	public final void setUlcerations(boolean ulcerations) {
		this.ulcerations = ulcerations;
	}

	public final boolean isEczema() {
		return eczema;
	}

	public final void setEczema(boolean eczema) {
		this.eczema = eczema;
	}

	public final boolean isPsoriasis() {
		return psoriasis;
	}

	public final void setPsoriasis(boolean psoriasis) {
		this.psoriasis = psoriasis;
	}

	public final boolean isAcne() {
		return acne;
	}

	public final void setAcne(boolean acne) {
		this.acne = acne;
	}

	public final boolean isDandruff() {
		return dandruff;
	}

	public final void setDandruff(boolean dandruff) {
		this.dandruff = dandruff;
	}

	public final boolean isItching() {
		return itching;
	}

	public final void setItching(boolean itching) {
		this.itching = itching;
	}

	public final boolean isHairloss() {
		return hairloss;
	}

	public final void setHairloss(boolean hairloss) {
		this.hairloss = hairloss;
	}

	public final boolean isSkintexture() {
		return skintexture;
	}

	public final void setSkintexture(boolean skintexture) {
		this.skintexture = skintexture;
	}

	public final boolean isFungalinfections() {
		return fungalinfections;
	}

	public final void setFungalinfections(boolean fungalinfections) {
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

}
