/* $Id$ */
package com.bodyfs.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author kesav
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MPIData implements Serializable {

	private static final long serialVersionUID = 9083510015091587560L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long personId;

	@Persistent
	private Date examDate;

	@Persistent
	private float LU1;

	@Persistent
	private float LU2;

	@Persistent
	private float LU3;

	@Persistent
	private float P1;

	@Persistent
	private float P2;

	@Persistent
	private float P3;

	@Persistent
	private float HT1;

	@Persistent
	private float HT2;

	@Persistent
	private float HT3;

	@Persistent
	private float SI1;

	@Persistent
	private float SI2;

	@Persistent
	private float SI3;

	@Persistent
	private float TH1;

	@Persistent
	private float TH2;

	@Persistent
	private float TH3;

	@Persistent
	private float LT1;

	@Persistent
	private float LT2;

	@Persistent
	private float LT3;

	@Persistent
	private float SP1;

	@Persistent
	private float SP2;

	@Persistent
	private float SP3;

	@Persistent
	private float LV1;

	@Persistent
	private float LV2;

	@Persistent
	private float LV3;

	@Persistent
	private float KI1;

	@Persistent
	private float KI2;

	@Persistent
	private float KI3;

	@Persistent
	private float BL1;

	@Persistent
	private float BL2;

	@Persistent
	private float BL3;

	@Persistent
	private float GB1;

	@Persistent
	private float GB2;

	@Persistent
	private float GB3;

	@Persistent
	private float ST1;

	@Persistent
	private float ST2;

	@Persistent
	private float ST3;

	@Persistent
	private float high;

	@Persistent
	private float low;

	@Persistent
	private float average;

	@Persistent
	private String notes;

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

	public final Date getExamDate() {
		return examDate;
	}

	public final void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public final float getLU1() {
		return LU1;
	}

	public final void setLU1(float lU1) {
		LU1 = lU1;
	}

	public final float getLU2() {
		return LU2;
	}

	public final void setLU2(float lU2) {
		LU2 = lU2;
	}

	public final float getLU3() {
		return LU3;
	}

	public final void setLU3(float lU3) {
		LU3 = lU3;
	}

	public final float getP1() {
		return P1;
	}

	public final void setP1(float p1) {
		P1 = p1;
	}

	public final float getP2() {
		return P2;
	}

	public final void setP2(float p2) {
		P2 = p2;
	}

	public final float getP3() {
		return P3;
	}

	public final void setP3(float p3) {
		P3 = p3;
	}

	public final float getHT1() {
		return HT1;
	}

	public final void setHT1(float hT1) {
		HT1 = hT1;
	}

	public final float getHT2() {
		return HT2;
	}

	public final void setHT2(float hT2) {
		HT2 = hT2;
	}

	public final float getHT3() {
		return HT3;
	}

	public final void setHT3(float hT3) {
		HT3 = hT3;
	}

	public final float getSI1() {
		return SI1;
	}

	public final void setSI1(float sI1) {
		SI1 = sI1;
	}

	public final float getSI2() {
		return SI2;
	}

	public final void setSI2(float sI2) {
		SI2 = sI2;
	}

	public final float getSI3() {
		return SI3;
	}

	public final void setSI3(float sI3) {
		SI3 = sI3;
	}

	public final float getTH1() {
		return TH1;
	}

	public final void setTH1(float tH1) {
		TH1 = tH1;
	}

	public final float getTH2() {
		return TH2;
	}

	public final void setTH2(float tH2) {
		TH2 = tH2;
	}

	public final float getTH3() {
		return TH3;
	}

	public final void setTH3(float tH3) {
		TH3 = tH3;
	}

	public final float getLT1() {
		return LT1;
	}

	public final void setLT1(float lT1) {
		LT1 = lT1;
	}

	public final float getLT2() {
		return LT2;
	}

	public final void setLT2(float lT2) {
		LT2 = lT2;
	}

	public final float getLT3() {
		return LT3;
	}

	public final void setLT3(float lT3) {
		LT3 = lT3;
	}

	public final float getSP1() {
		return SP1;
	}

	public final void setSP1(float sP1) {
		SP1 = sP1;
	}

	public final float getSP2() {
		return SP2;
	}

	public final void setSP2(float sP2) {
		SP2 = sP2;
	}

	public final float getSP3() {
		return SP3;
	}

	public final void setSP3(float sP3) {
		SP3 = sP3;
	}

	public final float getLV1() {
		return LV1;
	}

	public final void setLV1(float lV1) {
		LV1 = lV1;
	}

	public final float getLV2() {
		return LV2;
	}

	public final void setLV2(float lV2) {
		LV2 = lV2;
	}

	public final float getLV3() {
		return LV3;
	}

	public final void setLV3(float lV3) {
		LV3 = lV3;
	}

	public final float getKI1() {
		return KI1;
	}

	public final void setKI1(float kI1) {
		KI1 = kI1;
	}

	public final float getKI2() {
		return KI2;
	}

	public final void setKI2(float kI2) {
		KI2 = kI2;
	}

	public final float getKI3() {
		return KI3;
	}

	public final void setKI3(float kI3) {
		KI3 = kI3;
	}

	public final float getBL1() {
		return BL1;
	}

	public final void setBL1(float bL1) {
		BL1 = bL1;
	}

	public final float getBL2() {
		return BL2;
	}

	public final void setBL2(float bL2) {
		BL2 = bL2;
	}

	public final float getBL3() {
		return BL3;
	}

	public final void setBL3(float bL3) {
		BL3 = bL3;
	}

	public final float getGB1() {
		return GB1;
	}

	public final void setGB1(float gB1) {
		GB1 = gB1;
	}

	public final float getGB2() {
		return GB2;
	}

	public final void setGB2(float gB2) {
		GB2 = gB2;
	}

	public final float getGB3() {
		return GB3;
	}

	public final void setGB3(float gB3) {
		GB3 = gB3;
	}

	public final float getST1() {
		return ST1;
	}

	public final void setST1(float sT1) {
		ST1 = sT1;
	}

	public final float getST2() {
		return ST2;
	}

	public final void setST2(float sT2) {
		ST2 = sT2;
	}

	public final float getST3() {
		return ST3;
	}

	public final void setST3(float sT3) {
		ST3 = sT3;
	}

	public final float getHigh() {
		return high;
	}

	public final void setHigh(float high) {
		this.high = high;
	}

	public final float getLow() {
		return low;
	}

	public final void setLow(float low) {
		this.low = low;
	}

	public final float getAverage() {
		return average;
	}

	public final void setAverage(float average) {
		this.average = average;
	}

	public final String getNotes() {
		return notes;
	}

	public final void setNotes(String notes) {
		this.notes = notes;
	}

}
