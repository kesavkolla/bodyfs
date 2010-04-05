/* $Id$ */
package com.bodyfs.ui;

import java.util.Collection;

import net.sf.jsr107cache.Cache;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;

import com.bodyfs.dao.IPersonDAO;
import com.bodyfs.model.QuickPatient;

/**
 * 
 * This composer is attached to header.zul. This composer adds the quick list of
 * previously accessed patients in a list
 * 
 * @author Kesav Kumar Kolla
 * 
 */
public class QuickPatientListComposer extends GenericForwardComposer {

	public static final String QUICK_PATIENT_LIST = "com.bodyfs.quickpatientlist";
	private static final long serialVersionUID = 2301671317724738233L;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final Cache cache = (Cache) SpringUtil.getBean("datacache");
		final IPersonDAO personDAO = (IPersonDAO) SpringUtil.getBean("personDAO");
		Collection<QuickPatient> quicklist = null;

		if (!cache.containsKey(QUICK_PATIENT_LIST)) {
			quicklist = personDAO.getQuickList();
			cache.put(QUICK_PATIENT_LIST, quicklist);
		} else {
			quicklist = (Collection<QuickPatient>) cache.get(QUICK_PATIENT_LIST);
		}
		if (quicklist == null || quicklist.size() <= 0) {
			return;
		}
		final StringBuilder buffer = new StringBuilder();

		final String curId = execution.getParameter("id");
		for (final QuickPatient qp : quicklist) {
			buffer.append("<div class='bodyfs-qpatient");
			if (curId != null && curId.equals(qp.getId().toString())) {
				buffer.append(" bodyfs-qpatient-selected");
			}
			buffer.append("'><a href='/pages/patient/weeklyvisit.zul?&id=").append(qp.getId()).append("'>").append(
					qp.getName()).append("</a></div>");
		}

		final Div divQuicklist = (Div) comp;

		divQuicklist.appendChild(new Html(buffer.toString()));
	}
}
