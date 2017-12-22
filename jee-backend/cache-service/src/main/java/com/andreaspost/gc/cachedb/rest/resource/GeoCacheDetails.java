package com.andreaspost.gc.cachedb.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.geojson.Point;

/**
 * Details of a geocache.
 * 
 * @author Andreas Post
 */
public class GeoCacheDetails {

	private List<Attribute> attributes = new ArrayList<>();

	private String country;

	private String state;

	private String shortDescription;

	private String longDescription;

	private String encodedHints;

	private Point originalCoordinates;

	private User reviewer;

	private String personalNote;

	private Integer favPoints;

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
	public User getReviewer() {
		return reviewer;
	}

	/**
	 * @param reviewer
	 *            the reviewer to set
	 */
	public void setReviewer(User reviewer) {
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
		return "GeoCacheDetails [attributes=" + attributes + ", country=" + country + ", state=" + state + ", shortDescription=" + shortDescription
				+ ", longDescription=" + longDescription + ", encodedHints=" + encodedHints + ", originalCoordinates=" + originalCoordinates
				+ ", reviewer=" + reviewer + ", personalNote=" + personalNote + ", favPoints=" + favPoints + "]";
	}

}
