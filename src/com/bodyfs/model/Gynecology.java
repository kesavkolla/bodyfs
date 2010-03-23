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
	private Boolean agemensesbegan;

	@Persistent
	private String lengthofcycle;

	@Persistent
	private Boolean durationofflow;

	@Persistent
	private Boolean duration;

	@Persistent
	private Boolean irregularperiods;

	@Persistent
	private Boolean painfulperiods;

	@Persistent
	private Boolean pms;

	@Persistent
	private Boolean vaginaldischarge;

	@Persistent
	private String color;

	@Persistent
	private Boolean vaginalsores;

	@Persistent
	private Boolean vaginalodor;

	@Persistent
	private Boolean clots;

	@Persistent
	private Boolean breastlumps;

	@Persistent
	private Integer pregnancies;

	@Persistent
	private Integer livebirths;

	@Persistent
	private Integer prematurebirths;

	@Persistent
	private Integer agearmenopause;

	@Persistent
	private String dateoflastPAP;

	@Persistent
	private String datelastperiodbegan;

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

	public final Boolean getAgemensesbegan() {
		return agemensesbegan;
	}

	public final void setAgemensesbegan(Boolean agemensesbegan) {
		this.agemensesbegan = agemensesbegan;
	}

	public final String getLengthofcycle() {
		return lengthofcycle;
	}

	public final void setLengthofcycle(String lengthofcycle) {
		this.lengthofcycle = lengthofcycle;
	}

	public final Boolean getDurationofflow() {
		return durationofflow;
	}

	public final void setDurationofflow(Boolean durationofflow) {
		this.durationofflow = durationofflow;
	}

	public final Boolean getDuration() {
		return duration;
	}

	public final void setDuration(Boolean duration) {
		this.duration = duration;
	}

	public final Boolean getIrregularperiods() {
		return irregularperiods;
	}

	public final void setIrregularperiods(Boolean irregularperiods) {
		this.irregularperiods = irregularperiods;
	}

	public final Boolean getPainfulperiods() {
		return painfulperiods;
	}

	public final void setPainfulperiods(Boolean painfulperiods) {
		this.painfulperiods = painfulperiods;
	}

	public final Boolean getPms() {
		return pms;
	}

	public final void setPms(Boolean pms) {
		this.pms = pms;
	}

	public final Boolean getVaginaldischarge() {
		return vaginaldischarge;
	}

	public final void setVaginaldischarge(Boolean vaginaldischarge) {
		this.vaginaldischarge = vaginaldischarge;
	}

	public final String getColor() {
		return color;
	}

	public final void setColor(String color) {
		this.color = color;
	}

	public final Boolean getVaginalsores() {
		return vaginalsores;
	}

	public final void setVaginalsores(Boolean vaginalsores) {
		this.vaginalsores = vaginalsores;
	}

	public final Boolean getVaginalodor() {
		return vaginalodor;
	}

	public final void setVaginalodor(Boolean vaginalodor) {
		this.vaginalodor = vaginalodor;
	}

	public final Boolean getClots() {
		return clots;
	}

	public final void setClots(Boolean clots) {
		this.clots = clots;
	}

	public final Boolean getBreastlumps() {
		return breastlumps;
	}

	public final void setBreastlumps(Boolean breastlumps) {
		this.breastlumps = breastlumps;
	}

	public final Integer getPregnancies() {
		return pregnancies;
	}

	public final void setPregnancies(Integer pregnancies) {
		this.pregnancies = pregnancies;
	}

	public final Integer getLivebirths() {
		return livebirths;
	}

	public final void setLivebirths(Integer livebirths) {
		this.livebirths = livebirths;
	}

	public final Integer getPrematurebirths() {
		return prematurebirths;
	}

	public final void setPrematurebirths(Integer prematurebirths) {
		this.prematurebirths = prematurebirths;
	}

	public final Integer getAgearmenopause() {
		return agearmenopause;
	}

	public final void setAgearmenopause(Integer agearmenopause) {
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
		if (agearmenopause != null) {
			return true;
		}
		if (agemensesbegan != null) {
			return true;
		}
		if (breastlumps != null) {
			return true;
		}
		if (clots != null) {
			return true;
		}
		if (color != null) {
			return true;
		}
		if (datelastperiodbegan != null) {
			return true;
		}
		if (dateoflastPAP != null) {
			return true;
		}
		if (duration != null) {
			return true;
		}
		if (durationofflow != null) {
			return true;
		}
		if (irregularperiods != null) {
			return true;
		}
		if (lengthofcycle != null) {
			return true;
		}
		if (livebirths != null) {
			return true;
		}
		if (other != null) {
			return true;
		}
		if (painfulperiods != null) {
			return true;
		}
		if (pms != null) {
			return true;
		}
		if (pregnancies != null) {
			return true;
		}
		if (prematurebirths != null) {
			return true;
		}
		if (vaginaldischarge != null) {
			return true;
		}
		if (vaginalodor != null) {
			return true;
		}
		if (vaginalsores != null) {
			return true;
		}
		return false;
	}

}
