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
	private Boolean aids;

	@Persistent
	private Boolean alcoholism;

	@Persistent
	private Boolean allergies;

	@Persistent
	private Boolean appendicitis;

	@Persistent
	private Boolean arteriosclerosis;

	@Persistent
	private Boolean asthma;

	@Persistent
	private Boolean birthtrauma;

	@Persistent
	private Boolean cancer;

	@Persistent
	private Boolean chickenpox;

	@Persistent
	private Boolean diabetes;

	@Persistent
	private Boolean emphysema;

	@Persistent
	private Boolean epilepsy;

	@Persistent
	private Boolean goiter;

	@Persistent
	private Boolean gout;

	@Persistent
	private Boolean heartdisease;

	@Persistent
	private Boolean hepatitis;

	@Persistent
	private Boolean herpes;

	@Persistent
	private Boolean highbloodpressure;

	@Persistent
	private Boolean measles;

	@Persistent
	private Boolean multipleSclerosis;

	@Persistent
	private Boolean mumps;

	@Persistent
	private Boolean pacemaker;

	@Persistent
	private Boolean pleurisy;

	@Persistent
	private Boolean pneumonia;

	@Persistent
	private Boolean polio;

	@Persistent
	private Boolean rheumaticfever;

	@Persistent
	private Boolean scarletfever;

	@Persistent
	private Boolean seizures;

	@Persistent
	private Boolean stroke;

	@Persistent
	private Boolean surgery;

	@Persistent
	private String surgeryList;

	@Persistent
	private Boolean hyroiddisorders;

	@Persistent
	private Boolean majortrauma;

	@Persistent
	private String carfalletc;

	@Persistent
	private Boolean tuberculosis;

	@Persistent
	private Boolean typhoidfever;

	@Persistent
	private Boolean ulcers;

	@Persistent
	private Boolean venerealdisease;

	@Persistent
	private Boolean whoopingcough;

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

	public final Boolean getAids() {
		return aids;
	}

	public final void setAids(Boolean aids) {
		this.aids = aids;
	}

	public final Boolean getAlcoholism() {
		return alcoholism;
	}

	public final void setAlcoholism(Boolean alcoholism) {
		this.alcoholism = alcoholism;
	}

	public final Boolean getAllergies() {
		return allergies;
	}

	public final void setAllergies(Boolean allergies) {
		this.allergies = allergies;
	}

	public final Boolean getAppendicitis() {
		return appendicitis;
	}

	public final void setAppendicitis(Boolean appendicitis) {
		this.appendicitis = appendicitis;
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

	public final Boolean getBirthtrauma() {
		return birthtrauma;
	}

	public final void setBirthtrauma(Boolean birthtrauma) {
		this.birthtrauma = birthtrauma;
	}

	public final Boolean getCancer() {
		return cancer;
	}

	public final void setCancer(Boolean cancer) {
		this.cancer = cancer;
	}

	public final Boolean getChickenpox() {
		return chickenpox;
	}

	public final void setChickenpox(Boolean chickenpox) {
		this.chickenpox = chickenpox;
	}

	public final Boolean getDiabetes() {
		return diabetes;
	}

	public final void setDiabetes(Boolean diabetes) {
		this.diabetes = diabetes;
	}

	public final Boolean getEmphysema() {
		return emphysema;
	}

	public final void setEmphysema(Boolean emphysema) {
		this.emphysema = emphysema;
	}

	public final Boolean getEpilepsy() {
		return epilepsy;
	}

	public final void setEpilepsy(Boolean epilepsy) {
		this.epilepsy = epilepsy;
	}

	public final Boolean getGoiter() {
		return goiter;
	}

	public final void setGoiter(Boolean goiter) {
		this.goiter = goiter;
	}

	public final Boolean getGout() {
		return gout;
	}

	public final void setGout(Boolean gout) {
		this.gout = gout;
	}

	public final Boolean getHeartdisease() {
		return heartdisease;
	}

	public final void setHeartdisease(Boolean heartdisease) {
		this.heartdisease = heartdisease;
	}

	public final Boolean getHepatitis() {
		return hepatitis;
	}

	public final void setHepatitis(Boolean hepatitis) {
		this.hepatitis = hepatitis;
	}

	public final Boolean getHerpes() {
		return herpes;
	}

	public final void setHerpes(Boolean herpes) {
		this.herpes = herpes;
	}

	public final Boolean getHighbloodpressure() {
		return highbloodpressure;
	}

	public final void setHighbloodpressure(Boolean highbloodpressure) {
		this.highbloodpressure = highbloodpressure;
	}

	public final Boolean getMeasles() {
		return measles;
	}

	public final void setMeasles(Boolean measles) {
		this.measles = measles;
	}

	public final Boolean getMultipleSclerosis() {
		return multipleSclerosis;
	}

	public final void setMultipleSclerosis(Boolean multipleSclerosis) {
		this.multipleSclerosis = multipleSclerosis;
	}

	public final Boolean getMumps() {
		return mumps;
	}

	public final void setMumps(Boolean mumps) {
		this.mumps = mumps;
	}

	public final Boolean getPacemaker() {
		return pacemaker;
	}

	public final void setPacemaker(Boolean pacemaker) {
		this.pacemaker = pacemaker;
	}

	public final Boolean getPleurisy() {
		return pleurisy;
	}

	public final void setPleurisy(Boolean pleurisy) {
		this.pleurisy = pleurisy;
	}

	public final Boolean getPneumonia() {
		return pneumonia;
	}

	public final void setPneumonia(Boolean pneumonia) {
		this.pneumonia = pneumonia;
	}

	public final Boolean getPolio() {
		return polio;
	}

	public final void setPolio(Boolean polio) {
		this.polio = polio;
	}

	public final Boolean getRheumaticfever() {
		return rheumaticfever;
	}

	public final void setRheumaticfever(Boolean rheumaticfever) {
		this.rheumaticfever = rheumaticfever;
	}

	public final Boolean getScarletfever() {
		return scarletfever;
	}

	public final void setScarletfever(Boolean scarletfever) {
		this.scarletfever = scarletfever;
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

	public final Boolean getSurgery() {
		return surgery;
	}

	public final void setSurgery(Boolean surgery) {
		this.surgery = surgery;
	}

	public final String getSurgeryList() {
		return surgeryList;
	}

	public final void setSurgeryList(String surgeryList) {
		this.surgeryList = surgeryList;
	}

	public final Boolean getHyroiddisorders() {
		return hyroiddisorders;
	}

	public final void setHyroiddisorders(Boolean hyroiddisorders) {
		this.hyroiddisorders = hyroiddisorders;
	}

	public final Boolean getMajortrauma() {
		return majortrauma;
	}

	public final void setMajortrauma(Boolean majortrauma) {
		this.majortrauma = majortrauma;
	}

	public final String getCarfalletc() {
		return carfalletc;
	}

	public final void setCarfalletc(String carfalletc) {
		this.carfalletc = carfalletc;
	}

	public final Boolean getTuberculosis() {
		return tuberculosis;
	}

	public final void setTuberculosis(Boolean tuberculosis) {
		this.tuberculosis = tuberculosis;
	}

	public final Boolean getTyphoidfever() {
		return typhoidfever;
	}

	public final void setTyphoidfever(Boolean typhoidfever) {
		this.typhoidfever = typhoidfever;
	}

	public final Boolean getUlcers() {
		return ulcers;
	}

	public final void setUlcers(Boolean ulcers) {
		this.ulcers = ulcers;
	}

	public final Boolean getVenerealdisease() {
		return venerealdisease;
	}

	public final void setVenerealdisease(Boolean venerealdisease) {
		this.venerealdisease = venerealdisease;
	}

	public final Boolean getWhoopingcough() {
		return whoopingcough;
	}

	public final void setWhoopingcough(Boolean whoopingcough) {
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

	/**
	 * This method will determine if any of the properties of this class is set
	 * or not Based on this value this object is either persisted or discarded
	 * 
	 * @return true if any one of the property is set false otherwise
	 */
	public boolean isDirty() {
		if (aids != null) {
			return true;
		}
		if (alcoholism != null) {
			return true;
		}
		if (allergies != null) {
			return true;
		}
		if (appendicitis != null) {
			return true;
		}
		if (arteriosclerosis != null) {
			return true;
		}
		if (asthma != null) {
			return true;
		}
		if (birthtrauma != null) {
			return true;
		}
		if (cancer != null) {
			return true;
		}
		if (carfalletc != null) {
			return true;
		}
		if (chickenpox != null) {
			return true;
		}
		if (diabetes != null) {
			return true;
		}
		if (emphysema != null) {
			return true;
		}
		if (epilepsy != null) {
			return true;
		}
		if (goiter != null) {
			return true;
		}
		if (gout != null) {
			return true;
		}
		if (heartdisease != null) {
			return true;
		}
		if (hepatitis != null) {
			return true;
		}
		if (herpes != null) {
			return true;
		}
		if (highbloodpressure != null) {
			return true;
		}
		if (hyroiddisorders != null) {
			return true;
		}
		if (majortrauma != null) {
			return true;
		}
		if (measles != null) {
			return true;
		}
		if (multipleSclerosis != null) {
			return true;
		}
		if (mumps != null) {
			return true;
		}
		if (other != null) {
			return true;
		}
		if (pacemaker != null) {
			return true;
		}
		if (pleurisy != null) {
			return true;
		}
		if (pneumonia != null) {
			return true;
		}
		if (polio != null) {
			return true;
		}
		if (rheumaticfever != null) {
			return true;
		}
		if (scarletfever != null) {
			return true;
		}
		if (seizures != null) {
			return true;
		}
		if (stroke != null) {
			return true;
		}
		if (surgery != null) {
			return true;
		}
		if (surgeryList != null) {
			return true;
		}
		if (tuberculosis != null) {
			return true;
		}
		if (typhoidfever != null) {
			return true;
		}
		if (ulcers != null) {
			return true;
		}
		if (venerealdisease != null) {
			return true;
		}
		if (whoopingcough != null) {
			return true;
		}
		return false;
	}

}
