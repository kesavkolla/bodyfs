/* $Id
 */
package com.bodyfs.ui.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.bodyfs.dao.IPageDAO;
import com.dyuproject.openid.Constants;
import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.dyuproject.openid.ext.SRegExtension;
import com.dyuproject.util.http.UrlEncodedParameterMap;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 * This class initializes the OpenID plugin
 * 
 * @author kesav
 * 
 */
public class ApplicationInitializer implements ServletContextListener {
	private static Log LOGGER = LogFactory.getLog(ApplicationInitializer.class);

	@Override
	public void contextDestroyed(final ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(final ServletContextEvent event) {
		initializeOpenId(event);
		initPages(event.getServletContext());
	}

	/**
	 * This will load the pages.xml in the memory
	 * 
	 * @param event
	 */
	public static void initPages(final ServletContext context) {
		try {
			final SAXReader reader = new SAXReader();
			LOGGER.debug("Loading: " + context.getResource("/WEB-INF/pages.xml"));
			final Document document = reader.read(context.getResourceAsStream("/WEB-INF/pages.xml"));
			MemcacheServiceFactory.getMemcacheService().put(IPageDAO.PAGES_DOCUMENT, document.getRootElement());
			LOGGER.debug("Saving in memcache: "
					+ MemcacheServiceFactory.getMemcacheService().get(IPageDAO.PAGES_DOCUMENT));
		} catch (final Exception e) {
			LOGGER.error(e);
		}
	}

	/**
	 * This method initializes all the OpenId handlers
	 * 
	 * @param event
	 */
	private void initializeOpenId(final ServletContextEvent event) {
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
					user.setAttribute("info", sreg);
				} else if (axschema != null && !axschema.isEmpty()) {
					user.setAttribute("info", axschema);
				} else {
				}

			}

			@Override
			public void onAccess(final OpenIdUser user, final HttpServletRequest request) {

			}
		});
	}
}
