/* $Id$ */
package com.bodyfs.ui.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.spring.SpringUtil;

import com.bodyfs.Constants;
import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.LoginInfo;
import com.bodyfs.model.Person;
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
		if (id != null && !id.equals("")) {
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

	public static boolean getIsAdminUser() {

		final Session session = Sessions.getCurrent(false);
		if (session != null) {
			final LoginInfo logininfo = (LoginInfo) session.getAttribute(Constants.SESSION_LOGIN_CRED);
			if (logininfo != null
					&& ((PersonType) session.getAttribute(Constants.SESSION_PERSON_TYPE)) != PersonType.EMPLOYEE) {
				return false;
			}
		}
		return true;
	}

	public static Long getPatientId(final HttpServletRequest request) {
		String id = request.getParameter("id");
		if (id != null && id.trim().length() > 0) {
			return Long.parseLong(id);
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
		} else {
			final HttpSession hsession = request.getSession(false);
			if (hsession != null) {
				final LoginInfo logininfo = (LoginInfo) hsession.getAttribute(Constants.SESSION_LOGIN_CRED);
				if (logininfo != null) {
					if (logininfo != null
							&& ((PersonType) hsession.getAttribute(Constants.SESSION_PERSON_TYPE)) != PersonType.EMPLOYEE) {
						return logininfo.getPersonId();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return display name of the patient
	 */
	public static String getDisplayName() {
		final Long patid = getPatientId();
		if (patid == null) {
			return "";
		}
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		if (personDAO == null) {
			return "";
		}
		final Person person = personDAO.getPerson(patid, true);
		return person.getDisplayName();
	}
}
