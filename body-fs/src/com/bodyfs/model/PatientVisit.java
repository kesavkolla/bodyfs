/*
 * $Id$
 */
package com.bodyfs.model;

import java.io.Serializable;
import java.util.Calendar;
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
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PatientVisit implements Serializable {

	public enum BowlMovement {
		NORMAL, INCOMPLETE, LOOSE, BLOOD, DRY, DIARRHEA, MUCOUS, DIFFICULT
	}

	public enum Digestion {
		BLOATING, GAS, BLEACHING, ACIDREFLUX, NAUSEA, PAIN
	}

	public enum Urination {
		NORMAL, DARK, CLEAR, URGENT, FREQUENT, DIFFICULT, BURNING
	}

	public enum QualityOfSleep {
		GOOD, BAD, WAKEUP, DIFFICULT, FREQUENT
	}

	public enum Feeling {
		BETTER, SAME, WORSE
	}

	public enum Treatment {
		ACUP, MSG15, HBCONS, MSG30, RE
	}

	private static final long serialVersionUID = -7551325937518296136L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private String healthGoals;

	@Persistent
	private int numOfHearbBags;

	@Persistent
	private int energyLevel = -1;

	@Persistent
	private Digestion digestion;

	@Persistent
	private HighLow appetiteLevel;

	@Persistent
	private BowlMovement bowlMovement;

	@Persistent
	private int bowlTimes;

	@Persistent
	private Urination urine;

	@Persistent
	private String urineTimes;

	@Persistent
	private QualityOfSleep sleep;

	@Persistent
	private String question;

	@Persistent
	private String betterService;

	@Persistent
	private Feeling feeling;

	@Persistent
	private String pulse;

	@Persistent
	private String subjective;

	@Persistent
	private String tounge;

	@Persistent
	private String plan;

	@Persistent
	private Treatment treatment;

	@Persistent
	private String herbs;

	@Persistent
	private String herb;

	@Persistent
	private String bags;

	@Persistent
	private String pills;

	@Persistent
	private String powder;

	@Persistent
	private String capsules;

	@Persistent
	private String othersign;

	@Persistent
	private Date visitDate = Calendar.getInstance().getTime();

	public final Long getId() {
		return id;
	}

	public final void setId(final Long id) {
		this.id = id;
	}

	public final Long getPersonId() {
		return personId;
	}

	public final void setPersonId(final Long personId) {
		this.personId = personId;
	}

	public final String getHealthGoals() {
		return healthGoals;
	}

	public final void setHealthGoals(final String healthGoals) {
		this.healthGoals = healthGoals;
	}

	public final int getNumOfHearbBags() {
		return numOfHearbBags;
	}

	public final void setNumOfHearbBags(final int numOfHearbBags) {
		this.numOfHearbBags = numOfHearbBags;
	}

	public final int getEnergyLevel() {
		return energyLevel;
	}

	public final void setEnergyLevel(final int energyLevel) {
		this.energyLevel = energyLevel;
	}

	public final Digestion getDigestion() {
		return digestion;
	}

	public final void setDigestion(final Digestion digestion) {
		this.digestion = digestion;
	}

	public final HighLow getAppetiteLevel() {
		return appetiteLevel;
	}

	public final void setAppetiteLevel(final HighLow appetiteLevel) {
		this.appetiteLevel = appetiteLevel;
	}

	public final BowlMovement getBowlMovement() {
		return bowlMovement;
	}

	public final void setBowlMovement(final BowlMovement bowlMovement) {
		this.bowlMovement = bowlMovement;
	}

	public final int getBowlTimes() {
		return bowlTimes;
	}

	public final void setBowlTimes(final int bowlTimes) {
		this.bowlTimes = bowlTimes;
	}

	public final Urination getUrine() {
		return urine;
	}

	public final void setUrine(final Urination urine) {
		this.urine = urine;
	}

	public final String getUrineTimes() {
		return urineTimes;
	}

	public final void setUrineTimes(final String urineTimes) {
		this.urineTimes = urineTimes;
	}

	public final QualityOfSleep getSleep() {
		return sleep;
	}

	public final void setSleep(final QualityOfSleep sleep) {
		this.sleep = sleep;
	}

	public final String getQuestion() {
		return question;
	}

	public final void setQuestion(final String question) {
		this.question = question;
	}

	public final String getBetterService() {
		return betterService;
	}

	public final void setBetterService(final String betterService) {
		this.betterService = betterService;
	}

	public final Feeling getFeeling() {
		return feeling;
	}

	public final void setFeeling(final Feeling feeling) {
		this.feeling = feeling;
	}

	public final String getPulse() {
		return pulse;
	}

	public final void setPulse(final String pulse) {
		this.pulse = pulse;
	}

	public final String getSubjective() {
		return subjective;
	}

	public final void setSubjective(final String subjective) {
		this.subjective = subjective;
	}

	public final String getTounge() {
		return tounge;
	}

	public final void setTounge(final String tounge) {
		this.tounge = tounge;
	}

	public final String getPlan() {
		return plan;
	}

	public final void setPlan(final String plan) {
		this.plan = plan;
	}

	public final Treatment getTreatment() {
		return treatment;
	}

	public final void setTreatment(final Treatment treatment) {
		this.treatment = treatment;
	}

	public final String getHerbs() {
		return herbs;
	}

	public final void setHerbs(final String herbs) {
		this.herbs = herbs;
	}

	public final String getHerb() {
		return herb;
	}

	public final void setHerb(final String herb) {
		this.herb = herb;
	}

	public final String getBags() {
		return bags;
	}

	public final void setBags(final String bags) {
		this.bags = bags;
	}

	public final String getPills() {
		return pills;
	}

	public final void setPills(final String pills) {
		this.pills = pills;
	}

	public final String getPowder() {
		return powder;
	}

	public final void setPowder(final String powder) {
		this.powder = powder;
	}

	public final String getCapsules() {
		return capsules;
	}

	public final void setCapsules(final String capsules) {
		this.capsules = capsules;
	}

	public final String getOthersign() {
		return othersign;
	}

	public final void setOthersign(final String othersign) {
		this.othersign = othersign;
	}

	public final Date getVisitDate() {
		return visitDate;
	}

	public final void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
}
