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
public class ENT implements Serializable {

	private static final long serialVersionUID = -838285376712313951L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Boolean glasses;

	@Persistent
	private String age;

	@Persistent
	private Boolean eyestrain;

	@Persistent
	private Boolean eyepain;

	@Persistent
	private Boolean redeyes;

	@Persistent
	private Boolean itchyeyes;

	@Persistent
	private Boolean spotsineyes;

	@Persistent
	private Boolean poorvision;

	@Persistent
	private Boolean blurredvision;

	@Persistent
	private Boolean nightblindness;

	@Persistent
	private Boolean myopiaorPresbyopia;

	@Persistent
	private Boolean glaucoma;

	@Persistent
	private Boolean cataracts;

	@Persistent
	private Boolean teethproblems;

	@Persistent
	private Boolean grindingteeth;

	@Persistent
	private Boolean tmj;

	@Persistent
	private Boolean facialpain;

	@Persistent
	private Boolean gumproblems;

	@Persistent
	private Boolean sores;

	@Persistent
	private Boolean drymouth;

	@Persistent
	private Boolean excessivesaliva;

	@Persistent
	private Boolean sinusproblems;

	@Persistent
	private Boolean phlegmColor;

	@Persistent
	private Boolean recurrentsorethroat;

	@Persistent
	private Boolean swollenglands;

	@Persistent
	private Boolean lumpsinthroat;

	@Persistent
	private Boolean enlargedthyroid;

	@Persistent
	private Boolean nosebleeds;

	@Persistent
	private Boolean ringinginears;

	@Persistent
	private HighLow ringears;

	@Persistent
	private Boolean poorhearing;

	@Persistent
	private Boolean earaches;

	@Persistent
	private Boolean headaches;

	@Persistent
	private Boolean migraines;

	@Persistent
	private Boolean concussions;

	@Persistent
	private String Other;

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

	public final Boolean getGlasses() {
		return glasses;
	}

	public final void setGlasses(Boolean glasses) {
		this.glasses = glasses;
	}

	public final String getAge() {
		return age;
	}

	public final void setAge(String age) {
		this.age = age;
	}

	public final Boolean getEyestrain() {
		return eyestrain;
	}

	public final void setEyestrain(Boolean eyestrain) {
		this.eyestrain = eyestrain;
	}

	public final Boolean getEyepain() {
		return eyepain;
	}

	public final void setEyepain(Boolean eyepain) {
		this.eyepain = eyepain;
	}

	public final Boolean getRedeyes() {
		return redeyes;
	}

	public final void setRedeyes(Boolean redeyes) {
		this.redeyes = redeyes;
	}

	public final Boolean getItchyeyes() {
		return itchyeyes;
	}

	public final void setItchyeyes(Boolean itchyeyes) {
		this.itchyeyes = itchyeyes;
	}

	public final Boolean getSpotsineyes() {
		return spotsineyes;
	}

	public final void setSpotsineyes(Boolean spotsineyes) {
		this.spotsineyes = spotsineyes;
	}

	public final Boolean getPoorvision() {
		return poorvision;
	}

	public final void setPoorvision(Boolean poorvision) {
		this.poorvision = poorvision;
	}

	public final Boolean getBlurredvision() {
		return blurredvision;
	}

	public final void setBlurredvision(Boolean blurredvision) {
		this.blurredvision = blurredvision;
	}

	public final Boolean getNightblindness() {
		return nightblindness;
	}

	public final void setNightblindness(Boolean nightblindness) {
		this.nightblindness = nightblindness;
	}

	public final Boolean getMyopiaorPresbyopia() {
		return myopiaorPresbyopia;
	}

	public final void setMyopiaorPresbyopia(Boolean myopiaorPresbyopia) {
		this.myopiaorPresbyopia = myopiaorPresbyopia;
	}

	public final Boolean getGlaucoma() {
		return glaucoma;
	}

	public final void setGlaucoma(Boolean glaucoma) {
		this.glaucoma = glaucoma;
	}

	public final Boolean getCataracts() {
		return cataracts;
	}

	public final void setCataracts(Boolean cataracts) {
		this.cataracts = cataracts;
	}

	public final Boolean getTeethproblems() {
		return teethproblems;
	}

	public final void setTeethproblems(Boolean teethproblems) {
		this.teethproblems = teethproblems;
	}

	public final Boolean getGrindingteeth() {
		return grindingteeth;
	}

	public final void setGrindingteeth(Boolean grindingteeth) {
		this.grindingteeth = grindingteeth;
	}

	public final Boolean getTmj() {
		return tmj;
	}

	public final void setTmj(Boolean tmj) {
		this.tmj = tmj;
	}

	public final Boolean getFacialpain() {
		return facialpain;
	}

	public final void setFacialpain(Boolean facialpain) {
		this.facialpain = facialpain;
	}

	public final Boolean getGumproblems() {
		return gumproblems;
	}

	public final void setGumproblems(Boolean gumproblems) {
		this.gumproblems = gumproblems;
	}

	public final Boolean getSores() {
		return sores;
	}

