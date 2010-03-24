/*
 * $Id$
 */
package com.bodyfs.ui.util;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.bodyfs.model.HighLow;

/**
 * This class converts HighLow enum class to radio group selected index
 * 
 * @author kesav
 * 
 */
public class HighLowToRadiogroup implements TypeConverter, Serializable {

	private static final long serialVersionUID = 5183060791782324203L;

	@Override
	public Object coerceToBean(final Object val, final Component comp) {
		if (val == null) {
			return null;
		}
		int selectedIndex = ((Integer) val).intValue();
		if (selectedIndex == -1) {
			return null;
		}
		return HighLow.values()[selectedIndex];
	}

	@Override
	public Object coerceToUi(final Object val, final Component comp) {
		if (val == null) {
			return -1;
		}
		return ((HighLow) val).ordinal();
	}

}
