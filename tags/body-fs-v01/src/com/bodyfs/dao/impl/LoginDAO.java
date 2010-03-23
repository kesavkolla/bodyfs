package com.bodyfs.dao.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.stereotype.Repository;

import com.bodyfs.PMF;
import com.bodyfs.controllers.PersonController;
import com.bodyfs.dao.ILoginDAO;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.ui.util.CustSearchOptions;

@Repository(value = "loginDAO")
public class LoginDAO implements ILoginDAO, Serializable {

	private static final long serialVersionUID = 6501061341246752560L;
	private static final Log LOGGER = LogFactory.getLog(PersonController.class);
	private JdoTemplate jdoTemplate = new JdoTemplate(PMF.get());

	/**
	 * This method should be used for development purposes and disabled during
	 * the time of deployment
	 */
	public void initLogins() {

		if (this.getAll().size() > 0) {
			LOGGER.error("LOgin exists");
			return;
		}
		LoginInfo login1 = new LoginInfo();
		login1.setUserid("amit");
		login1.setPassword("1111");
		this.createNewLogin(login1);
		// pm.makePersistent(login1);
		LOGGER.error("Login 1 created");
		LoginInfo login2 = new LoginInfo();
		login2.setUserid("kesav");
		login2.setPassword("2222");
		this.createNewLogin(login2);
		LOGGER.debug("Login 2 created");

		LoginInfo login3 = new LoginInfo();
		login3.setUserid("neel");
		login3.setPassword("1111");
		this.createNewLogin(login3);
		LOGGER.debug("Login 3 created");

		// resultSet = pm.detachCopyAll(resultSet);
		// extendedSearch.setVisible(false);
	}

	@Override
	public LoginInfo getLoginDetails(Long personId) {
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
	public LoginInfo verifyLoginDetails(String loginId, String password) {
		try {
			final LoginInfo login = this.jdoTemplate.getObjectById(LoginInfo.class, loginId);
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
	public void createNewLogin(LoginInfo login) {
		this.jdoTemplate.makePersistent(login);
	}

	@Override
	public Collection<LoginInfo> getAll() {
		return this.jdoTemplate.execute(new JdoCallback<Collection<LoginInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Collection<LoginInfo> doInJdo(PersistenceManager pm) throws JDOException {
				final Query query = pm.newQuery(LoginInfo.class);
				Collection<LoginInfo> lst = (Collection<LoginInfo>) query.execute();
				// lst.size();
				return lst;
			}

		});
	}

	@Override
	public Collection<LoginInfo> getByOptions(CustSearchOptions options) {
		// return this.jdoTemplate.find(queryString);
		return null;
	}

}
