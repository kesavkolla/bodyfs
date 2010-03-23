/* $Id$*/
package com.bodyfs.model.payments;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author Kesav Kumar Kolla
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class MasterService implements Serializable {

	private static final long serialVersionUID = 5940822307540205170L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String serviceName;

	@Persistent
	private String lowername;

	@Persistent
	private Double charge;

	@Persistent
	private boolean hidden;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
		if (serviceName != null) {
			this.lowername = serviceName.toLowerCase();
		}
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

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getLowername() {
		return lowername;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MasterService [");
		if (charge != null)
			builder.append("charge=").append(charge).append(", ");
		builder.append("hidden=").append(hidden).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (lowername != null)
			builder.append("lowername=").append(lowername).append(", ");
		if (serviceName != null)
			builder.append("serviceName=").append(serviceName);
		builder.append("]");
		return builder.toString();
	}

}
