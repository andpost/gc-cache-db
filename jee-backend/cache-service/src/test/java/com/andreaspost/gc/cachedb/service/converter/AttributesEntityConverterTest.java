package com.andreaspost.gc.cachedb.service.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.andreaspost.gc.cachedb.persistence.entity.AttributeEntity;
import com.andreaspost.gc.cachedb.rest.resource.Attribute;

/**
 * Tests for {@link AttributesEntityConverter}.
 * 
 * @author Andreas Post
 */
public class AttributesEntityConverterTest {

	private static final String NAME = "Scenic view";
	private static final String ID = "8";

	@Test
	public void decodeTest() {
		AttributeEntity entity = new AttributeEntity(NAME, ID);

		Attribute user = new AttributesEntityConverter().decode(entity);

		assertEquals("ID must be the same.", ID, user.getId());
		assertEquals("Name must be the same.", NAME, user.getName());
	}

	@Test
	public void encodeTest() {
		Attribute user = new Attribute(NAME, ID);

		AttributeEntity entity = new AttributesEntityConverter().encode(user);

		assertEquals("ID must be the same.", ID, entity.getId());
		assertEquals("Name must be the same.", NAME, entity.getName());
	}
}
