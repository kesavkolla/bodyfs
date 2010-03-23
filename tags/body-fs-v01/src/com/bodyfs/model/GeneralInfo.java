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
	private Boolean accupunture;

	@Persistent
	private Boolean chineseherbalmedicine;

	@Persistent
	private String condition;

	@Persistent
	private Boolean gettingworse;

	@Persistent
	private Boolean botherSleep;

	@Persistent
	private Boolean botherWork;

	@Persistent
	private Boolean botherOther;

	@Persistent
	private String botherReason;

	@Persistent
	private String initialcause;

	@Persistent
	private String makeitbetter;

	@Persistent
	private String makeitworse;

	@Persistent
	private Boolean physiciancare;

	@Persistent
	private String carereason;

	@Persistent
	private String physicianname;

	@Persistent
	private String physicianphone;
	
	@Persistent
	private String diagnosisCode;

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

	public final Boolean getAccupunture() {
		return accupunture;
	}

	public final void setAccupunture(Boolean accupunture) {
		this.accupunture = accupunture;
	}

	public final Boolean getChineseherbalmedicine() {
		return chineseherbalmedicine;
	}

	public final void setChineseherbalmedicine(Boolean chineseherbalmedicine) {
		this.chineseherbalmedicine = chineseherbalmedicine;
	}

	public final String getCondition() {
		return condition;
	}

	public final void setCondition(String condition) {
		this.condition = condition;
	}

	public final Boolean getGettingworse() {
		return gettingworse;
	}

	public final void setGettingworse(Boolean gettingworse) {
		this.gettingworse = gettingworse;
	}

	public final Boolean getBotherSleep() {
		return botherSleep;
	}

	public final void setBotherSleep(Boolean botherSleep) {
		this.botherSleep = botherSleep;
	}

	public final Boolean getBotherWork() {
		return botherWork;
	}

	public final void setBotherWork(Boolean botherWork) {
		this.botherWork = botherWork;
	}

	public final Boolean getBotherOther() {
		return botherOther;
	}

	public final void setBotherOther(Boolean botherOther) {
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

	public final Boolean getPhysiciancare() {
		return physiciancare;
	}

	public final void setPhysiciancare(Boolean physiciancare) {
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
	
	

	/**
	 * @return the diagnosisCode
	 */
	public String getDiagnosisCode() {
		return diagnosisCode;
	}

	/**
	 * @param diagnosisCode the diagnosisCode to set
	 */
	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	/**
	 * This method will determine if any of the properties of this class is set
	 * or not Based on this value this object is either persisted or discarded
	 * 
	 * @return true if any one of the property is set false otherwise
	 */
	public boolean isDirty() {
		if (accupunture != null) {
			return true;
		}
		if (botherOther != null) {
			return true;
		}
		if (botherReason != null) {
			return true;
		}
		if (botherSleep != null) {
			return true;
		}
		if (botherWork != null) {
			return true;
		}
		if (carereason != null) {
			return true;
		}
		if (cellPhone != null) {
			return true;
		}
		if (chineseherbalmedicine != null) {
			return true;
		}
		if (city != null) {
			return true;
		}
		if (condition != null) {
			return true;
		}
		if (emergencycontact != null) {
			return true;
		}
		if (gettingworse != null) {
			return true;
		}
		if (height != null) {
			return true;
		}
		if (homePhone != null) {
			return true;
		}
		if (initialcause != null) {
			return true;
		}
		if (makeitbetter != null) {
			return true;
		}
		if (makeitworse != null) {
			return true;
		}
		if (othertherapies != null) {
			return true;
		}
		if (physiciancare != null) {
			return true;
		}
		if (physicianname != null) {
			return true;
		}
		if (physicianphone != null) {
			return true;
		}
		if (reasonforvisit != null) {
			return true;
		}
		if (referredBy != null) {
			return true;
		}
		if (state != null) {
			return true;
		}
		if (street != null) {
			return true;
		}
		if (weight != null) {
			return true;
		}
		if (workPhone != null) {
			return true;
		}
		if (zipcode != null) {
			return true;
		}
		return false;
	}

}
