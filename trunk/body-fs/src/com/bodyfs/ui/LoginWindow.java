package com.bodyfs.ui;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Window;

import com.bodyfs.dao.ILoginDAO;
import com.bodyfs.dao.impl.LoginDAO;
import com.bodyfs.model.LoginInfo;

public class LoginWindow extends GenericForwardComposer{
	
	
	AnnotateDataBinder binder;

	Textbox usertb;
	Textbox pwdtb;
	Button login;
	
	ILoginDAO dao = (ILoginDAO)SpringUtil.getBean("loginDAO");
	
	Window win;
	
	Label msg;
	
	private static final long serialVersionUID = 4311810570038057744L;

	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		win = (Window)comp;
		//win.
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	
	public void doLogin(){
		String user = usertb.getValue();
		String pwd = pwdtb.getValue();
		
		if((user!=null && user.isEmpty()) || (pwd!=null && pwd.isEmpty())){
			msg.setValue("*Need user name and password!");
			return;
		}
		
		LoginInfo userDetails = getLoginDetails(user, pwd); 
		
		if(userDetails==null){
			msg.setValue("*Wrong username or password!");
			return;
		}
		
		
		session.setAttribute("LOGIN_CREDENTIALS",userDetails);
		
		msg.setValue("");
		Executions.sendRedirect("/index.zul");
		binder.loadAll();
	}
	
	public void onClick$login() throws Exception{
		doLogin();
	}
	
	public void onOK(){
		doLogin();
	}
	
	
	public LoginInfo getLoginDetails(String loginId,String password ){
		LoginInfo login =  dao.verifyLoginDetails(loginId, password);
		return login;
	}
}
