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
public class Gastrointestinal implements Serializable {

	private static final long serialVersionUID = -6459933419450463715L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private boolean nausea;

	@Persistent
	private boolean vomiting;

	@Persistent
	private boolean acidregurgitation;

	@Persistent
	private boolean gas;

	@Persistent
	private boolean hiccup;

	@Persistent
	private boolean bloating;

	@Persistent
	private boolean badbreath;

	@Persistent
	private boolean diarrhea;

	@Persistent
	private boolean constipation;

	@Persistent
	private boolean blackstools;

	@Persistent
	private boolean bloodystools;

	@Persistent
	private boolean mucousinstools;

	@Persistent
	private boolean henorrhoid;

	@Persistent
	private boolean itchyanus;

	@Persistent
	private boolean cramping;

	@Persistent
	private boolean burninganus;

	@Persistent
	private boolean rectalpain;

	@Persistent
	private boolean analfissures;

	@Persistent
	private boolean laxativeusel;

	@Persistent
	private String depression;

	@Persistent
	private String dpresseionfreq;

	@Persistent
	private String bowelfreq;

	@Persistent
	private String color;

	@Persistent
	private String texture;

	@Persistent
	private String odor;

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

	public final boolean isNausea() {
		return nausea;
	}

	public final void setNausea(boolean nausea) {
		this.nausea = nausea;
	}

	public final boolean isVomiting() {
		return vomiting;
	}

	public final void setVomiting(boolean vomiting) {
		this.vomiting = vomiting;
	}

	public final boolean isAcidregurgitation() {
		return acidregurgitation;
	}

	public final void setAcidregurgitation(boolean acidregurgitation) {
		this.acidregurgitation = acidregurgitation;
	}

	public final boolean isGas() {
		return gas;
	}

	public final void setGas(boolean gas) {
		this.gas = gas;
	}

	public final boolean isHiccup() {
		return hiccup;
	}

	public final void setHiccup(boolean hiccup) {
		this.hiccup = hiccup;
	}

	public final boolean isBloating() {
		return bloating;
	}

	public final void setBloating(boolean bloating) {
		this.bloating = bloating;
	}

	public final boolean isBadbreath() {
		return badbreath;
	}

	public final void setBadbreath(boolean badbreath) {
		this.badbreath = badbreath;
	}

	public final boolean isDiarrhea() {
		return diarrhea;
	}

	public final void setDiarrhea(boolean diarrhea) {
		this.diarrhea = diarrhea;
	}

	public final boolean isConstipation() {
		return constipation;
	}

	public final void setConstipation(boolean constipation) {
		this.constipation = constipation;
	}

	public final boolean isBlackstools() {
		return blackstools;
	}

	public final void setBlackstools(boolean blackstools) {
		this.blackstools = blackstools;
	}

	public final boolean isBloodystools() {
		return bloodystools;
	}

	public final void setBloodystools(boolean bloodystools) {
		this.bloodystools = bloodystools;
	}

	public final boolean isMucousinstools() {
		return mucousinstools;
	}

	public final void setMucousinstools(boolean mucousinstools) {
		this.mucousinstools = mucousinstools;
	}

	public final boolean isHenorrhoid() {
		return henorrhoid;
	}

	public final void setHenorrhoid(boolean henorrhoid) {
		this.henorrhoid = henorrhoid;
	}

	public final boolean isItchyanus() {
		return itchyanus;
	}

	public final void setItchyanus(boolean itchyanus) {
		this.itchyanus = itchyanus;
	}

	public final boolean isCramping() {
		return cramping;
	}

	public final void setCramping(boolean cramping) {
		this.cramping = cramping;
	}

	public final boolean isBurninganus() {
		return burninganus;
	}

	public final void setBurninganus(boolean burninganus) {
		this.burninganus = burninganus;
	}

	public final boolean isRectalpain() {
		return rectalpain;
	}

	public final void setRectalpain(boolean rectalpain) {
		this.rectalpain = rectalpain;
	}

	public final boolean isAnalfissures() {
		return analfissures;
	}

	public final void setAnalfissures(boolean analfissures) {
		this.analfissures = analfissures;
	}

	public final boolean isLaxativeusel() {
		return laxativeusel;
	}

	public final void setLaxativeusel(boolean laxativeusel) {
		this.laxativeusel = laxativeusel;
	}

	public final String getDepression() {
		return depression;
	}

	public final void setDepression(String depression) {
		this.depression = depression;
	}

	public final String getDpresseionfreq() {
		return dpresseionfreq;
	}

	public final void setDpresseionfreq(String dpresseionfreq) {
		this.dpresseionfreq = dpresseionfreq;
	}

	public final String getBowelfreq() {
		return bowelfreq;
	}

	public final void setBowelfreq(String bowelfreq) {
		this.bowelfreq = bowelfreq;
	}

	public final String getColor() {
		return color;
	}

	public final void setColor(String color) {
		this.color = color;
	}

	public final String getTexture() {
		return texture;
	}

	public final void setTexture(String texture) {
		this.texture = texture;
	}

	public final String getOdor() {
		return odor;
	}

	public final void setOdor(String odor) {
		this.odor = odor;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
