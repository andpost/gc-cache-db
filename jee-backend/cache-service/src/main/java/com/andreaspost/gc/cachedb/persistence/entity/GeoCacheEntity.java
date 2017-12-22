package com.andreaspost.gc.cachedb.persistence.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.geo.Point;
import org.mongodb.morphia.utils.IndexType;

@Entity(value = "geocache", noClassnameStored = true)
@Indexes({ @Index(fields = { @Field(value = "coordinates", type = IndexType.GEO2DSPHERE) }),
		@Index(fields = { @Field(value = "gcCode") }, options = @IndexOptions(unique = true)),
		@Index(fields = { @Field(value = "name") }), @Index(fields = { @Field(value = "type") }),
		@Index(fields = { @Field(value = "id") }, options = @IndexOptions(unique = true)) })
public class GeoCacheEntity {

	@Id
	private ObjectId objectId;

	private String gcCode;

	private String name;

	@Embedded
	private Point coordinates;

	private LocalDateTime placedAt;

	private String placedBy;

	@Embedded
	private UserEntity owner;

	private String type;

	private String container;

	@Embedded
	private List<AttributeEntity> attributes = new ArrayList<>();

	private float difficulty;
	private float terrain;
	private String country;
	private String state;

	private String shortDescription;

	private String longDescription;

	private String encodedHints;

	private String id;

	@Embedded
	private Point originalCoordinates;

	@Embedded
	private UserEntity reviewer;

	private String personalNote;

	private Integer favPoints;

	@Embedded
	private Set<LogEntity> logs = new TreeSet<>();

	public GeoCacheEntity() {

	}

	/**
	 * @return the objectId
	 */
	public ObjectId getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId
	 *            the objectId to set
	 */
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
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
	public UserEntity getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
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
	public List<AttributeEntity> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(List<AttributeEntity> attributes) {
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
	 * @return the logs
	 */
	public Set<LogEntity> getLogs() {
		return logs;
	}

	/**
	 * @param logs
	 *            the logs to set
	 */
	public void setLogs(Set<LogEntity> logs) {
		this.logs = logs;
	}

	/**
	 * @return the originalCoordinates
	 */
	public Point getOriginalCoordinates() {
		return originalCoordinates;
	}

	/**
	 * @param originalCoordinates
	 *            the originalCoordinates to set
	 */
	public void setOriginalCoordinates(Point originalCoordinates) {
		this.originalCoordinates = originalCoordinates;
	}

	/**
	 * @return the reviewer
	 */
	public UserEntity getReviewer() {
		return reviewer;
	}

	/**
	 * @param reviewer
	 *            the reviewer to set
	 */
	public void setReviewer(UserEntity reviewer) {
		this.reviewer = reviewer;
	}

	/**
	 * @return the personalNote
	 */
	public String getPersonalNote() {
		return personalNote;
	}

	/**
	 * @param personalNote
	 *            the personalNote to set
	 */
	public void setPersonalNote(String personalNote) {
		this.personalNote = personalNote;
	}

	/**
	 * @return the favPoints
	 */
	public Integer getFavPoints() {
		return favPoints;
	}

	/**
	 * @param favPoints
	 *            the favPoints to set
	 */
	public void setFavPoints(Integer favPoints) {
		this.favPoints = favPoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GeoCacheEntity [objectId=" + objectId + ", gcCode=" + gcCode + ", name=" + name + ", coordinates=" + coordinates + ", placedAt="
				+ placedAt + ", placedBy=" + placedBy + ", owner=" + owner + ", type=" + type + ", container=" + container + ", attributes="
				+ attributes + ", difficulty=" + difficulty + ", terrain=" + terrain + ", country=" + country + ", state=" + state
				+ ", shortDescription=" + shortDescription + ", longDescription=" + longDescription + ", encodedHints=" + encodedHints + ", id=" + id
				+ ", originalCoordinates=" + originalCoordinates + ", reviewer=" + reviewer + ", personalNote=" + personalNote + ", favPoints="
				+ favPoints + ", logs=" + logs + "]";
	}
}
