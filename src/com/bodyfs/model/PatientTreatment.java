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
public class PatientTreatment implements Serializable {

	private static final long serialVersionUID = -6480331684945676245L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Date visitDate = Calendar.getInstance().getTime();

	@Persistent
	private Text diagnosisPlan;

	@Persistent
	private Text massageNotes;

	@Persistent
	private Text additionalNotes;

	@Persistent
	private Text markers;

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

	public final String getDiagnosisPlan() {
		return diagnosisPlan == null ? null : diagnosisPlan.getValue();
	}

	public final void setDiagnosisPlan(String diagnosisPlan) {
		if (diagnosisPlan != null && (diagnosisPlan = diagnosisPlan.trim()).length() > 0) {
			this.diagnosisPlan = new Text(diagnosisPlan);
		}
	}

	public final String getMassageNotes() {
		return massageNotes == null ? null : massageNotes.getValue();
	}

	public final void setMassageNotes(String massageNotes) {
		if (massageNotes != null && (massageNotes = massageNotes.trim()).length() > 0) {
			this.massageNotes = new Text(massageNotes);
		}
	}

	public final String getAdditionalNotes() {
		return additionalNotes == null ? null : additionalNotes.getValue();
	}

	public final void setAdditionalNotes(String additionalNotes) {
		if (additionalNotes != null && (additionalNotes = additionalNotes.trim()).length() > 0) {
			this.additionalNotes = new Text(additionalNotes);
		}
	}

	public final String getMarkers() {
		return markers == null ? null : markers.getValue();
	}

	public final void setMarkers(String markers) {
		if (markers != null && (markers = markers.trim()).length() > 0) {
			this.markers = new Text(markers);
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PatientTreatment [");
		if (getAdditionalNotes() != null)
			builder.append("getAdditionalNotes()=").append(getAdditionalNotes()).append(", ");
		if (getDiagnosisPlan() != null)
			builder.append("getDiagnosisPlan()=").append(getDiagnosisPlan()).append(", ");
		if (getId() != null)
			builder.append("getId()=").append(getId()).append(", ");
		if (getMarkers() != null)
			builder.append("getMarkers()=").append(getMarkers()).append(", ");
		if (getMassageNotes() != null)
			builder.append("getMassageNotes()=").append(getMassageNotes()).append(", ");
		if (getPersonId() != null)
			builder.append("getPersonId()=").append(getPersonId()).append(", ");
		if (getVisitDate() != null)
			builder.append("getVisitDate()=").append(getVisitDate());
		builder.append("]");
		return builder.toString();
	}

}
