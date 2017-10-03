package com.andreaspost.gc.cachedb.rest.resource;

import java.util.logging.Logger;

public enum LogType {

	ANNOUNCEMENT("Announcement"), //
	ARCHIVE("Archive"), //
	ATTENDED("Attended"), //
	DNF("Didn't Find It"), //
	ENABLE_LISTING("Enable Listing"), //
	FOUND_IT("Found It"), //
	NOTE("Write Note"), //
	NM("Needs Maintenance"), //
	OWNER_MAINTENANCE("Owner Maintenance"), //
	TEMPORARILY_DISABLE_LISTING("Temporarily Disable Listing"), //
	WEBCAM("Webcam Photo Taken"), //
	WILL_ATTEND("Will attend"), //
	UNDEFINED("Undefined");

	private static final Logger LOG = Logger.getLogger(LogType.class.getName());

	private String name;

	private LogType(String name) {
		this.name = name;
	}

	public static LogType of(String name) {
		for (LogType type : LogType.values()) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		LOG.warning("Unknown LogType: " + name);
		return UNDEFINED;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
