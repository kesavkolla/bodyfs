package com.bodyfs.model.payments;

import java.io.Serializable;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class MasterServices implements Serializable{
	
	
	private static final long serialVersionUID = 5940822307540205170L;

	@PrimaryKey
	private String ServiceId;
	
	@Persistent
	private String ServiceName;
	
	@Persistent
	private Double charge;

	/**
	 * @return the serviceId
	 */
	public String getServiceId() {
		return ServiceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		ServiceId = serviceId;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return ServiceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	/**
	 * @return the charge
	 */
	public Double getCharge() {
		return charge;
	}

	/**
	 * @param charge the charge to set
	 */
	public void setCharge(Double charge) {
		this.charge = charge;
	}

	
	
}
