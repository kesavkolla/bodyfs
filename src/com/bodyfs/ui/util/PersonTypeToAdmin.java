/* $Id$ */
package com.bodyfs.ui.util;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.bodyfs.model.PersonType;

/**
 * This class converts the PersonType Employee to a admin checkbox. This
 * converter is used in createLoginInfo.zul page
 * 
 * @author kesav
 * 
 */
public class PersonTypeToAdmin implements TypeConverter, Serializable {

	private static final long serialVersionUID = 2544945810121178972L;

	@Override
	public Object coerceToBean(final Object val, final Component comp) {
		if (val == null) {
			return null;
		}
		if ((Boolean) val) {
			return PersonType.EMPLOYEE;
		}
		return null;
	}

	@Override
	public Object coerceToUi(final Object val, final Component comp) {
		if (val == null || !(val instanceof PersonType)) {
			return false;
		}
		return (PersonType.EMPLOYEE == (PersonType) val);
	}
}