	public final void setSores(Boolean sores) {
		this.sores = sores;
	}

	public final Boolean getDrymouth() {
		return drymouth;
	}

	public final void setDrymouth(Boolean drymouth) {
		this.drymouth = drymouth;
	}

	public final Boolean getExcessivesaliva() {
		return excessivesaliva;
	}

	public final void setExcessivesaliva(Boolean excessivesaliva) {
		this.excessivesaliva = excessivesaliva;
	}

	public final Boolean getSinusproblems() {
		return sinusproblems;
	}

	public final void setSinusproblems(Boolean sinusproblems) {
		this.sinusproblems = sinusproblems;
	}

	public final Boolean getPhlegmColor() {
		return phlegmColor;
	}

	public final void setPhlegmColor(Boolean phlegmColor) {
		this.phlegmColor = phlegmColor;
	}

	public final Boolean getRecurrentsorethroat() {
		return recurrentsorethroat;
	}

	public final void setRecurrentsorethroat(Boolean recurrentsorethroat) {
		this.recurrentsorethroat = recurrentsorethroat;
	}

	public final Boolean getSwollenglands() {
		return swollenglands;
	}

	public final void setSwollenglands(Boolean swollenglands) {
		this.swollenglands = swollenglands;
	}

	public final Boolean getLumpsinthroat() {
		return lumpsinthroat;
	}

	public final void setLumpsinthroat(Boolean lumpsinthroat) {
		this.lumpsinthroat = lumpsinthroat;
	}

	public final Boolean getEnlargedthyroid() {
		return enlargedthyroid;
	}

	public final void setEnlargedthyroid(Boolean enlargedthyroid) {
		this.enlargedthyroid = enlargedthyroid;
	}

	public final Boolean getNosebleeds() {
		return nosebleeds;
	}

	public final void setNosebleeds(Boolean nosebleeds) {
		this.nosebleeds = nosebleeds;
	}

	public final Boolean getRinginginears() {
		return ringinginears;
	}

	public final void setRinginginears(Boolean ringinginears) {
		this.ringinginears = ringinginears;
	}

	public final HighLow getRingears() {
		return ringears;
	}

	public final void setRingears(HighLow ringears) {
		this.ringears = ringears;
	}

	public final Boolean getPoorhearing() {
		return poorhearing;
	}

	public final void setPoorhearing(Boolean poorhearing) {
		this.poorhearing = poorhearing;
	}

	public final Boolean getEaraches() {
		return earaches;
	}

	public final void setEaraches(Boolean earaches) {
		this.earaches = earaches;
	}

	public final Boolean getHeadaches() {
		return headaches;
	}

	public final void setHeadaches(Boolean headaches) {
		this.headaches = headaches;
	}

	public final Boolean getMigraines() {
		return migraines;
	}

	public final void setMigraines(Boolean migraines) {
		this.migraines = migraines;
	}

	public final Boolean getConcussions() {
		return concussions;
	}

	public final void setConcussions(Boolean concussions) {
		this.concussions = concussions;
	}

	public final String getOther() {
		return Other;
	}

	public final void setOther(String other) {
		Other = other;
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
		if (age != null) {
			return true;
		}
		if (blurredvision != null) {
			return true;
		}
		if (cataracts != null) {
			return true;
		}
		if (concussions != null) {
			return true;
		}
		if (createDate != null) {
			return true;
		}
		if (drymouth != null) {
			return true;
		}
		if (earaches != null) {
			return true;
		}
		if (enlargedthyroid != null) {
			return true;
		}
		if (excessivesaliva != null) {
			return true;
		}
		if (eyepain != null) {
			return true;
		}
		if (eyestrain != null) {
			return true;
		}
		if (facialpain != null) {
			return true;
		}
		if (glasses != null) {
			return true;
		}
		if (glaucoma != null) {
			return true;
		}
		if (grindingteeth != null) {
			return true;
		}
		if (gumproblems != null) {
			return true;
		}
		if (headaches != null) {
			return true;
		}
		if (itchyeyes != null) {
			return true;
		}
		if (lumpsinthroat != null) {
			return true;
		}
		if (migraines != null) {
			return true;
		}
		if (myopiaorPresbyopia != null) {
			return true;
		}
		if (nightblindness != null) {
			return true;
		}
		if (nosebleeds != null) {
			return true;
		}
		if (phlegmColor != null) {
			return true;
		}
		if (poorhearing != null) {
			return true;
		}
		if (poorvision != null) {
			return true;
		}
		if (recurrentsorethroat != null) {
			return true;
		}
		if (redeyes != null) {
			return true;
		}
		if (ringears != null) {
			return true;
		}
		if (ringinginears != null) {
			return true;
		}
		if (sinusproblems != null) {
			return true;
		}
		if (sores != null) {
			return true;
		}
		if (spotsineyes != null) {
			return true;
		}
		if (swollenglands != null) {
			return true;
		}
		if (teethproblems != null) {
			return true;
		}
		if (tmj != null) {
			return true;
		}
		return false;
	}

}
