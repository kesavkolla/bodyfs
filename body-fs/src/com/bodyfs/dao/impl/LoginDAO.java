package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.dao.ILoginDAO;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.ui.util.CustSearchOptions;

@Repository(value = "loginDAO")
public class LoginDAO implements ILoginDAO, Serializable {

	private static final long serialVersionUID = 6501061341246752560L;
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	@Override
	public LoginInfo getLoginDetails(final Long personId) {
		try {
			final Collection<LoginInfo> results = this.jdoTemplate.find(LoginInfo.class, "personId==pid", "String pid",
					personId);
			if (results.size() <= 0) {
				return null;
			}
			return results.iterator().next();
		} catch (final Throwable e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public LoginInfo verifyLoginDetails(final String loginId, final String password) {
		try {
			final LoginInfo login = this.jdoTemplate.getObjectById(LoginInfo.class, loginId.trim().toLowerCase());
			if (login != null && login.getPassword().equals(password)) {
				return login;
			}
			return null;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isLoginIdExists(final String loginId) {
		if (loginId == null) {
			return true;
		}
		try {
			final LoginInfo logininfo = jdoTemplate.getObjectById(LoginInfo.class, loginId.trim().toLowerCase());
			return (logininfo != null);
		} catch (final Exception e) {
			return false;
		}
	}

	@Override
	public void createNewLogin(final LoginInfo login) {
		login.setUserid(login.getUserid().trim().toLowerCase());
		this.jdoTemplate.makePersistent(login);
	}

	@Override
	public Collection<LoginInfo> getAll() {
		return jdoTemplate.find(LoginInfo.class);
	}

	@Override
	public Collection<LoginInfo> getByOptions(final CustSearchOptions options) {
		// return this.jdoTemplate.find(queryString);
		return null;
	}

}
