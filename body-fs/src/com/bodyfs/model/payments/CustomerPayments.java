package com.bodyfs.model.payments;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class CustomerPayments implements Serializable{
	
	
	private static final long serialVersionUID = 5940822307540205170L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Integer totalCuping;
	
	@Persistent
	private Integer cupingConsumed;
	
	@Persistent
	private Integer totalMassage;
	
	@Persistent
	private Integer massageConsumed;
	
	@Persistent
	private Integer totalAcupuncture;
	
	@Persistent
	private Integer acupunctureConsumed;
	
	@Persistent
	private Integer totalHerbal;
	
	@Persistent
	private Integer herbalConsumed;
	
	@Persistent
	private Double discount;
	

	@Persistent
	private Double total;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}


	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}


	/**
	 * @return the totalCuping
	 */
	public Integer getTotalCuping() {
		return totalCuping;
	}


	/**
	 * @param totalCuping the totalCuping to set
	 */
	public void setTotalCuping(Integer totalCuping) {
		this.totalCuping = totalCuping;
	}


	/**
	 * @return the cupingConsumed
	 */
	public Integer getCupingConsumed() {
		return cupingConsumed;
	}


	/**
	 * @param cupingConsumed the cupingConsumed to set
	 */
	public void setCupingConsumed(Integer cupingConsumed) {
		this.cupingConsumed = cupingConsumed;
	}


	/**
	 * @return the totalMassage
	 */
	public Integer getTotalMassage() {
		return totalMassage;
	}


	/**
	 * @param totalMassage the totalMassage to set
	 */
	public void setTotalMassage(Integer totalMassage) {
		this.totalMassage = totalMassage;
	}


	/**
	 * @return the massageConsumed
	 */
	public Integer getMassageConsumed() {
		return massageConsumed;
	}


	/**
	 * @param massageConsumed the massageConsumed to set
	 */
	public void setMassageConsumed(Integer massageConsumed) {
		this.massageConsumed = massageConsumed;
	}


	/**
	 * @return the totalAcupuncture
	 */
	public Integer getTotalAcupuncture() {
		return totalAcupuncture;
	}


	/**
	 * @param totalAcupuncture the totalAcupuncture to set
	 */
	public void setTotalAcupuncture(Integer totalAcupuncture) {
		this.totalAcupuncture = totalAcupuncture;
	}


	/**
	 * @return the acupunctureConsumed
	 */
	public Integer getAcupunctureConsumed() {
		return acupunctureConsumed;
	}


	/**
	 * @param acupunctureConsumed the acupunctureConsumed to set
	 */
	public void setAcupunctureConsumed(Integer acupunctureConsumed) {
		this.acupunctureConsumed = acupunctureConsumed;
	}


	/**
	 * @return the totalHerbal
	 */
	public Integer getTotalHerbal() {
		return totalHerbal;
	}


	/**
	 * @param totalHerbal the totalHerbal to set
	 */
	public void setTotalHerbal(Integer totalHerbal) {
		this.totalHerbal = totalHerbal;
	}


	/**
	 * @return the herbalConsumed
	 */
	public Integer getHerbalConsumed() {
		return herbalConsumed;
	}


	/**
	 * @param herbalConsumed the herbalConsumed to set
	 */
	public void setHerbalConsumed(Integer herbalConsumed) {
		this.herbalConsumed = herbalConsumed;
	}


	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}


	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}


	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	
	
	
}
