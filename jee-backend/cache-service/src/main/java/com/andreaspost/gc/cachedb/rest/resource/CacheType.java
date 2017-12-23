package com.andreaspost.gc.cachedb.rest.resource;

import java.util.logging.Logger;

public enum CacheType {

	TRADITIONAL("Traditional Cache"), //
	MULTI("Multi-cache"), //
	MYSTERY("Unknown Cache"), //
	VIRTUAL("Virtual Cache"), //
	LETTERBOX("Letterbox Hybrid"), //
	WEBCAM("Webcam Cache"), //
	EARTHCACHE("Earthcache"), //
	GPS_ADVENTURE("GPS Adventures Exhibit"), //
	WHERIGO("Wherigo Cache"), //
	EVENT("Event Cache"), //
	CITO("Cache In Trash Out Event"), //
	MEGA_EVENT("Mega-Event Cache"), //
	LNF_EVENT("Lost and Found Event Cache"), //
	GIGA_EVENT("Giga-Event Cache"), //
	// TODO Geocaching HQ Cache, LAB Cache, APE, Reverse
	UNDEFINED("Undefined");

	private static final Logger LOG = Logger.getLogger(CacheType.class.getName());

	private String name;

	private CacheType(String name) {
		this.name = name;
	}

	public static CacheType of(String name) {
		for (CacheType type : CacheType.values()) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		LOG.warning("Unknown CacheType: " + name);
		return UNDEFINED;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
