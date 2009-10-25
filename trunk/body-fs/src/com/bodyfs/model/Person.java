/*
 * $Id$
 */
package com.bodyfs.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import com.google.appengine.api.datastore.Key;

/**
 * This class represents person in the system. This class defines all
 * nonmultable properties
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class Person {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String firstName;

	@Persistent
	private String lastName;

	@Persistent
	private String initial;

	@Persistent
	@Unique
	private String email;

	@Persistent
	private String homePhone;

	@Persistent
	private String mobilePhone;

	@Persistent
	private String workPhone;

	@Persistent
	private Gender gender;

	@Persistent
	@Unique
	private String SSN;

	@Persistent
	private String dateOfBirth;

	@Persistent
	private Key homeAddress;

	@Persistent
	private Key businessAddress;

	@Persistent
	private PersonType personType;

	@Persistent
	private boolean married;

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
	public final String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets date of birth of the person
	 * 
	 * @param dateOfBirth
	 */
	public final void setDateOfBirth(final String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * 
	 * @return primary key of address object
	 */
	public final Key getHomeAddress() {
		return homeAddress;
	}

	/**
	 * Sets primary key of the home address
	 * 
	 * @param homeAddress
	 */
	public final void setHomeAddress(final Key homeAddress) {
		this.homeAddress = homeAddress;
	}

	/**
	 * 
	 * @return primary key of the business address
	 */
	public final Key getBusinessAddress() {
		return businessAddress;
	}

	/**
	 * Sets primary key of address object for business address
	 * 
	 * @param businessAddress
	 *            key of address object
	 */
	public final void setBusinessAddress(final Key businessAddress) {
		this.businessAddress = businessAddress;
	}

	/**
	 * 
	 * @return person's type
	 */
	public final PersonType getPersonType() {
		return personType;
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Person [");
		if (SSN != null)
			builder.append("SSN=").append(SSN).append(", ");
		if (email != null)
			builder.append("email=").append(email).append(", ");
		if (firstName != null)
			builder.append("firstName=").append(firstName).append(", ");
		if (gender != null)
			builder.append("gender=").append(gender).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (lastName != null)
			builder.append("lastName=").append(lastName);
		if (initial != null)
			builder.append("initial=").append(initial);
		builder.append("]");
		return builder.toString();
	}

}
