/* $Id$ */
package com.bodyfs.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

/**
 * 
 * @author kesav
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PatientDiagnosis implements Serializable {

	private static final long serialVersionUID = -8000506643754364421L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Date visitDate = Calendar.getInstance().getTime();

	@Persistent
	private Text diagnosisData;

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

	public final Date getVisitDate() {
		return visitDate;
	}

	public final void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public final String getDiagnosisData() {
		if (diagnosisData == null) {
			return null;
		}
		return diagnosisData.getValue();
	}

	public final void setDiagnosisData(String diagnosisData) {
		this.diagnosisData = new Text(diagnosisData);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PatientDiagnosis [");
		if (getId() != null)
			builder.append("Id()=").append(getId()).append(", ");
		if (getPersonId() != null)
			builder.append("PersonId()=").append(getPersonId()).append(", ");
		if (getVisitDate() != null)
			builder.append("VisitDate()=").append(getVisitDate());
		if (getDiagnosisData() != null)
			builder.append("DiagnosisData()=").append(getDiagnosisData()).append(", ");
		builder.append("]");
		return builder.toString();
	}

}
