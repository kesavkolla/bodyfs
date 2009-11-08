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
public class PastMedicalHistory implements Serializable {

	private static final long serialVersionUID = -8612358499864225915L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private boolean aids;

	@Persistent
	private boolean alcoholism;

	@Persistent
	private boolean allergies;

	@Persistent
	private boolean appendicitis;

	@Persistent
	private boolean arteriosclerosis;

	@Persistent
	private boolean asthma;

	@Persistent
	private boolean birthtrauma;

	@Persistent
	private boolean cancer;

	@Persistent
	private boolean chickenpox;

	@Persistent
	private boolean diabetes;

	@Persistent
	private boolean emphysema;

	@Persistent
	private boolean epilepsy;

	@Persistent
	private boolean goiter;

	@Persistent
	private boolean gout;

	@Persistent
	private boolean heartdisease;

	@Persistent
	private boolean hepatitis;

	@Persistent
	private boolean herpes;

	@Persistent
	private boolean highbloodpressure;

	@Persistent
	private boolean measles;

	@Persistent
	private boolean multipleSclerosis;

	@Persistent
	private boolean mumps;

	@Persistent
	private boolean pacemaker;

	@Persistent
	private boolean pleurisy;

	@Persistent
	private boolean pneumonia;

	@Persistent
	private boolean polio;

	@Persistent
	private boolean rheumaticfever;

	@Persistent
	private boolean scarletfever;

	@Persistent
	private boolean seizures;

	@Persistent
	private boolean stroke;

	@Persistent
	private boolean surgery;

	@Persistent
	private String surgeryList;

	@Persistent
	private boolean hyroiddisorders;

	@Persistent
	private boolean majortrauma;

	@Persistent
	private String carfalletc;

	@Persistent
	private boolean tuberculosis;

	@Persistent
	private boolean typhoidfever;

	@Persistent
	private boolean ulcers;

	@Persistent
	private boolean venerealdisease;

	@Persistent
	private boolean whoopingcough;

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

	public final boolean isAids() {
		return aids;
	}

	public final void setAids(boolean aids) {
		this.aids = aids;
	}

	public final boolean isAlcoholism() {
		return alcoholism;
	}

	public final void setAlcoholism(boolean alcoholism) {
		this.alcoholism = alcoholism;
	}

	public final boolean isAllergies() {
		return allergies;
	}

	public final void setAllergies(boolean allergies) {
		this.allergies = allergies;
	}

	public final boolean isAppendicitis() {
		return appendicitis;
	}

	public final void setAppendicitis(boolean appendicitis) {
		this.appendicitis = appendicitis;
	}

	public final boolean isArteriosclerosis() {
		return arteriosclerosis;
	}

	public final void setArteriosclerosis(boolean arteriosclerosis) {
		this.arteriosclerosis = arteriosclerosis;
	}

	public final boolean isAsthma() {
		return asthma;
	}

	public final void setAsthma(boolean asthma) {
		this.asthma = asthma;
	}

	public final boolean isBirthtrauma() {
		return birthtrauma;
	}

	public final void setBirthtrauma(boolean birthtrauma) {
		this.birthtrauma = birthtrauma;
	}

	public final boolean isCancer() {
		return cancer;
	}

	public final void setCancer(boolean cancer) {
		this.cancer = cancer;
	}

	public final boolean isChickenpox() {
		return chickenpox;
	}

	public final void setChickenpox(boolean chickenpox) {
		this.chickenpox = chickenpox;
	}

	public final boolean isDiabetes() {
		return diabetes;
	}

	public final void setDiabetes(boolean diabetes) {
		this.diabetes = diabetes;
	}

	public final boolean isEmphysema() {
		return emphysema;
	}

	public final void setEmphysema(boolean emphysema) {
		this.emphysema = emphysema;
	}

	public final boolean isEpilepsy() {
		return epilepsy;
	}

	public final void setEpilepsy(boolean epilepsy) {
		this.epilepsy = epilepsy;
	}

	public final boolean isGoiter() {
		return goiter;
	}

	public final void setGoiter(boolean goiter) {
		this.goiter = goiter;
	}

	public final boolean isGout() {
		return gout;
	}

	public final void setGout(boolean gout) {
		this.gout = gout;
	}

