package com.bodyfs.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Address {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String street;
	@Persistent
	private String street1;
	@Persistent
	private String city;
	@Persistent
	private String state;
	@Persistent
	private String zipcode;

	public final Long getId() {
		return id;
	}

	public final void setId(final Long id) {
		this.id = id;
	}

	public final String getStreet() {
		return street;
	}

	public final void setStreet(final String street) {
		this.street = street;
	}

	public final String getStreet1() {
		return street1;
	}

	public final void setStreet1(final String street1) {
		this.street1 = street1;
	}

	public final String getCity() {
		return city;
	}

	public final void setCity(final String city) {
		this.city = city;
	}

	public final String getState() {
		return state;
	}

	public final void setState(final String state) {
		this.state = state;
	}

	public final String getZipcode() {
		return zipcode;
	}

	public final void setZipcode(final String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Address [");
		if (city != null)
			builder.append("city=").append(city).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (state != null)
			builder.append("state=").append(state).append(", ");
		if (street != null)
			builder.append("street=").append(street).append(", ");
		if (street1 != null)
			builder.append("street1=").append(street1).append(", ");
		if (zipcode != null)
			builder.append("zipcode=").append(zipcode);
		builder.append("]");
		return builder.toString();
	}

}
