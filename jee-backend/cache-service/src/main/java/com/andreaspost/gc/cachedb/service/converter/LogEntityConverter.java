package com.andreaspost.gc.cachedb.service.converter;

import com.andreaspost.gc.cachedb.persistence.entity.LogEntity;
import com.andreaspost.gc.cachedb.rest.resource.Log;
import com.andreaspost.gc.cachedb.rest.resource.LogType;

public class LogEntityConverter extends AbstractEntityConverter<Log, LogEntity> {

	private static final UserEntityConverter userConverter = new UserEntityConverter();

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public Log decode(LogEntity entity) {

		Log log = new Log();

		log.setId(entity.getId());
		log.setDate(entity.getDate());
		log.setText(entity.getText());
		log.setType(LogType.of(entity.getType()));

		if (entity.getFinder() != null) {
			log.setFinder(userConverter.decode(entity.getFinder()));
		}

		return log;
	}

	/**
	 * 
	 * @param Log
	 * @return
	 */
	public LogEntity encode(Log log) {
		LogEntity entity = new LogEntity();

		entity.setId(log.getId());
		entity.setDate(log.getDate());
		entity.setText(log.getText());
		entity.setType(log.getType().getName());

		if (log.getFinder() != null) {
			entity.setFinder(userConverter.encode(log.getFinder()));
		}

		return entity;
	}
}
