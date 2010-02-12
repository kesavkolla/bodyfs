package com.bodyfs.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Window;

import com.bodyfs.Constants;
import com.bodyfs.dao.ILoginDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.Person;
import com.bodyfs.model.PersonType;

public class LoginWindow extends GenericForwardComposer {

	AnnotateDataBinder binder;

	Textbox usertb;
	Textbox pwdtb;
	Button login;
	Window win;
	Label msg;
	Textbox redirecturl;

	private static final long serialVersionUID = 4311810570038057744L;

	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		win = (Window) comp;
		// win.
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	public void doLogin() {
		String user = usertb.getValue().toLowerCase();
		String pwd = pwdtb.getValue();

		if ((user != null && user.isEmpty()) || (pwd != null && pwd.isEmpty())) {
			msg.setValue("*Need user name and password!");
			return;
		}

		final LoginInfo userDetails = getLoginDetails(user, pwd);

		if (userDetails == null) {
			msg.setValue("*Wrong username or password!");
			return;
		}

		session.setAttribute(Constants.SESSION_LOGIN_CRED, userDetails);

		msg.setValue("");
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		final Person person = personDAO.getPerson(userDetails.getPersonId());
		session.setAttribute(Constants.SESSION_PERSON_TYPE, person.getPersonType());
		if (redirecturl != null && redirecturl.getText() != null && redirecturl.getText().length() > 0) {
			Executions.sendRedirect(redirecturl.getText());
			return;
		}
		if (person.getPersonType() == PersonType.EMPLOYEE) {
			Executions.sendRedirect("/index.zul");
		} else {
			Executions.sendRedirect("/pages/user/index.zul");
		}
		binder.loadAll();
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
