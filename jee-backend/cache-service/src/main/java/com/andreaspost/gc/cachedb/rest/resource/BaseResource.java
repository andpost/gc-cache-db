package com.andreaspost.gc.cachedb.rest.resource;

/**
 * Base class for resources...
 * 
 * @author Andreas Post
 */
public abstract class BaseResource {

	private String href;

	private String id;

	/**
	 * Empty constructor.
	 */
	public BaseResource() {

	}

	/**
	 * Constructor for main fields.
	 * 
	 * @param id
	 * @param content
	 * @param createdAt
	 * @param createdBy
	 */
	public BaseResource(String id) {
		this.id = id;
	}

	/**
	 * Constructor for all fields.
	 * 
	 * @param href
	 * @param id
	 * @param createdAt
	 * @param createdBy
	 */
	public BaseResource(String href, String id) {
		this.href = href;
		this.id = id;
	}

	/**
	 * The self URL.
	 * 
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 *            the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
