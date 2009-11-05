/* $Id
 */
package com.bodyfs.ui.listener;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import com.dyuproject.openid.Constants;
import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.dyuproject.openid.ext.SRegExtension;
import com.dyuproject.util.http.UrlEncodedParameterMap;

/**
 * This class initializes the OpenID plugin
 * 
 * @author kesav
 * 
 */
public class OpenIdLoaderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(final ServletContextEvent event) {
		RelyingParty.getInstance().addListener(
				new SRegExtension().addExchange("email").addExchange("fullname").addExchange("nickname").addExchange(
						"language")).addListener(
				new AxSchemaExtension().addExchange("email").addExchange("firstname").addExchange("lastname")
						.addExchange("language")).addListener(new RelyingParty.Listener() {

			@Override
			public void onPreAuthenticate(final OpenIdUser user, final HttpServletRequest request,
					final UrlEncodedParameterMap params) {
				if ("true".equals(request.getParameter("popup"))) {
					final String returnTo = params.get(Constants.OPENID_TRUST_ROOT) + request.getContextPath()
							+ "/popup_verify.html";
					params.put(Constants.OPENID_RETURN_TO, returnTo);
					params.put(Constants.OPENID_REALM, returnTo);
					params.put("openid.ns.ui", "http://specs.openid.net/extensions/ui/1.0");
					params.put("openid.ui.mode", "popup");
				}
			}

			@Override
			public void onDiscovery(final OpenIdUser user, final HttpServletRequest request) {
			}

			@Override
			public void onAuthenticate(final OpenIdUser user, final HttpServletRequest request) {
				final Map<String, String> sreg = SRegExtension.remove(user);
				final Map<String, String> axschema = AxSchemaExtension.remove(user);
				if (sreg != null && !sreg.isEmpty()) {
					System.err.println("sreg: " + sreg);
					user.setAttribute("info", sreg);
				} else if (axschema != null && !axschema.isEmpty()) {
					System.err.println("axschema: " + axschema);
					user.setAttribute("info", axschema);
				} else {
					System.err.println("identity: " + user.getIdentity());
				}

			}

			@Override
			public void onAccess(final OpenIdUser user, final HttpServletRequest request) {

			}
		});
	}
}
