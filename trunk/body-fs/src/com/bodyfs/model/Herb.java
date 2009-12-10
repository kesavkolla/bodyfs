/* $Id$ */
package com.bodyfs.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "true", detachable = "true")
public class Herb implements Serializable {

	private static final long serialVersionUID = -2740917335487828692L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long Id;

	@Persistent
	private String pinyin;
	@Persistent
	private String latin;
	@Persistent
	private String taste;
	@Persistent
	private String temperature;
	@Persistent
	private Text action;
	@Persistent
	private String category;
	@Persistent
	private String meridians;
	@Persistent
	private String contraindications;
	@Persistent
	private String txtext;
	@Persistent
	private String herbtype;
	@Persistent
	private String commonName;
	@Persistent
	private String referencePage;
	@Persistent
	private String comment;
	@Persistent
	private String distributor;
	@Persistent
	private String inven;

	public final Long getId() {
		return Id;
	}

	public final void setId(Long id) {
		Id = id;
	}

	public final String getPinyin() {
		return pinyin;
	}

	public final void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public final String getLatin() {
		return latin;
	}

	public final void setLatin(String latin) {
		this.latin = latin;
	}

	public final String getTaste() {
		return taste;
	}

	public final void setTaste(String taste) {
		this.taste = taste;
	}

	public final String getTemperature() {
		return temperature;
	}

	public final void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public final String getAction() {
		if (action == null) {
			return null;
		}
		return action.getValue();
	}

	public final void setAction(final String action) {
		this.action = new Text(action);
	}

	public final String getCategory() {
		return category;
	}

	public final void setCategory(String category) {
		this.category = category;
	}

	public final String getMeridians() {
		return meridians;
	}

	public final void setMeridians(String meridians) {
		this.meridians = meridians;
	}

	public final String getContraindications() {
		return contraindications;
	}

	public final void setContraindications(String contraindications) {
		this.contraindications = contraindications;
	}

	public final String getTxtext() {
		return txtext;
	}

	public final void setTxtext(String txtext) {
		this.txtext = txtext;
	}

	public final String getHerbtype() {
		return herbtype;
	}

	public final void setHerbtype(String herbtype) {
		this.herbtype = herbtype;
	}

	public final String getCommonName() {
		return commonName;
	}

	public final void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public final String getReferencePage() {
		return referencePage;
	}

	public final void setReferencePage(String referencePage) {
		this.referencePage = referencePage;
	}

	public final String getComment() {
		return comment;
	}

	public final void setComment(String comment) {
		this.comment = comment;
	}

	public final String getDistributor() {
		return distributor;
	}

	public final void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public final String getInven() {
		return inven;
	}

	public final void setInven(String inven) {
		this.inven = inven;
	}
}
