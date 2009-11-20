/*
 * $Id$
 */
package com.bodyfs.ui.util;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.bodyfs.model.PatientVisit;

/**
 * 
 * @author kesav
 * 
 */
public class QualityOfSleepToRadiogroup implements TypeConverter, Serializable {

	private static final long serialVersionUID = -2668378090398265729L;

	@Override
	public Object coerceToBean(final Object val, final Component comp) {
		if (val == null) {
			return null;
		}
		int selectedIndex = ((Integer) val).intValue();
		if (selectedIndex == -1) {
			return null;
		}
		return PatientVisit.QualityOfSleep.values()[selectedIndex];
	}

	@Override
	public Object coerceToUi(final Object val, final Component comp) {
		if (val == null) {
			return -1;
		}
		return ((PatientVisit.QualityOfSleep) val).ordinal();
	}

}
