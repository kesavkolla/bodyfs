/*
 * $Id$
 */
package com.bodyfs.ui.util;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.bodyfs.model.Gender;

/**
 * This class converts Gender enum class to radio group selected index
 * 
 * @author kesav
 * 
 */
public class GenderToRadiogroup implements TypeConverter, Serializable {

	private static final long serialVersionUID = -3348774722651545293L;

	@Override
	public Object coerceToBean(final Object val, final Component comp) {
		if (val == null) {
			return null;
		}
		int selectedIndex = ((Integer) val).intValue();
		if (selectedIndex == -1) {
			return null;
		}
		return Gender.values()[selectedIndex];
	}

	@Override
	public Object coerceToUi(final Object val, final Component comp) {
		if (val == null) {
			return -1;
		}
		return ((Gender) val).ordinal();
	}

}
