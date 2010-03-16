package com.bodyfs.model.payments;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class TotalCustomerServicesBreakDown implements Serializable{
	
	
	private static final long serialVersionUID = 5940822307540205170L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;
	
	@Persistent
	private String serviceId;
	
	@Persistent
	private String serviceName;
	
	@Persistent
	private Double serviceCharge;
	
	
	@Persistent
	private Integer totalSessions;

	@Persistent
	private Integer sessionsConsumed;
	
	@Persistent
	private Double cost;

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
	 * @return the totalSessions
	 */
	public Integer getTotalSessions() {
		return totalSessions;
	}

	/**
	 * @param totalSessions the totalSessions to set
	 */
	public void setTotalSessions(Integer totalSessions) {
		this.totalSessions = totalSessions;
	}

	/**
	 * @return the sessionsConsumed
	 */
	public Integer getSessionsConsumed() {
		return sessionsConsumed;
	}

	/**
	 * @param sessionsConsumed the sessionsConsumed to set
	 */
	public void setSessionsConsumed(Integer sessionsConsumed) {
		this.sessionsConsumed = sessionsConsumed;
	}

	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(Double cost) {
		this.cost = cost;
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
	 * @return the serviceCharge
	 */
	public Double getServiceCharge() {
		return serviceCharge;
	}

	/**
	 * @param serviceCharge the serviceCharge to set
	 */
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	
	
	
	
}
