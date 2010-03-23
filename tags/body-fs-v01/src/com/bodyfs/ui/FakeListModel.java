package com.bodyfs.ui;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

public class FakeListModel extends AbstractListModel {

	private static final long serialVersionUID = 4143517731003447818L;
	private int _size;

	public FakeListModel() {
		this(10000);
	}

	public FakeListModel(int size) {
		_size = size;
	}

	public void invalidate() {
		fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
	}

	// AbstractListModel
	public Object getElementAt(int v) {
		String value = "Option " + v;
		return value;
	}

	// AbstractListModel
	public int getSize() {
		return _size;
	}

	// AbstractListModel
	public void setSize(int size) {
		_size = size;
	}

}
