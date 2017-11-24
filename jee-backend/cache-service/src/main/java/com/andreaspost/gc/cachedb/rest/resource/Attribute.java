package com.andreaspost.gc.cachedb.rest.resource;

/**
 * Class representing a geocache attribute.
 * 
 * @author Andreas Post
 */
public class Attribute {

	private String name;

	private String id;

	public Attribute() {

	}

	public Attribute(String name, String id) {
		this.name = name;
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Attribute [name=" + name + ", id=" + id + "]";
	}
}
