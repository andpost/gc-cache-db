package com.andreaspost.gc.cachedb.rest.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.geojson.Point;

public class GeoCache {

	private String href;

	private String gcCode;

	private String name;

	private Point coordinates;

	private String placedBy;

	private User owner;
	private CacheType type;
	private String container;
	private List<Attribute> attributes = new ArrayList<>();
	private float difficulty;
	private float terrain;
	private String country;
	private String state;

	private String shortDescription;

	private String longDescription;

	private String encodedHints;
	private Set<Log> logs = new TreeSet<>();

	private String id;

	public GeoCache() {

	}

	public GeoCache(String gcCode) {
		this.gcCode = gcCode;
	}

	/**
	 * @return the gcCode
	 */
	public String getGcCode() {
		return gcCode;
	}

	/**
	 * @param gcCode
	 *            the gcCode to set
	 */
	public void setGcCode(String gcCode) {
		this.gcCode = gcCode;
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
	 * @return the coordinates
	 */
	public Point getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates
	 *            the coordinates to set
	 */
	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the placedBy
	 */
	public String getPlacedBy() {
		return placedBy;
	}

	/**
	 * @param placedBy
	 *            the placedBy to set
	 */
	public void setPlacedBy(String placedBy) {
		this.placedBy = placedBy;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the type
	 */
	public CacheType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(CacheType type) {
		this.type = type;
	}

	/**
	 * @return the container
	 */
	public String getContainer() {
		return container;
	}

	/**
	 * @param container
	 *            the container to set
	 */
	public void setContainer(String container) {
		this.container = container;
	}

	/**
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the difficulty
	 */
	public float getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty
	 *            the difficulty to set
	 */
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the terrain
	 */
	public float getTerrain() {
		return terrain;
	}

	/**
	 * @param terrain
	 *            the terrain to set
	 */
	public void setTerrain(float terrain) {
		this.terrain = terrain;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @param shortDescription
	 *            the shortDescription to set
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * @return the longDescription
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * @param longDescription
	 *            the longDescription to set
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	/**
	 * @return the encodedHints
	 */
	public String getEncodedHints() {
		return encodedHints;
	}

	/**
	 * @param encodedHints
	 *            the encodedHints to set
	 */
	public void setEncodedHints(String encodedHints) {
		this.encodedHints = encodedHints;
	}

	/**
	 * @return the logs
	 */
	public Set<Log> getLogs() {
		return logs;
	}

	/**
	 * @param logs
	 *            the logs to set
	 */
	public void setLogs(Set<Log> logs) {
		this.logs = logs;
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

	/**
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GeoCache [gcCode=" + gcCode + ", name=" + name + ", coordinates=" + coordinates + ", placedBy="
				+ placedBy + ", owner=" + owner + ", type=" + type + ", container=" + container + ", attributes="
				+ attributes + ", difficulty=" + difficulty + ", terrain=" + terrain + ", country=" + country
				+ ", state=" + state + ", shortDescription=" + shortDescription + ", longDescription=" + longDescription
				+ ", encodedHints=" + encodedHints + ", logs=" + logs + ", id=" + id + "]";
	}

}
