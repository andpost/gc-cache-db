package com.andreaspost.gc.cachedb.rest.resource;

import java.util.logging.Logger;

/**
 * Enum for geocaching container types.
 * 
 * @author Andreas Post
 */
public enum ContainerType {

	NOT_CHOSEN("Not chosen"), //
	MICRO("Micro"), //
	SMALL("Small"), //
	REGULAR("Regular"), //
	LARGE("Large"), //
	OTHER("Other"), //
	VIRTUAL("Virtual"), //
	UNDEFINED("Undefined");

	private static final Logger LOG = Logger.getLogger(ContainerType.class.getName());

	private String name;

	private ContainerType(String name) {
		this.name = name;
	}

	public static ContainerType of(String name) {
		for (ContainerType type : ContainerType.values()) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		LOG.warning("Unknown Container: " + name);
		return UNDEFINED;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
