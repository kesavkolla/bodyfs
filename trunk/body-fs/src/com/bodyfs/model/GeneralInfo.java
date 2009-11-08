/*
 * $Id$
 */
package com.bodyfs.model;

import java.io.Serializable;

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
public class GeneralInfo implements Serializable {

	private static final long serialVersionUID = 5632674877748703059L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private String street;

	@Persistent
	private String city;

	@Persistent
	private String state;

	@Persistent
	private String zipcode;

	@Persistent
	private String height;

	@Persistent
	private String weight;

	@Persistent
	private String emergencycontact;

	@Persistent
	private String homePhone;

	@Persistent
	private String workPhone;

	@Persistent
	private String cellPhone;

	@Persistent
	private String referredBy;

	@Persistent
	private String reasonforvisit;

	@Persistent
	private boolean accupunture;

	@Persistent
	private boolean chineseherbalmedicine;

	@Persistent
	private String condition;

	@Persistent
	private boolean gettingworse;

	@Persistent
	private boolean botherSleep;

	@Persistent
	private boolean botherWork;

	@Persistent
	private boolean botherOther;

	@Persistent
	private String botherReason;

	@Persistent
	private String initialcause;

	@Persistent
	private String makeitbetter;

	@Persistent
	private String makeitworse;

	@Persistent
	private boolean physiciancare;

	@Persistent
	private String carereason;

	@Persistent
	private String physicianname;

	@Persistent
	private String physicianphone;

	@Persistent
	private String othertherapies;

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

	public final String getStreet() {
		return street;
	}

	public final void setStreet(String street) {
		this.street = street;
	}

	public final String getCity() {
		return city;
	}

	public final void setCity(String city) {
		this.city = city;
	}

	public final String getState() {
		return state;
	}

	public final void setState(String state) {
		this.state = state;
	}

	public final String getZipcode() {
		return zipcode;
	}

	public final void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public final String getHeight() {
		return height;
	}

	public final void setHeight(String height) {
		this.height = height;
	}

	public final String getWeight() {
		return weight;
	}

	public final void setWeight(String weight) {
		this.weight = weight;
	}

	public final String getEmergencycontact() {
		return emergencycontact;
	}

	public final void setEmergencycontact(String emergencycontact) {
		this.emergencycontact = emergencycontact;
	}

	public final String getHomePhone() {
		return homePhone;
	}

	public final void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public final String getWorkPhone() {
		return workPhone;
	}

	public final void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public final String getCellPhone() {
		return cellPhone;
	}

	public final void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public final String getReferredBy() {
		return referredBy;
	}

	public final void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public final String getReasonforvisit() {
		return reasonforvisit;
	}

	public final void setReasonforvisit(String reasonforvisit) {
		this.reasonforvisit = reasonforvisit;
	}

	public final boolean isAccupunture() {
		return accupunture;
	}

	public final void setAccupunture(boolean accupunture) {
		this.accupunture = accupunture;
	}

	public final boolean isChineseherbalmedicine() {
		return chineseherbalmedicine;
	}

	public final void setChineseherbalmedicine(boolean chineseherbalmedicine) {
		this.chineseherbalmedicine = chineseherbalmedicine;
	}

	public final String getCondition() {
		return condition;
	}

	public final void setCondition(String condition) {
		this.condition = condition;
	}

	public final boolean isGettingworse() {
		return gettingworse;
	}

	public final void setGettingworse(boolean gettingworse) {
		this.gettingworse = gettingworse;
	}

	public final boolean isBotherSleep() {
		return botherSleep;
	}

	public final void setBotherSleep(boolean botherSleep) {
		this.botherSleep = botherSleep;
	}

	public final boolean isBotherWork() {
		return botherWork;
	}

	public final void setBotherWork(boolean botherWork) {
		this.botherWork = botherWork;
	}

	public final boolean isBotherOther() {
		return botherOther;
	}

	public final void setBotherOther(boolean botherOther) {
		this.botherOther = botherOther;
	}

	public final String getBotherReason() {
		return botherReason;
	}

	public final void setBotherReason(String botherReason) {
		this.botherReason = botherReason;
	}

	public final String getInitialcause() {
		return initialcause;
	}

	public final void setInitialcause(String initialcause) {
		this.initialcause = initialcause;
	}

	public final String getMakeitbetter() {
		return makeitbetter;
	}

	public final void setMakeitbetter(String makeitbetter) {
		this.makeitbetter = makeitbetter;
	}

	public final String getMakeitworse() {
		return makeitworse;
	}

	public final void setMakeitworse(String makeitworse) {
		this.makeitworse = makeitworse;
	}

	public final boolean isPhysiciancare() {
		return physiciancare;
	}

	public final void setPhysiciancare(boolean physiciancare) {
		this.physiciancare = physiciancare;
	}

	public final String getCarereason() {
		return carereason;
	}

	public final void setCarereason(String carereason) {
		this.carereason = carereason;
	}

	public final String getPhysicianname() {
		return physicianname;
	}

	public final void setPhysicianname(String physicianname) {
		this.physicianname = physicianname;
	}

	public final String getPhysicianphone() {
		return physicianphone;
	}

	public final void setPhysicianphone(String physicianphone) {
		this.physicianphone = physicianphone;
	}

	public final String getOthertherapies() {
		return othertherapies;
	}

	public final void setOthertherapies(String othertherapies) {
		this.othertherapies = othertherapies;
	}
}
