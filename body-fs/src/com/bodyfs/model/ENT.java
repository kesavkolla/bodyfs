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
	private boolean glasses;

	@Persistent
	private String age;

	@Persistent
	private boolean eyestrain;

	@Persistent
	private boolean eyepain;

	@Persistent
	private boolean redeyes;

	@Persistent
	private boolean itchyeyes;

	@Persistent
	private boolean spotsineyes;

	@Persistent
	private boolean poorvision;

	@Persistent
	private boolean blurredvision;

	@Persistent
	private boolean nightblindness;

	@Persistent
	private boolean myopiaorPresbyopia;

	@Persistent
	private boolean glaucoma;

	@Persistent
	private boolean cataracts;

	@Persistent
	private boolean teethproblems;

	@Persistent
	private boolean grindingteeth;

	@Persistent
	private boolean tmj;

	@Persistent
	private boolean facialpain;

	@Persistent
	private boolean gumproblems;

	@Persistent
	private boolean sores;

	@Persistent
	private boolean drymouth;

	@Persistent
	private boolean excessivesaliva;

	@Persistent
	private boolean sinusproblems;

	@Persistent
	private boolean phlegmColor;

	@Persistent
	private boolean recurrentsorethroat;

	@Persistent
	private boolean swollenglands;

	@Persistent
	private boolean lumpsinthroat;

	@Persistent
	private boolean enlargedthyroid;

	@Persistent
	private boolean nosebleeds;

	@Persistent
	private boolean ringinginears;

	@Persistent
	private HighLow ringears;

	@Persistent
	private boolean poorhearing;

	@Persistent
	private boolean earaches;

	@Persistent
	private boolean headaches;

	@Persistent
	private boolean migraines;

	@Persistent
	private boolean concussions;

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

	public final boolean isGlasses() {
		return glasses;
	}

	public final void setGlasses(boolean glasses) {
		this.glasses = glasses;
	}

	public final String getAge() {
		return age;
	}

	public final void setAge(String age) {
		this.age = age;
	}

	public final boolean isEyestrain() {
		return eyestrain;
	}

	public final void setEyestrain(boolean eyestrain) {
		this.eyestrain = eyestrain;
	}

	public final boolean isEyepain() {
		return eyepain;
	}

	public final void setEyepain(boolean eyepain) {
		this.eyepain = eyepain;
	}

	public final boolean isRedeyes() {
		return redeyes;
	}

	public final void setRedeyes(boolean redeyes) {
		this.redeyes = redeyes;
	}

	public final boolean isItchyeyes() {
		return itchyeyes;
	}

	public final void setItchyeyes(boolean itchyeyes) {
		this.itchyeyes = itchyeyes;
	}

	public final boolean isSpotsineyes() {
		return spotsineyes;
	}

	public final void setSpotsineyes(boolean spotsineyes) {
		this.spotsineyes = spotsineyes;
	}

	public final boolean isPoorvision() {
		return poorvision;
	}

	public final void setPoorvision(boolean poorvision) {
		this.poorvision = poorvision;
	}

	public final boolean isBlurredvision() {
		return blurredvision;
	}

	public final void setBlurredvision(boolean blurredvision) {
		this.blurredvision = blurredvision;
	}

	public final boolean isNightblindness() {
		return nightblindness;
	}

	public final void setNightblindness(boolean nightblindness) {
		this.nightblindness = nightblindness;
	}

	public final boolean isMyopiaorPresbyopia() {
		return myopiaorPresbyopia;
	}

	public final void setMyopiaorPresbyopia(boolean myopiaorPresbyopia) {
		this.myopiaorPresbyopia = myopiaorPresbyopia;
	}

	public final boolean isGlaucoma() {
		return glaucoma;
	}

	public final void setGlaucoma(boolean glaucoma) {
		this.glaucoma = glaucoma;
	}

	public final boolean isCataracts() {
		return cataracts;
	}

	public final void setCataracts(boolean cataracts) {
		this.cataracts = cataracts;
	}

	public final boolean isTeethproblems() {
		return teethproblems;
	}

	public final void setTeethproblems(boolean teethproblems) {
		this.teethproblems = teethproblems;
	}

	public final boolean isGrindingteeth() {
		return grindingteeth;
	}

	public final void setGrindingteeth(boolean grindingteeth) {
		this.grindingteeth = grindingteeth;
	}

	public final boolean isTmj() {
		return tmj;
	}

	public final void setTmj(boolean tmj) {
		this.tmj = tmj;
	}

	public final boolean isFacialpain() {
		return facialpain;
	}

	public final void setFacialpain(boolean facialpain) {
		this.facialpain = facialpain;
	}

	public final boolean isGumproblems() {
		return gumproblems;
	}

	public final void setGumproblems(boolean gumproblems) {
		this.gumproblems = gumproblems;
	}

	public final boolean isSores() {
		return sores;
	}

	public final void setSores(boolean sores) {
		this.sores = sores;
	}

	public final boolean isDrymouth() {
		return drymouth;
	}

	public final void setDrymouth(boolean drymouth) {
		this.drymouth = drymouth;
	}

	public final boolean isExcessivesaliva() {
		return excessivesaliva;
	}

	public final void setExcessivesaliva(boolean excessivesaliva) {
		this.excessivesaliva = excessivesaliva;
	}

	public final boolean isSinusproblems() {
		return sinusproblems;
	}

	public final void setSinusproblems(boolean sinusproblems) {
		this.sinusproblems = sinusproblems;
	}

	public final boolean isPhlegmColor() {
		return phlegmColor;
	}

	public final void setPhlegmColor(boolean phlegmColor) {
		this.phlegmColor = phlegmColor;
	}

	public final boolean isRecurrentsorethroat() {
		return recurrentsorethroat;
	}

	public final void setRecurrentsorethroat(boolean recurrentsorethroat) {
		this.recurrentsorethroat = recurrentsorethroat;
	}

	public final boolean isSwollenglands() {
		return swollenglands;
	}

	public final void setSwollenglands(boolean swollenglands) {
		this.swollenglands = swollenglands;
	}

	public final boolean isLumpsinthroat() {
		return lumpsinthroat;
	}

	public final void setLumpsinthroat(boolean lumpsinthroat) {
		this.lumpsinthroat = lumpsinthroat;
	}

	public final boolean isEnlargedthyroid() {
		return enlargedthyroid;
	}

	public final void setEnlargedthyroid(boolean enlargedthyroid) {
		this.enlargedthyroid = enlargedthyroid;
	}

	public final boolean isNosebleeds() {
		return nosebleeds;
	}

	public final void setNosebleeds(boolean nosebleeds) {
		this.nosebleeds = nosebleeds;
	}

	public final boolean isRinginginears() {
		return ringinginears;
	}

	public final void setRinginginears(boolean ringinginears) {
		this.ringinginears = ringinginears;
	}

	public final HighLow getRingears() {
		return ringears;
	}

	public final void setRingears(HighLow ringears) {
		this.ringears = ringears;
	}

	public final boolean isPoorhearing() {
		return poorhearing;
	}

	public final void setPoorhearing(boolean poorhearing) {
		this.poorhearing = poorhearing;
	}

	public final boolean isEaraches() {
		return earaches;
	}

	public final void setEaraches(boolean earaches) {
		this.earaches = earaches;
	}

	public final boolean isHeadaches() {
		return headaches;
	}

	public final void setHeadaches(boolean headaches) {
		this.headaches = headaches;
	}

	public final boolean isMigraines() {
		return migraines;
	}

	public final void setMigraines(boolean migraines) {
		this.migraines = migraines;
	}

	public final boolean isConcussions() {
		return concussions;
	}

	public final void setConcussions(boolean concussions) {
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

}
