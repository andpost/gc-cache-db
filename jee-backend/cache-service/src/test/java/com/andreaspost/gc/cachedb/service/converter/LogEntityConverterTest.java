package com.andreaspost.gc.cachedb.service.converter;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import com.andreaspost.gc.cachedb.persistence.entity.LogEntity;
import com.andreaspost.gc.cachedb.rest.resource.Log;
import com.andreaspost.gc.cachedb.rest.resource.LogType;

/**
 * Tests for {@link LogEntityConverter}.
 * 
 * @author Andreas Post
 */
public class LogEntityConverterTest {

	private static final LocalDateTime DATE = LocalDateTime.now();

	private static final LogType TYPE = LogType.DNF;

	private static final String TEXT = "Test log";

	private static final String ID = "123";

	@Test
	public void decodeTest() {
		LogEntity entity = new LogEntity(DATE, TYPE.getName(), null, TEXT, ID);

		Log log = new LogEntityConverter().decode(entity);

		assertEquals("Date must be the same.", DATE, log.getDate());
		assertEquals("Type must be the same.", TYPE, log.getType());
		assertEquals("ID must be the same.", ID, log.getId());
		assertEquals("Text must be the same.", TEXT, log.getText());
	}

	@Test
	public void encodeTest() {
		Log log = new Log(DATE, TYPE, null, TEXT, ID);

		LogEntity entity = new LogEntityConverter().encode(log);

		assertEquals("Date must be the same.", DATE, entity.getDate());
		assertEquals("Type must be the same.", TYPE.getName(), entity.getType());
		assertEquals("ID must be the same.", ID, entity.getId());
		assertEquals("Text must be the same.", TEXT, entity.getText());
	}
}
