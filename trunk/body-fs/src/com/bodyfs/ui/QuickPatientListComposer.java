/* $Id$ */
package com.bodyfs.ui;

import java.util.concurrent.ArrayBlockingQueue;

import net.sf.jsr107cache.Cache;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;

/**
 * 
 * This composer is attached to header.zul. This composer adds the quick list of
 * previously accessed patients in a list
 * 
 * @author Kesav Kumar Kolla
 * 
 */
public class QuickPatientListComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 2301671317724738233L;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		final Cache cache = (Cache) SpringUtil.getBean("datacache");
		if (!cache.containsKey(PatientViewComposer.QUICK_PATIENT_LIST)) {
			return;
		}
		final ArrayBlockingQueue<String> quicklist = (ArrayBlockingQueue<String>) cache
				.get(PatientViewComposer.QUICK_PATIENT_LIST);
		if (quicklist.size() <= 0) {
			return;
		}
		final StringBuilder buffer = new StringBuilder();

		final String[] patData = new String[quicklist.size()];
		quicklist.toArray(patData);
		String curId = execution.getParameter("id");
		for (int i = patData.length - 1; i >= 0; i--) {
			int idIndex = patData[i].indexOf(":");
			String patId = patData[i].substring(0, idIndex);
			buffer.append("<div class='bodyfs-qpatient");
			if (curId != null && curId.equals(patId)) {
				buffer.append(" bodyfs-qpatient-selected");
			}
			buffer.append("'><a href='/pages/patient/patientview.zul?&id=").append(patId).append("'>").append(
					patData[i].substring(idIndex + 1)).append("</a></div>");
		}

		final Div divQuicklist = (Div) comp;

		divQuicklist.appendChild(new Html(buffer.toString()));
	}
}
