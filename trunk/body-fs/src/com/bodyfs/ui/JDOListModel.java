/* $Id$ */
package com.bodyfs.ui;

import java.util.Comparator;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.ListModelExt;
import org.zkoss.zul.event.ListDataEvent;

/**
 * 
 * @author Kesav Kumar Kolla
 * 
 * @param <T>
 */
public class JDOListModel<T> extends AbstractListModel implements ListModelExt {

	private static final long serialVersionUID = -4682300908773450776L;
	private int _size;
	@SuppressWarnings("unused")
	private boolean _asc = true;
	private int _fetched;

	@SuppressWarnings("unchecked")
	@Override
	public void sort(Comparator cmpr, boolean ascending) {
		_asc = ascending;
		invalidate();
	}

	@Override
	public T getElementAt(int index) {
		return null;
	}

	public void invalidate() {
		fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
	}

	@Override
	public int getSize() {
		return _size;
	}

	public void setSize(final int size) {
		_size = size;
	}
}
