/* $Id$ */
package com.bodyfs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;

/**
 * 
 * @author Kesav Kumar Kolla
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PatientPrescription implements Serializable {

	private static final long serialVersionUID = -8359071924161097577L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Date visitDate = Calendar.getInstance().getTime();

	@Persistent
	private List<String> herbData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public List<String> getHerbData() {
		return herbData;
	}

	public void setHerbData(List<String> herbData) {
		this.herbData = herbData;
	}

	/**
	 * This method prepares the JSON Array format of herbdata
	 * 
	 * @return json format of the herbdata
	 */
	public String getJsonHerbData() {
		if (this.getHerbData() == null || this.getHerbData().size() <= 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (final String data : this.herbData) {
			sb.append(data).append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * This method converts the JSON array format and stores as a list of strings
	 * 
	 * @param jsonHerbData
	 */
	public void setJsonHerbData(final String jsonHerbData) {
		if (jsonHerbData == null || jsonHerbData.length() <= 0) {
			return;
		}

		// Parse the json string into json array
		final JSONParser parser = new JSONParser();
		final JSONArray arr = (JSONArray) parser.parse(jsonHerbData);
		if (arr == null || arr.size() <= 0) {
			return;
		}
		this.herbData = new ArrayList<String>(arr.size());
		for (int i = 0, len = arr.size(); i < len; i++) {
			final JSONObject obj = (JSONObject) arr.get(i);
			this.herbData.add(obj.toJSONString());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PatientPrescription [");
		if (herbData != null)
			builder.append("herbData=").append(herbData).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (personId != null)
			builder.append("personId=").append(personId).append(", ");
		if (visitDate != null)
			builder.append("visitDate=").append(visitDate);
		builder.append("]");
		return builder.toString();
	}

	public void copy(final PatientPrescription prescription) {
		prescription.setHerbData(this.getHerbData());
	}

}
