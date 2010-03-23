package com.bodyfs.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.bodyfs.Constants;
import com.bodyfs.dao.ILoginDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.Person;
import com.bodyfs.model.PersonType;

public class LoginWindow extends GenericForwardComposer {

	private static final long serialVersionUID = 4311810570038057744L;

	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	public void doLogin() {
		final Textbox txtUserName = (Textbox) Path.getComponent(page, "txtUserName");
		final Textbox txtPasswd = (Textbox) Path.getComponent(page, "txtPasswd");
		final Textbox txtRedirectUrl = (Textbox) Path.getComponent(page, "txtRedirectUrl");
		final Label lblMsg = (Label) Path.getComponent(page, "lblMsg");

		String user = txtUserName.getValue().toLowerCase();
		String pwd = txtPasswd.getValue();

		if ((user != null && user.isEmpty()) || (pwd != null && pwd.isEmpty())) {
			lblMsg.setValue("*Need user name and password!");
			return;
		}

		final LoginInfo userDetails = getLoginDetails(user, pwd);

		if (userDetails == null) {
			lblMsg.setValue("*Wrong username or password!");
			return;
		}

		session.setAttribute(Constants.SESSION_LOGIN_CRED, userDetails);

		lblMsg.setValue("");
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final Person person = personDAO.getPerson(userDetails.getPersonId());
		session.setAttribute(Constants.SESSION_PERSON_TYPE, person.getPersonType());
		if (txtRedirectUrl != null && txtRedirectUrl.getText() != null && txtRedirectUrl.getText().length() > 0) {
			Executions.sendRedirect(txtRedirectUrl.getText());
			return;
		}
		if (person.getPersonType() == PersonType.EMPLOYEE) {
			Executions.sendRedirect("/index.zul");
		} else {
			Executions.sendRedirect("/pages/user/index.zul");
		}
	}

	public void onClick$login() throws Exception {
		doLogin();
	}

	public void onOK() {
		doLogin();
	}

	/**
	 * 
	 * @param loginId
	 * @param password
	 * @return
	 */
	public LoginInfo getLoginDetails(String loginId, String password) {
		final ILoginDAO dao = (ILoginDAO) SpringUtil.getBean("loginDAO");
		LoginInfo login = dao.verifyLoginDetails(loginId, password);
		return login;
	}
}
