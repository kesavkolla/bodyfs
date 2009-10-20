/*
 * $Id$
 */
package com.bodyfs.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * This class reprasents all the login information of the user
 * 
 * @author Kesav Kumar Kolla
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class LoginInfo {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Key person;

	@Persistent
	private LoginType loginType;

	@Persistent
	private String userid;

	@Persistent
	private String password;

	@Persistent
	private String openIdProvider;

	@Persistent
	private String openIdURL;

	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDSTRING)
	private String token;

	@Persistent
	private String secQuestion1;

	@Persistent
	private String secAnswer1;

	@Persistent
	private String secQuestion2;

	@Persistent
	private String secAnswer2;

	@Persistent
	private String secQuestion3;

	@Persistent
	private String secAnswer3;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method will return the primary key of the person object that this
	 * logininfo belongs to
	 * 
	 * @return person objects primary key
	 */
	public final Key getPerson() {
		return person;
	}

	public final void setPerson(Key person) {
		this.person = person;
	}

	public final LoginType getLoginType() {
		return loginType;
	}

	public final void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public final String getUserid() {
		return userid;
	}

	public final void setUserid(String userid) {
		this.userid = userid;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final String getOpenIdProvider() {
		return openIdProvider;
	}

	public final void setOpenIdProvider(String openIdProvider) {
		this.openIdProvider = openIdProvider;
	}

	public final String getOpenIdURL() {
		return openIdURL;
	}

	public final void setOpenIdURL(String openIdURL) {
		this.openIdURL = openIdURL;
	}

	public final String getToken() {
		return token;
	}

	public final void setToken(String token) {
		this.token = token;
	}

	public final String getSecQuestion1() {
		return secQuestion1;
	}

	public final void setSecQuestion1(String secQuestion1) {
		this.secQuestion1 = secQuestion1;
	}

	public final String getSecAnswer1() {
		return secAnswer1;
	}

	public final void setSecAnswer1(String secAnswer1) {
		this.secAnswer1 = secAnswer1;
	}

	public final String getSecQuestion2() {
		return secQuestion2;
	}

	public final void setSecQuestion2(String secQuestion2) {
		this.secQuestion2 = secQuestion2;
	}

	public final String getSecAnswer2() {
		return secAnswer2;
	}

	public final void setSecAnswer2(String secAnswer2) {
		this.secAnswer2 = secAnswer2;
	}

	public final String getSecQuestion3() {
		return secQuestion3;
	}

	public final void setSecQuestion3(String secQuestion3) {
		this.secQuestion3 = secQuestion3;
	}

	public final String getSecAnswer3() {
		return secAnswer3;
	}

	public final void setSecAnswer3(String secAnswer3) {
		this.secAnswer3 = secAnswer3;
	}

}
