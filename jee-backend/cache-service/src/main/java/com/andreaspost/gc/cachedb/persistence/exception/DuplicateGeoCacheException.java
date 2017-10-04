package com.andreaspost.gc.cachedb.persistence.exception;

public class DuplicateGeoCacheException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6062180862213869226L;

	public DuplicateGeoCacheException() {
		super();
	}

	public DuplicateGeoCacheException(String message) {
		super(message);
	}

	public DuplicateGeoCacheException(Throwable cause) {
		super(cause);
	}

	public DuplicateGeoCacheException(String message, Throwable cause) {
		super(message, cause);
	}
}
