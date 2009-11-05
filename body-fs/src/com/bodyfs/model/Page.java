/* $Id$
 */
package com.bodyfs.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * This class reprasents all the pages and their corresponding URIs in the
 * application. The page id is used for bookmark purposes
 * 
 * @author kesav
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Page {

	@PrimaryKey
	@Persistent
	private String id;
	@Persistent
	private String title;
	@Persistent
	private String path;

	public Page() {
	}

	public Page(final String id, final String title, final String path) {
		this.id = id;
		this.title = title;
		this.path = path;
	}

	public final String getId() {
		return id;
	}

	public final void setId(final String id) {
		this.id = id;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(final String title) {
		this.title = title;
	}

	public final String getPath() {
		return path;
	}

	public final void setPath(final String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Page [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (path != null)
			builder.append("path=").append(path).append(", ");
		if (title != null)
			builder.append("title=").append(title);
		builder.append("]");
		return builder.toString();
	}

}
