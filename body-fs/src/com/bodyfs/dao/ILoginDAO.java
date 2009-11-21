package com.bodyfs.dao;

import java.util.Collection;

import com.bodyfs.model.LoginInfo;
import com.bodyfs.ui.util.CustSearchOptions;

public interface ILoginDAO {
	public LoginInfo verifyLoginDetails(String loginId, String password);
	
	public LoginInfo getLoginDetails();
	
	public void createNewLogin(LoginInfo login);
	
	public Collection<LoginInfo> getAll();
	
	public Collection<LoginInfo> getByOptions(CustSearchOptions options);
	
}
