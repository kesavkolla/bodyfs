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
	private Boolean allergies;

	@Persistent
	private List<String> allergy;

	@Persistent
	private Boolean arteriosclerosis;

	@Persistent
	private Boolean asthma;

	@Persistent
	private Boolean alcoholism;

	@Persistent
	private String cancer;

	@Persistent
	private String cancerType;

	@Persistent
	private Boolean depression;

	@Persistent
	private Boolean diabetes;

	@Persistent
	private Boolean heartdisease;

	@Persistent
	private Boolean highbloodpressure;

	@Persistent
	private Boolean seizures;

	@Persistent
	private Boolean stroke;

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

	public final Boolean getAllergies() {
		return allergies;
	}

	public final void setAllergies(Boolean allergies) {
		this.allergies = allergies;
	}

	public final List<String> getAllergy() {
		return allergy;
	}

	public final void setAllergy(List<String> allergy) {
		this.allergy = allergy;
	}

	public final Boolean getArteriosclerosis() {
		return arteriosclerosis;
	}

	public final void setArteriosclerosis(Boolean arteriosclerosis) {
		this.arteriosclerosis = arteriosclerosis;
	}

	public final Boolean getAsthma() {
		return asthma;
	}

	public final void setAsthma(Boolean asthma) {
		this.asthma = asthma;
	}

	public final Boolean getAlcoholism() {
		return alcoholism;
	}

	public final void setAlcoholism(Boolean alcoholism) {
		this.alcoholism = alcoholism;
	}

	public final String getCancer() {
		return cancer;
	}

	public final void setCancer(String cancer) {
		this.cancer = cancer;
	}

	public final String getCancerType() {
		return cancerType;
	}

	public final void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}

	public final Boolean getDepression() {
		return depression;
	}

	public final void setDepression(Boolean depression) {
		this.depression = depression;
	}

	public final Boolean getDiabetes() {
		return diabetes;
	}

	public final void setDiabetes(Boolean diabetes) {
		this.diabetes = diabetes;
	}

	public final Boolean getHeartdisease() {
		return heartdisease;
	}

	public final void setHeartdisease(Boolean heartdisease) {
		this.heartdisease = heartdisease;
	}

	public final Boolean getHighbloodpressure() {
		return highbloodpressure;
	}

	public final void setHighbloodpressure(Boolean highbloodpressure) {
		this.highbloodpressure = highbloodpressure;
	}

	public final Boolean getSeizures() {
		return seizures;
	}

	public final void setSeizures(Boolean seizures) {
		this.seizures = seizures;
	}

	public final Boolean getStroke() {
		return stroke;
	}

	public final void setStroke(Boolean stroke) {
		this.stroke = stroke;
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
		if (alcoholism != null) {
			return true;
		}
		if (allergies != null) {
			return true;
		}
		if (allergy != null) {
			return true;
		}
		if (arteriosclerosis != null) {
			return true;
		}
		if (asthma != null) {
			return true;
		}
		if (cancer != null) {
			return true;
		}
		if (cancerType != null) {
			return true;
		}
		if (depression != null) {
			return true;
		}
		if (diabetes != null) {
			return true;
		}
		if (heartdisease != null) {
			return true;
		}
		if (highbloodpressure != null) {
			return true;
		}
		if (seizures != null) {
			return true;
		}
		if (stroke != null) {
			return true;
		}
		return false;
	}

}
