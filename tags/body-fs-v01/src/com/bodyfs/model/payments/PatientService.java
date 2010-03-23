/*$Id$*/
package com.bodyfs.model.payments;

import java.io.Serializable;
import java.util.Date;

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
public class PatientService implements Serializable {

	private static final long serialVersionUID = 6290918133813612483L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Date visitDate;

	@Persistent
	private Long personId;

	@Persistent
	private Long serviceId;

	@Persistent
	private float numServices;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public float getNumServices() {
		return numServices;
	}

	public void setNumServices(float numServices) {
		this.numServices = numServices;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PatientService [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		builder.append("numServices=").append(numServices).append(", ");
		if (personId != null)
			builder.append("personId=").append(personId).append(", ");
		if (serviceId != null)
			builder.append("serviceId=").append(serviceId).append(", ");
		if (visitDate != null)
			builder.append("visitDate=").append(visitDate);
		builder.append("]");
		return builder.toString();
	}

}
