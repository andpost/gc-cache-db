package com.andreaspost.gc.cachedb.persistence.entity;

import java.time.LocalDateTime;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Embedded
@Entity(noClassnameStored = true)
public class LogEntity implements Comparable<LogEntity> {

	private LocalDateTime date;
	private String type;

	@Embedded
	private UserEntity finder;

	private String text;

	private String id;

	public LogEntity() {
	}

	public LogEntity(LocalDateTime date, String type, UserEntity finder, String text, String id) {
		this.date = date;
		this.type = type;
		this.finder = finder;
		this.text = text;
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
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
	 * @return the finder
	 */
	public UserEntity getFinder() {
		return finder;
	}

	/**
	 * @param finder
	 *            the finder to set
	 */
	public void setFinder(UserEntity finder) {
		this.finder = finder;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
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

	@Override
	public int compareTo(LogEntity o) {
		return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(o.getId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LogEntity)) {
			return false;
		}
		return this.getId() == ((LogEntity) obj).getId();
	}
}
