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
public class Gynecology implements Serializable {

	private static final long serialVersionUID = 2784647412214379640L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private boolean agemensesbegan;

	@Persistent
	private String lengthofcycle;

	@Persistent
	private boolean durationofflow;

	@Persistent
	private boolean duration;

	@Persistent
	private boolean irregularperiods;

	@Persistent
	private boolean painfulperiods;

	@Persistent
	private boolean pms;

	@Persistent
	private boolean vaginaldischarge;

	@Persistent
	private String color;

	@Persistent
	private boolean vaginalsores;

	@Persistent
	private boolean vaginalodor;

	@Persistent
	private boolean clots;

	@Persistent
	private boolean breastlumps;

	@Persistent
	private int pregnancies;

	@Persistent
	private int livebirths;

	@Persistent
	private boolean prematurebirths;

	@Persistent
	private int agearmenopause;

	@Persistent
	private String dateoflastPAP;

	@Persistent
	private String datelastperiodbegan;

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

	public final boolean isAgemensesbegan() {
		return agemensesbegan;
	}

	public final void setAgemensesbegan(boolean agemensesbegan) {
		this.agemensesbegan = agemensesbegan;
	}

	public final String getLengthofcycle() {
		return lengthofcycle;
	}

	public final void setLengthofcycle(String lengthofcycle) {
		this.lengthofcycle = lengthofcycle;
	}

	public final boolean isDurationofflow() {
		return durationofflow;
	}

	public final void setDurationofflow(boolean durationofflow) {
		this.durationofflow = durationofflow;
	}

	public final boolean isDuration() {
		return duration;
	}

	public final void setDuration(boolean duration) {
		this.duration = duration;
	}

	public final boolean isIrregularperiods() {
		return irregularperiods;
	}

	public final void setIrregularperiods(boolean irregularperiods) {
		this.irregularperiods = irregularperiods;
	}

	public final boolean isPainfulperiods() {
		return painfulperiods;
	}

	public final void setPainfulperiods(boolean painfulperiods) {
		this.painfulperiods = painfulperiods;
	}

	public final boolean isPms() {
		return pms;
	}

	public final void setPms(boolean pms) {
		this.pms = pms;
	}

	public final boolean isVaginaldischarge() {
		return vaginaldischarge;
	}

	public final void setVaginaldischarge(boolean vaginaldischarge) {
		this.vaginaldischarge = vaginaldischarge;
	}

	public final String getColor() {
		return color;
	}

	public final void setColor(String color) {
		this.color = color;
	}

	public final boolean isVaginalsores() {
		return vaginalsores;
	}

	public final void setVaginalsores(boolean vaginalsores) {
		this.vaginalsores = vaginalsores;
	}

	public final boolean isVaginalodor() {
		return vaginalodor;
	}

	public final void setVaginalodor(boolean vaginalodor) {
		this.vaginalodor = vaginalodor;
	}

	public final boolean isClots() {
		return clots;
	}

	public final void setClots(boolean clots) {
		this.clots = clots;
	}

	public final boolean isBreastlumps() {
		return breastlumps;
	}

	public final void setBreastlumps(boolean breastlumps) {
		this.breastlumps = breastlumps;
	}

	public final int getPregnancies() {
		return pregnancies;
	}

	public final void setPregnancies(int pregnancies) {
		this.pregnancies = pregnancies;
	}

	public final int getLivebirths() {
		return livebirths;
	}

	public final void setLivebirths(int livebirths) {
		this.livebirths = livebirths;
	}

	public final boolean isPrematurebirths() {
		return prematurebirths;
	}

	public final void setPrematurebirths(boolean prematurebirths) {
		this.prematurebirths = prematurebirths;
	}

	public final int getAgearmenopause() {
		return agearmenopause;
	}

	public final void setAgearmenopause(int agearmenopause) {
		this.agearmenopause = agearmenopause;
	}

	public final String getDateoflastPAP() {
		return dateoflastPAP;
	}

	public final void setDateoflastPAP(String dateoflastPAP) {
		this.dateoflastPAP = dateoflastPAP;
	}

	public final String getDatelastperiodbegan() {
		return datelastperiodbegan;
	}

	public final void setDatelastperiodbegan(String datelastperiodbegan) {
		this.datelastperiodbegan = datelastperiodbegan;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
