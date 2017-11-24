package com.andreaspost.gc.cachedb.rest.resource;

import java.time.LocalDateTime;

import org.geojson.Point;

import com.andreaspost.gc.cachedb.rest.converter.LocalDateTimeDeserializer;
import com.andreaspost.gc.cachedb.rest.converter.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Resource representing geo cache data.
 * 
 * @author Andreas Post
 */
public class GeoCache extends BaseResource {

	private String gcCode;

	private String name;

	private Point coordinates;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime placedAt;

	private String placedBy;

	private User owner;
	private CacheType type;
	private String container;

	private float difficulty;
	private float terrain;

	private GeoCacheDetails details;

	public GeoCache() {

	}

	public GeoCache(String gcCode) {
		this.gcCode = gcCode;
	}

	public GeoCache(String href, String id) {
		super(href, id);
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
	 * @return the placedAt
	 */
	public LocalDateTime getPlacedAt() {
		return placedAt;
	}

	/**
	 * @param placedAt
	 *            the placedAt to set
	 */
	public void setPlacedAt(LocalDateTime placedAt) {
		this.placedAt = placedAt;
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
	 * @return the details
	 */
	public GeoCacheDetails getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(GeoCacheDetails details) {
		this.details = details;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GeoCache [gcCode=" + gcCode + ", name=" + name + ", coordinates=" + coordinates + ", placedAt=" + placedAt + ", placedBy=" + placedBy
				+ ", owner=" + owner + ", type=" + type + ", container=" + container + ", difficulty=" + difficulty + ", terrain=" + terrain
				+ ", details=" + details + ", getDetails()=" + getDetails() + ", getHref()=" + getHref() + ", getId()=" + getId() + "]";
	}

}
