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
import javax.jdo.annotations.Unique;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

/**
 * This class represents person in the system. This class defines all
 * nonmultable properties
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
@Searchable
public class Person implements Serializable {

	private static final long serialVersionUID = 7682118330339432960L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@SearchableId
	private Long id;

	@Persistent
	@SearchableProperty
	private String firstName;

	@Persistent
	@SearchableProperty
	private String lastName;

	@Persistent
	private String initial;

	@Persistent
	@Unique
	@SearchableProperty
	private String email;

	@Persistent
	private String homePhone;

	@Persistent
	@SearchableProperty
	private String mobilePhone;

	@Persistent
	private String workPhone;

	@Persistent
	private Gender gender;

	@Persistent
	@Unique
	private String SSN;

	@Persistent
	private Date dateOfBirth;

	@Persistent
	private PersonType personType;

	@Persistent
	private boolean married;

	@Persistent
	private Long accountNumber;

	public final Long getId() {
		return id;
	}

	public final void setId(final Long id) {
		this.id = id;
	}

	public final String getFirstName() {
		return firstName;
	}

	public final void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public final String getLastName() {
		return lastName;
	}

	public final void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public final String getInitial() {
		return initial;
	}

	public final void setInitial(final String initial) {
		this.initial = initial;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(final String email) {
		this.email = email;
	}

	public final String getHomePhone() {
		return homePhone;
	}

	public final void setHomePhone(final String homePhone) {
		this.homePhone = homePhone;
	}

	public final String getMobilePhone() {
		return mobilePhone;
	}

	public final void setMobilePhone(final String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public final String getWorkPhone() {
		return workPhone;
	}

	public final void setWorkPhone(final String workPhone) {
		this.workPhone = workPhone;
	}

	public final Gender getGender() {
		return gender;
	}

	public final void setGender(final Gender gender) {
		this.gender = gender;
	}

	public final String getSSN() {
		return SSN;
	}

	/**
	 * Sets SSN of the person
	 * 
	 * @param sSN
	 */
	public final void setSSN(final String sSN) {
		SSN = sSN;
	}

	/**
	 * 
	 * @return Date of birth
	 */
	public final Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets date of birth of the person
	 * 
	 * @param dateOfBirth
	 */
	public final void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * 
	 * @return person's type
	 */
	public final PersonType getPersonType() {
		return personType;
	}

	/**
	 * 
	 * @return person's type
	 */
	public final String getPersonTypeStr() {
		if (personType == null)
			return "";
		else if (personType.equals(PersonType.PRE_USER))
			return "pre user";
		else if (personType.equals(PersonType.POST_USER))
			return "post user";
		else if (personType.equals(PersonType.USER))
			return "current user";
		else if (personType.equals(PersonType.EMPLOYEE) || personType.equals(PersonType.SIA_AGENT))
			return "admin";

		return "";
	}

	/**
	 * Sets the person type
	 * 
	 * @param personType
	 *            person type
	 */
	public final void setPersonType(final PersonType personType) {
		this.personType = personType;
	}

	public final boolean isMarried() {
		return married;
	}

	public final void setMarried(final boolean married) {
		this.married = married;
	}

	public String getDisplayName() {
		if (this.firstName == null) {
			return this.lastName;
		}
		return this.lastName + " " + this.firstName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [");
		if (SSN != null)
			builder.append("SSN=").append(SSN).append(", ");
		if (accountNumber != null)
			builder.append("accountNumber=").append(accountNumber).append(", ");
		if (dateOfBirth != null)
			builder.append("dateOfBirth=").append(dateOfBirth).append(", ");
		if (firstName != null)
			builder.append("firstName=").append(firstName).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (initial != null)
			builder.append("initial=").append(initial).append(", ");
		if (lastName != null)
			builder.append("lastName=").append(lastName).append(", ");
		if (personType != null)
			builder.append("personType=").append(personType);
		builder.append("]");
		return builder.toString();
	}

}
