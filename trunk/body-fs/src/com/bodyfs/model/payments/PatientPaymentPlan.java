/* $Id$ */
package com.bodyfs.model.payments;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
public class PatientPaymentPlan implements Serializable {

	private static final long serialVersionUID = -3563692724476701262L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Date paymentDate = Calendar.getInstance().getTime();

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	@Persistent
	private List<String> planItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public List<String> getPlanItems() {
		return planItems;
	}

	public void setPlanItems(List<String> planItems) {
		this.planItems = planItems;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PatientPaymentPlan [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (paymentDate != null)
			builder.append("paymentDate=").append(paymentDate).append(", ");
		if (personId != null)
			builder.append("personId=").append(personId).append(", ");
		if (planItems != null)
			builder.append("planItems=").append(planItems);
		builder.append("]");
		return builder.toString();
	}

}
