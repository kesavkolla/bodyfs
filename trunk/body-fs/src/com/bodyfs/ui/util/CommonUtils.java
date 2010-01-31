/* $Id$ */
package com.bodyfs.ui.util;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.bodyfs.Constants;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.PersonType;

/**
 * 
 * @author kesav
 * 
 */
public class CommonUtils {

	/**
	 * This method returns the patient id. This method first checks the URL
	 * parameter if not present checks the current logged in user if not present
	 * checks for session attribute.
	 * 
	 * @return
	 */
	public static Long getPatientId() {
		String id = Executions.getCurrent().getParameter("id");
		if (id != null) {
			return new Long(id);
		}
		final Session session = Sessions.getCurrent(false);
		if (session != null) {
			final LoginInfo logininfo = (LoginInfo) session.getAttribute(Constants.SESSION_LOGIN_CRED);
			if (logininfo != null
					&& ((PersonType) session.getAttribute(Constants.SESSION_PERSON_TYPE)) != PersonType.EMPLOYEE) {
				return logininfo.getPersonId();
			}

			final Long patid = (Long) session.getAttribute("patid");
			if (patid != null) {
				return patid;
			}
		}
		return null;
	}
}
