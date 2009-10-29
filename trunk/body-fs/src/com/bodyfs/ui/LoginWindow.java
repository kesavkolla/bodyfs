package com.bodyfs.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Window;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;

import com.bodyfs.dao.IPersonDAO;
import com.dyuproject.openid.RelyingParty;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginWindow extends GenericForwardComposer{
	
	private IPersonDAO personDAO;
	
	AnnotateDataBinder binder;

	Textbox usertb;
	Textbox pwdtb;
	Button login;
	A anchor;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4311810570038057744L;

	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		Window win = (Window)comp;
		//win.
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		System.out.println("aMIT 2112");
		//UserService user= UserServiceFactory.getUserService();
		//anchor.setHref(user.createLoginURL("http://localhost:8080/index.zul"));
		
		//LOGGER.error(this.page);
		//this.page.setAttribute("person", personDAO.getPerson(5L));
	}
	
	public void onClick$login(){
		//personDAO.getPerson(id);
		System.out.println("aMIT 11111");
		//UserService user= UserServiceFactory.getUserService();
		//user.createLoginURL("http://localhost:8080/index.zul");
		usertb.setValue("HAHAHAHA");
		binder.loadAll();
	}
}
