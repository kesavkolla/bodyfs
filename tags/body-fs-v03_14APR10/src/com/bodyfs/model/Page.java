/* $Id$
 */
package com.bodyfs.model;

/**
 * This class reprasents all the pages and their corresponding URIs in the
 * application. The page id is used for bookmark purposes
 * 
 * @author kesav
 * 
 */
public class Page {
	private String id;
	private String title;
	private String path;
	private String target;

	public Page() {
	}

	public Page(final String id, final String title, final String path, final String target) {
		this.id = id;
		this.title = title;
		this.path = path;
		this.target = target;
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

	public final String getTarget() {
		return target;
	}

	public final void setTarget(final String target) {
		this.target = target;
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
