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
public class Diet implements Serializable {

	private static final long serialVersionUID = -9170368365890936375L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private HighLow appetite;

	@Persistent
	private Boolean coffeeTea;

	@Persistent
	private Boolean drinks;

	@Persistent
	private HighLow protein;

	@Persistent
	private Boolean sweeteners;

	@Persistent
	private Boolean sugar;

	@Persistent
	private Boolean saltyfoods;

	@Persistent
	private String water;

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

	public final HighLow getAppetite() {
		return appetite;
	}

	public final void setAppetite(HighLow appetite) {
		this.appetite = appetite;
	}

	public final Boolean isCoffeeTea() {
		return coffeeTea;
	}

	public final void setCoffeeTea(Boolean coffeeTea) {
		this.coffeeTea = coffeeTea;
	}

	public final Boolean isDrinks() {
		return drinks;
	}

	public final void setDrinks(Boolean drinks) {
		this.drinks = drinks;
	}

	public final HighLow getProtein() {
		return protein;
	}

	public final void setProtein(HighLow protein) {
		this.protein = protein;
	}

	public final Boolean isSweeteners() {
		return sweeteners;
	}

	public final void setSweeteners(Boolean sweeteners) {
		this.sweeteners = sweeteners;
	}

	public final Boolean isSugar() {
		return sugar;
	}

	public final void setSugar(Boolean sugar) {
		this.sugar = sugar;
	}

	public final Boolean isSaltyfoods() {
		return saltyfoods;
	}

	public final void setSaltyfoods(Boolean saltyfoods) {
		this.saltyfoods = saltyfoods;
	}

	public final String getWater() {
		return water;
	}

	public final void setWater(final String water) {
		this.water = water;
	}

	public final Date getCreateDate() {
		return createDate;
	}

	public final void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * This method will determine if any of the properties of this class is set
	 * or not Based on this value this object is either persisted or discarded
	 * 
	 * @return true if any one of the property is set false otherwise
	 */
	public boolean isDirty() {
		if (appetite != null) {
			return true;
		}
		if (coffeeTea != null) {
			return true;
		}
		if (createDate != null) {
			return true;
		}
		if (drinks != null) {
			return true;
		}
		if (protein != null) {
			return true;
		}
		if (saltyfoods != null) {
			return true;
		}
		if (sugar != null) {
			return true;
		}
		if (sweeteners != null) {
			return true;
		}
		if (water != null) {
			return true;
		}
		return false;
	}

}
