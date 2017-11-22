package com.andreaspost.gc.cachedb.service.converter;

import com.andreaspost.gc.cachedb.persistence.entity.UserEntity;
import com.andreaspost.gc.cachedb.rest.resource.User;

/**
 * Converter class for converting from {@link User} to {@link UserEntity} and back.
 * 
 * @author Andreas Post
 */
public class UserEntityConverter extends AbstractEntityConverter<User, UserEntity> {

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public User decode(UserEntity entity) {

		User user = new User();

		user.setId(entity.getId());
		user.setName(entity.getName());

		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public UserEntity encode(User user) {
		UserEntity entity = new UserEntity();

		entity.setId(user.getId());
		entity.setName(user.getName());

		return entity;
	}
}
