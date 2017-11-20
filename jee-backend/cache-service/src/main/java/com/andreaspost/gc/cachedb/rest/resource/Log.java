package com.andreaspost.gc.cachedb.rest.resource;

import java.time.LocalDateTime;

import com.andreaspost.gc.cachedb.rest.converter.LocalDateTimeDeserializer;
import com.andreaspost.gc.cachedb.rest.converter.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Resource representing a geo cache log entry.
 * 
 * @author Andreas Post
 */
public class Log extends BaseResource implements Comparable<Log> {

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime date;

	private LogType type;

	private User finder;

	private String text;

	public Log() {
	}

	public Log(String id) {
		super(id);
	}

	public Log(String href, String id) {
		super(href, id);
	}

	public Log(LocalDateTime date, LogType type, User finder, String text, String id) {
		super(id);
		this.date = date;
		this.type = type;
		this.finder = finder;
		this.text = text;
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
	public LogType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(LogType type) {
		this.type = type;
	}

	/**
	 * @return the finder
	 */
	public User getFinder() {
		return finder;
	}

	/**
	 * @param finder
	 *            the finder to set
	 */
	public void setFinder(User finder) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Log [date=" + date + ", type=" + type + ", finder=" + finder + ", text=" + text + "]";
	}

	@Override
	public int compareTo(Log o) {
		return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(o.getId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Log)) {
			return false;
		}
		return this.getId() == ((Log) obj).getId();
	}
}
