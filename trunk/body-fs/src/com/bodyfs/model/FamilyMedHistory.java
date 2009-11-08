/*
 * $Id$
 */
package com.bodyfs.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class FamilyMedHistory implements Serializable {

	private static final long serialVersionUID = -175379416338884054L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private boolean allergies;

	@Persistent
	private List<String> allergy;

	@Persistent
	private boolean arteriosclerosis;

	@Persistent
	private boolean asthma;

	@Persistent
	private boolean alcoholism;

	@Persistent
	private boolean cancer;

	@Persistent
	private String cancerType;

	@Persistent
	private boolean depression;

	@Persistent
	private boolean diabetes;

	@Persistent
	private boolean heartdisease;

	@Persistent
	private boolean highbloodpressure;

	@Persistent
	private boolean seizures;

	@Persistent
	private boolean stroke;

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

	public final boolean isAllergies() {
		return allergies;
	}

	public final void setAllergies(boolean allergies) {
		this.allergies = allergies;
	}

	public final List<String> getAllergy() {
		return allergy;
	}

	public final void setAllergy(List<String> allergy) {
		this.allergy = allergy;
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

	public final boolean isAlcoholism() {
		return alcoholism;
	}

	public final void setAlcoholism(boolean alcoholism) {
		this.alcoholism = alcoholism;
	}

	public final boolean isCancer() {
		return cancer;
	}

	public final void setCancer(boolean cancer) {
		this.cancer = cancer;
	}

	public final String getCancerType() {
		return cancerType;
	}

	public final void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}

	public final boolean isDepression() {
		return depression;
	}

	public final void setDepression(boolean depression) {
		this.depression = depression;
	}

	public final boolean isDiabetes() {
		return diabetes;
	}

	public final void setDiabetes(boolean diabetes) {
		this.diabetes = diabetes;
	}

	public final boolean isHeartdisease() {
		return heartdisease;
	}

	public final void setHeartdisease(boolean heartdisease) {
		this.heartdisease = heartdisease;
	}

	public final boolean isHighbloodpressure() {
		return highbloodpressure;
	}

	public final void setHighbloodpressure(boolean highbloodpressure) {
		this.highbloodpressure = highbloodpressure;
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

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
