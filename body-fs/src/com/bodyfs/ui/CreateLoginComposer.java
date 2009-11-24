package com.bodyfs.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.api.Label;
import org.zkoss.zul.api.Textbox;

import com.bodyfs.dao.ILoginDAO;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.dao.impl.LoginDAO;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.Person;

public class CreateLoginComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -831489659737220006L;
	
	Person person;
	
	LoginInfo login;
	
	Textbox loginid;
	Textbox password; 
	
	Label loginmsg;
	
		
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		Long patid = (Long)sessionScope.get("patid");
		IPersonDAO personDao = (IPersonDAO)SpringUtil.getBean("personDAO");
		ILoginDAO loginDAO = (ILoginDAO) SpringUtil.getBean("loginDAO");
		person = personDao.getPerson(patid);
		login = loginDAO.getLoginDetails(patid);
		if(login == null){
			login = new LoginInfo();
			login.setPersonId(person.getId());
			login.setUserid(person.getFirstName()+"."+person.getLastName());
			login.setPassword(person.getFirstName()+"123");
		}
		this.requestScope.put("person", person);
		this.requestScope.put("login", login);
		
	}
	
	public void onSave(final Event evt){
		
	}
	
}
