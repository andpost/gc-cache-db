package com.andreaspost.gc.cachedb.service.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.andreaspost.gc.cachedb.persistence.entity.UserEntity;
import com.andreaspost.gc.cachedb.rest.resource.User;

/**
 * Tests for {@link UserEntityConverter}.
 * 
 * @author Andreas Post
 */
public class UserEntityConverterTest {

	private static final String ID = "123";

	private static final String NAME = "testuser";

	@Test
	public void decodeTest() {
		UserEntity entity = new UserEntity(NAME, ID);

		User user = new UserEntityConverter().decode(entity);

		assertEquals("ID must be the same.", ID, user.getId());
		assertEquals("Name must be the same.", NAME, user.getName());
	}

	@Test
	public void encodeTest() {
		User user = new User(NAME, ID);

		UserEntity entity = new UserEntityConverter().encode(user);

		assertEquals("ID must be the same.", ID, entity.getId());
		assertEquals("Name must be the same.", NAME, entity.getName());
	}
}
