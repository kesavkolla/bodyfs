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
	private Boolean nausea;

	@Persistent
	private Boolean vomiting;

	@Persistent
	private Boolean acidregurgitation;

	@Persistent
	private Boolean gas;

	@Persistent
	private Boolean hiccup;

	@Persistent
	private Boolean bloating;

	@Persistent
	private Boolean badbreath;

	@Persistent
	private Boolean diarrhea;

	@Persistent
	private Boolean constipation;

	@Persistent
	private Boolean blackstools;

	@Persistent
	private Boolean bloodystools;

	@Persistent
	private Boolean mucousinstools;

	@Persistent
	private Boolean henorrhoid;

	@Persistent
	private Boolean itchyanus;

	@Persistent
	private Boolean cramping;

	@Persistent
	private Boolean burninganus;

	@Persistent
	private Boolean rectalpain;

	@Persistent
	private Boolean analfissures;

	@Persistent
	private Boolean laxativeusel;

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

	public final Boolean getNausea() {
		return nausea;
	}

	public final void setNausea(Boolean nausea) {
		this.nausea = nausea;
	}

	public final Boolean getVomiting() {
		return vomiting;
	}

	public final void setVomiting(Boolean vomiting) {
		this.vomiting = vomiting;
	}

	public final Boolean getAcidregurgitation() {
		return acidregurgitation;
	}

	public final void setAcidregurgitation(Boolean acidregurgitation) {
		this.acidregurgitation = acidregurgitation;
	}

	public final Boolean getGas() {
		return gas;
	}

	public final void setGas(Boolean gas) {
		this.gas = gas;
	}

	public final Boolean getHiccup() {
		return hiccup;
	}

	public final void setHiccup(Boolean hiccup) {
		this.hiccup = hiccup;
	}

	public final Boolean getBloating() {
		return bloating;
	}

	public final void setBloating(Boolean bloating) {
		this.bloating = bloating;
	}

	public final Boolean getBadbreath() {
		return badbreath;
	}

	public final void setBadbreath(Boolean badbreath) {
		this.badbreath = badbreath;
	}

	public final Boolean getDiarrhea() {
		return diarrhea;
	}

	public final void setDiarrhea(Boolean diarrhea) {
		this.diarrhea = diarrhea;
	}

	public final Boolean getConstipation() {
		return constipation;
	}

	public final void setConstipation(Boolean constipation) {
		this.constipation = constipation;
	}

	public final Boolean getBlackstools() {
		return blackstools;
	}

	public final void setBlackstools(Boolean blackstools) {
		this.blackstools = blackstools;
	}

	public final Boolean getBloodystools() {
		return bloodystools;
	}

	public final void setBloodystools(Boolean bloodystools) {
		this.bloodystools = bloodystools;
	}

	public final Boolean getMucousinstools() {
		return mucousinstools;
	}

	public final void setMucousinstools(Boolean mucousinstools) {
		this.mucousinstools = mucousinstools;
	}

	public final Boolean getHenorrhoid() {
		return henorrhoid;
	}

	public final void setHenorrhoid(Boolean henorrhoid) {
		this.henorrhoid = henorrhoid;
	}

	public final Boolean getItchyanus() {
		return itchyanus;
	}

	public final void setItchyanus(Boolean itchyanus) {
		this.itchyanus = itchyanus;
	}

	public final Boolean getCramping() {
		return cramping;
	}

	public final void setCramping(Boolean cramping) {
		this.cramping = cramping;
	}

	public final Boolean getBurninganus() {
		return burninganus;
	}

	public final void setBurninganus(Boolean burninganus) {
		this.burninganus = burninganus;
	}

	public final Boolean getRectalpain() {
		return rectalpain;
	}

	public final void setRectalpain(Boolean rectalpain) {
		this.rectalpain = rectalpain;
	}

	public final Boolean getAnalfissures() {
		return analfissures;
	}

	public final void setAnalfissures(Boolean analfissures) {
		this.analfissures = analfissures;
	}

	public final Boolean getLaxativeusel() {
		return laxativeusel;
	}

	public final void setLaxativeusel(Boolean laxativeusel) {
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

	/***
	 * This method will determine if any of the properties of this class is set
	 * or not Based on this value this object is either persisted or discarded
	 * 
	 * @return true if any one of the property is set false otherwise
	 */
	public boolean isDirty() {
		if (acidregurgitation != null) {
			return true;
		}
		if (analfissures != null) {
			return true;
		}
		if (badbreath != null) {
			return true;
		}
		if (blackstools != null) {
			return true;
		}
		if (bloating != null) {
			return true;
		}
		if (bloodystools != null) {
			return true;
		}
		if (bowelfreq != null) {
			return true;
		}
		if (burninganus != null) {
			return true;
		}
		if (color != null) {
			return true;
		}
		if (constipation != null) {
			return true;
		}
		if (cramping != null) {
			return true;
		}
		if (depression != null) {
			return true;
		}
		if (diarrhea != null) {
			return true;
		}
		if (dpresseionfreq != null) {
			return true;
		}
		if (gas != null) {
			return true;
		}
		if (henorrhoid != null) {
			return true;
		}
		if (hiccup != null) {
			return true;
		}
		if (itchyanus != null) {
			return true;
		}
		if (laxativeusel != null) {
			return true;
		}
		if (mucousinstools != null) {
			return true;
		}
		if (nausea != null) {
			return true;
		}
		if (odor != null) {
			return true;
		}
		if (rectalpain != null) {
			return true;
		}
		if (texture != null) {
			return true;
		}
		if (vomiting != null) {
			return true;
		}
		return false;
	}
}
