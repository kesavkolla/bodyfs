package com.bodyfs.ui.util;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class BoolToInt implements TypeConverter, Serializable {

	private static final long serialVersionUID = -3342833292058702749L;

	@Override
	public Object coerceToBean(final Object val, final Component comp) {
		if (val == null) {
			return null;
		}
		Integer intVal = (Integer) val;
		if (intVal == -1) {
			return null;
		}
		return intVal.intValue();
	}

	@Override
	public Object coerceToUi(final Object val, final Component comp) {
		if (val == null) {
			return -1;
		}
		return ((Boolean) val).booleanValue() ? new Integer(0) : new Integer(1);
	}

}
