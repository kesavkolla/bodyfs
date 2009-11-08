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
	private boolean coffeeTea;

	@Persistent
	private boolean drinks;

	@Persistent
	private HighLow protein;

	@Persistent
	private boolean sweeteners;

	@Persistent
	private boolean sugar;

	@Persistent
	private boolean saltyfoods;

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

	public final boolean isCoffeeTea() {
		return coffeeTea;
	}

	public final void setCoffeeTea(boolean coffeeTea) {
		this.coffeeTea = coffeeTea;
	}

	public final boolean isDrinks() {
		return drinks;
	}

	public final void setDrinks(boolean drinks) {
		this.drinks = drinks;
	}

	public final HighLow getProtein() {
		return protein;
	}

	public final void setProtein(HighLow protein) {
		this.protein = protein;
	}

	public final boolean isSweeteners() {
		return sweeteners;
	}

	public final void setSweeteners(boolean sweeteners) {
		this.sweeteners = sweeteners;
	}

	public final boolean isSugar() {
		return sugar;
	}

	public final void setSugar(boolean sugar) {
		this.sugar = sugar;
	}

	public final boolean isSaltyfoods() {
		return saltyfoods;
	}

	public final void setSaltyfoods(boolean saltyfoods) {
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
}
