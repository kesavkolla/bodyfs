/* $Id$ */
package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.LoginInfo;
import com.bodyfs.ui.util.CustSearchOptions;

/**
 * 
 * @author kesav
 * 
 */
public interface ILoginDAO {

	/**
	 * Checks the userid and password matches or not
	 * 
	 * @param loginId userid
	 * @param password password
	 * @return LoginInfo object if they match null otherwise
	 */
	public LoginInfo verifyLoginDetails(final String loginId, final String password);

	/**
	 * 
	 * @param loginId
	 * @return true if the loginid is already exists false otherwise
	 */
	public boolean isLoginIdExists(final String loginId);

	/**
	 * 
	 * @param personId
	 * @return logininfo object for the given personid
	 */
	public LoginInfo getLoginDetails(final Long personId);

	/**
	 * Creates new login info
	 * 
	 * @param login
	 */
	public void createNewLogin(final LoginInfo login);

	/**
	 * 
	 * @return
	 */
	public Collection<LoginInfo> getAll();

	/**
	 * 
	 * @param options
	 * @return
	 */
	public Collection<LoginInfo> getByOptions(final CustSearchOptions options);

}
