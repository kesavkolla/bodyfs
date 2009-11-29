package com.bodyfs.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Label;
import org.zkoss.zul.api.Textbox;

import com.bodyfs.dao.ILoginDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.Person;

public class CreateLoginComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -831489659737220006L;
	
	Person person;
	
	Window win;
	
	LoginInfo login;
	
	Textbox loginid;
	Textbox password; 
	
	Label loginmsg;
	Label titleLabel;
		
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		win = (Window) comp;
		Long patid = (Long)session.getAttribute("patid");//sessionScope.get("patid");
		IPersonDAO personDao = (IPersonDAO)SpringUtil.getBean("personDAO");
		ILoginDAO loginDAO = (ILoginDAO) SpringUtil.getBean("loginDAO");
		person = personDao.getPerson(patid);
		login = loginDAO.getLoginDetails(patid);
		titleLabel.setValue(""+person.getLastName()+", "+person.getFirstName());
		if(login == null){
			login = new LoginInfo();
			login.setPersonId(person.getId());
			loginid.setValue(person.getFirstName().replaceAll(" ", "")+"."+person.getLastName().replaceAll(" ", ""));
			password.setValue(person.getFirstName().toLowerCase().split(" ")[0]+"123");
		}else{
			loginid.setValue(login.getUserid());
			loginid.setDisabled(true);
			password.setValue(login.getPassword());
		}
	}
	
	public void onSave(final Event evt){
		System.out.println("onsave");
		login.setUserid(loginid.getValue().toLowerCase());
		login.setPassword(password.getValue());
		ILoginDAO loginDAO = (ILoginDAO) SpringUtil.getBean("loginDAO");
		loginDAO.createNewLogin(login);
		win.detach();
	}
	
}