	public final boolean isHeartdisease() {
		return heartdisease;
	}

	public final void setHeartdisease(boolean heartdisease) {
		this.heartdisease = heartdisease;
	}

	public final boolean isHepatitis() {
		return hepatitis;
	}

	public final void setHepatitis(boolean hepatitis) {
		this.hepatitis = hepatitis;
	}

	public final boolean isHerpes() {
		return herpes;
	}

	public final void setHerpes(boolean herpes) {
		this.herpes = herpes;
	}

	public final boolean isHighbloodpressure() {
		return highbloodpressure;
	}

	public final void setHighbloodpressure(boolean highbloodpressure) {
		this.highbloodpressure = highbloodpressure;
	}

	public final boolean isMeasles() {
		return measles;
	}

	public final void setMeasles(boolean measles) {
		this.measles = measles;
	}

	public final boolean isMultipleSclerosis() {
		return multipleSclerosis;
	}

	public final void setMultipleSclerosis(boolean multipleSclerosis) {
		this.multipleSclerosis = multipleSclerosis;
	}

	public final boolean isMumps() {
		return mumps;
	}

	public final void setMumps(boolean mumps) {
		this.mumps = mumps;
	}

	public final boolean isPacemaker() {
		return pacemaker;
	}

	public final void setPacemaker(boolean pacemaker) {
		this.pacemaker = pacemaker;
	}

	public final boolean isPleurisy() {
		return pleurisy;
	}

	public final void setPleurisy(boolean pleurisy) {
		this.pleurisy = pleurisy;
	}

	public final boolean isPneumonia() {
		return pneumonia;
	}

	public final void setPneumonia(boolean pneumonia) {
		this.pneumonia = pneumonia;
	}

	public final boolean isPolio() {
		return polio;
	}

	public final void setPolio(boolean polio) {
		this.polio = polio;
	}

	public final boolean isRheumaticfever() {
		return rheumaticfever;
	}

	public final void setRheumaticfever(boolean rheumaticfever) {
		this.rheumaticfever = rheumaticfever;
	}

	public final boolean isScarletfever() {
		return scarletfever;
	}

	public final void setScarletfever(boolean scarletfever) {
		this.scarletfever = scarletfever;
	}

	public final boolean isSeizures() {
		return seizures;
	}

	public final void setSeizures(boolean seizures) {
		this.seizures = seizures;
	}

	public final boolean isStroke() {
		return stroke;
	}

	public final void setStroke(boolean stroke) {
		this.stroke = stroke;
	}

	public final boolean isSurgery() {
		return surgery;
	}

	public final void setSurgery(boolean surgery) {
		this.surgery = surgery;
	}

	public final String getSurgeryList() {
		return surgeryList;
	}

	public final void setSurgeryList(String surgeryList) {
		this.surgeryList = surgeryList;
	}

	public final boolean isHyroiddisorders() {
		return hyroiddisorders;
	}

	public final void setHyroiddisorders(boolean hyroiddisorders) {
		this.hyroiddisorders = hyroiddisorders;
	}

	public final boolean isMajortrauma() {
		return majortrauma;
	}

	public final void setMajortrauma(boolean majortrauma) {
		this.majortrauma = majortrauma;
	}

	public final String getCarfalletc() {
		return carfalletc;
	}

	public final void setCarfalletc(String carfalletc) {
		this.carfalletc = carfalletc;
	}

	public final boolean isTuberculosis() {
		return tuberculosis;
	}

	public final void setTuberculosis(boolean tuberculosis) {
		this.tuberculosis = tuberculosis;
	}

	public final boolean isTyphoidfever() {
		return typhoidfever;
	}

	public final void setTyphoidfever(boolean typhoidfever) {
		this.typhoidfever = typhoidfever;
	}

	public final boolean isUlcers() {
		return ulcers;
	}

	public final void setUlcers(boolean ulcers) {
		this.ulcers = ulcers;
	}

	public final boolean isVenerealdisease() {
		return venerealdisease;
	}

	public final void setVenerealdisease(boolean venerealdisease) {
		this.venerealdisease = venerealdisease;
	}

	public final boolean isWhoopingcough() {
		return whoopingcough;
	}

	public final void setWhoopingcough(boolean whoopingcough) {
		this.whoopingcough = whoopingcough;
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
