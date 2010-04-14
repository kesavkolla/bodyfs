package com.bodyfs.model.payments;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class PaymentBreakDown implements Serializable{
	
	
	private static final long serialVersionUID = 5940822307540205170L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long paymentId;
	
	@Persistent
	private String serviceId;
	
	@Persistent
	private String serviceName;
	
	@Persistent
	private Integer perWeek = 0;

	@Persistent
	private Integer duration = 0;
	
	private Double Cost;

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
	 * @return the paymentId
	 */
	public Long getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
		
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	/**
	 * @return the perWeek
	 */
	public Integer getPerWeek() {
		return perWeek;
	}

	/**
	 * @param perWeek the perWeek to set
	 */
	public void setPerWeek(Integer perWeek) {
		this.perWeek = perWeek;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the cost
	 */
	public Double getCost() {
		return Cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(Double cost) {
		Cost = cost;
	}
	
	

	
}
