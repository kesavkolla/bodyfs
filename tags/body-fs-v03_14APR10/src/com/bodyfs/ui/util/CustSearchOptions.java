package com.bodyfs.ui.util;

import java.io.Serializable;
import java.util.Date;

public class CustSearchOptions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7148090100102182159L;
	String firstName;
	String lastName; 
	String customerType;
	String zip;
	
	String email;
	
	String ageGroup;
	
	Date trmtDateFrom;
	Date trmtDateTo;
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the ageGroup
	 */
	public String getAgeGroup() {
		return ageGroup;
	}
	/**
	 * @param ageGroup the ageGroup to set
	 */
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	/**
	 * @return the trmtDateFrom
	 */
	public Date getTrmtDateFrom() {
		return trmtDateFrom;
	}
	/**
	 * @param trmtDateFrom the trmtDateFrom to set
	 */
	public void setTrmtDateFrom(Date trmtDateFrom) {
		this.trmtDateFrom = trmtDateFrom;
	}
	/**
	 * @return the trmtDateTo
	 */
	public Date getTrmtDateTo() {
		return trmtDateTo;
	}
	/**
	 * @param trmtDateTo the trmtDateTo to set
	 */
	public void setTrmtDateTo(Date trmtDateTo) {
		this.trmtDateTo = trmtDateTo;
	}
	
	
	
			
}
