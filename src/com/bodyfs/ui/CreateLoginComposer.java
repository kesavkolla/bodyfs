/* $Id$ */
package com.bodyfs.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import com.bodyfs.dao.ILoginDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.GeneralInfo;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.Person;
import com.bodyfs.model.PersonType;

/**
 * 
 * @author kesav
 * 
 */
public class CreateLoginComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -831489659737220006L;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final Long patid = new Long(execution.getParameter("id"));
		final IPersonDAO personDao = (IPersonDAO) SpringUtil.getBean("personDAO");
		final ILoginDAO loginDAO = (ILoginDAO) SpringUtil.getBean("loginDAO");
		final Person person = personDao.getPerson(patid);
		LoginInfo login = loginDAO.getLoginDetails(patid);
		final GeneralInfo ginfo = personDao.getGeneralInfo(patid) != null? personDao.getGeneralInfo(patid) : new GeneralInfo();
		
		if (ginfo.getPersonId() == null) {
			ginfo.setPersonId(patid);
		}
		
		if (login == null) {
			login = new LoginInfo();
			login.setPersonId(person.getId());
			login.setUserid(person.getFirstName().replaceAll(" ", "") + "." + person.getLastName().replaceAll(" ", ""));
			login.setPassword(person.getFirstName().toLowerCase().split(" ")[0] + "123");
		} else {
			final Textbox loginid = (Textbox) Path.getComponent(page, "loginid");
			loginid.setDisabled(true);
			final Button btnSave = (Button) Path.getComponent(page, "btnSave");
			btnSave.setLabel("Update");
		}
		this.page.setAttribute("person", person);
		this.page.setAttribute("ginfo", ginfo);
		this.page.setAttribute("logininfo", login);
		this.page.setAttribute("patid", patid);
	}

	public void onSave(final Event evt) {
		final Person person = (Person) page.getAttribute("person");
		if (person.getPersonType() == null) {
			person.setPersonType(PersonType.USER);
		}
		IPersonDAO personDao = (IPersonDAO) SpringUtil.getBean("personDAO");
		personDao.createPerson(person);
		final GeneralInfo ginfo = (GeneralInfo) page.getAttribute("ginfo");
		personDao.createGeneralInfo(ginfo);
		ILoginDAO loginDAO = (ILoginDAO) SpringUtil.getBean("loginDAO");
		final LoginInfo logininfo = (LoginInfo) page.getAttribute("logininfo");
		loginDAO.createNewLogin(logininfo);
		Executions.sendRedirect("/pages/patient/patientview.zul?id=" + page.getAttribute("patid"));
	}

	public void onCancel(final Event evt) {
		Executions.sendRedirect("/pages/patient/patientview.zul?id=" + page.getAttribute("patid"));
	}

}
