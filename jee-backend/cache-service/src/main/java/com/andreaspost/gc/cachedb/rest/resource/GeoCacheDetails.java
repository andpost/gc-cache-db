package com.andreaspost.gc.cachedb.rest.resource;

import java.util.ArrayList;
import java.util.List;

public class GeoCacheDetails {

	private List<Attribute> attributes = new ArrayList<>();

	private String country;

	private String state;

	private String shortDescription;

	private String longDescription;

	private String encodedHints;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GeoCacheDetails [attributes=" + attributes + ", country=" + country + ", state=" + state + ", shortDescription=" + shortDescription
				+ ", longDescription=" + longDescription + ", encodedHints=" + encodedHints + "]";
	}
}
