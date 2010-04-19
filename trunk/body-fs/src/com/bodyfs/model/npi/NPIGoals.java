/* $Id$ */
package com.bodyfs.model.npi;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

/**
 * 
 * @author Kesav Kumar Kolla
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class NPIGoals implements Serializable {

	private static final long serialVersionUID = -388657318564481569L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Date visitDate = Calendar.getInstance().getTime();

	@Persistent
	private Long personId;

	@Persistent
	private List<String> goals;

	@Persistent
	private List<String> programs;

	@Persistent
	private Text notes;

	@Persistent
	private String dueDate;

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

	public List<String> getGoals() {
		return goals;
	}

	public void setGoals(List<String> goals) {
		this.goals = goals;
	}

	public List<String> getPrograms() {
		return programs;
	}

	public void setPrograms(List<String> programs) {
		this.programs = programs;
	}

	public String getNotes() {
		return notes == null ? null : notes.getValue();
	}

	public void setNotes(final String notes) {
		this.notes = notes == null ? null : new Text(notes);
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NPIGoals [");
		if (dueDate != null)
			builder.append("dueDate=").append(dueDate).append(", ");
		if (goals != null)
			builder.append("goals=").append(goals).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (notes != null)
			builder.append("notes=").append(notes).append(", ");
		if (personId != null)
			builder.append("personId=").append(personId).append(", ");
		if (programs != null)
			builder.append("programs=").append(programs).append(", ");
		if (visitDate != null)
			builder.append("visitDate=").append(visitDate);
		builder.append("]");
		return builder.toString();
	}
}
